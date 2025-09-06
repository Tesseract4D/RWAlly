package cn.tesseract.union.api.rusted;

import cn.tesseract.union.accessor.BattleroomAccessor;
import cn.tesseract.union.accessor.ConnectionAccessor;
import cn.tesseract.union.accessor.NetworkAccessor;
import cn.tesseract.union.accessor.PlayerAccessor;
import cn.tesseract.union.api.command.UnionCommand;
import cn.tesseract.union.api.command.WrongUsageException;
import cn.tesseract.union.api.util.ScriptManager;
import com.corrodinggames.rts.union.appFramework.MultiplayerBattleroomActivity;
import com.corrodinggames.rts.union.game.units.Waypoint;
import com.corrodinggames.rts.union.game.units.WaypointType;
import com.corrodinggames.rts.union.game.units.a.ActionId;
import com.corrodinggames.rts.union.game.units.class_431;
import com.corrodinggames.rts.union.gameFramework.Action;
import com.corrodinggames.rts.union.gameFramework.GameEngine;
import com.corrodinggames.rts.union.gameFramework.j.NetworkEngine;
import com.corrodinggames.rts.union.gameFramework.j.class_1030;
import com.corrodinggames.rts.union.gameFramework.j.class_1037;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RustedNetwork extends RustedWrapper<NetworkEngine> {
    private final Map<String, UnionCommand> commandMap = new HashMap<>();
    private static RustedNetwork instance;
    public static boolean delayedSync = false;

    public RustedNetwork(NetworkEngine object) {
        super(object);
        instance = this;
        registerCommand("echo", new UnionCommand((player, conn, arg) -> {
            sendMessage(arg, conn);
        }, "给你自己发送信息", false));
        registerCommand("help", new UnionCommand((player, conn, arg) -> {
            StringBuilder sb = new StringBuilder();
            sb.append("联盟版指令：").append('\n');
            commandMap.forEach((s, c) -> {
                sb.append(s).append(" : ").append(c.desc).append('\n');
            });
            sendMessage(sb.toString(), conn);
        }, "查看所有指令", false));
        registerCommand("max", new UnionCommand((player, conn, arg) -> {
            int i = Integer.parseInt(arg);
            if (i < 10 || i > 100) throw new WrongUsageException("数值必须在10到100间！");
            RustedPlayer.setMaxPlayer(i);
            sendMessage("房间最大人数设置为：" + i, conn);
        }, "设置最大人数", true));
        registerCommand("unitcap", new UnionCommand((player, conn, arg) -> {
            int i = Integer.parseInt(arg);
            object.field_5925 = i;
            object.field_5926 = i;
            setDelayedSync();
            sendMessage("单位上限设置为：" + i, conn);
        }, "设置单位上限", true));
        registerCommand("rs", new UnionCommand((player, conn, arg) -> {
            sync();
        }, "立刻同步", true));
        registerCommand("income", new UnionCommand((player, conn, arg) -> {
            float i = Float.parseFloat(arg);
            object.field_5874.field_6019 = i;
            ((BattleroomAccessor) MultiplayerBattleroomActivity.lastLoaded).invoke_refreshServerInfo();
            sync();
            sendMessage("经济倍率已设置为：" + arg, conn);
        }, "设置经济倍率", true));
        registerCommand("rl", new UnionCommand((player, conn, arg) -> {
            ScriptManager.reload();
        }, "重载脚本", true));
        registerCommand("nukes", new UnionCommand((player, conn, arg) -> {
            if ("on".equals(arg) || "true".equals(arg)) {
                inner.field_5874.field_6020 = true;
                sync();
            } else if ("off".equals(arg) || "false".equals(arg)) {
                inner.field_5874.field_6020 = false;
                sync();
            } else {
                throw new WrongUsageException("参数应为 on 或 true 来开启，off 或 false 来关闭");
            }
        }, "开关禁核弹", true));
        registerCommand("sh", new UnionCommand((player, conn, arg) -> {
            if ("on".equals(arg) || "true".equals(arg)) {
                inner.field_5874.field_6023 = true;
                sync();
            } else if ("off".equals(arg) || "false".equals(arg)) {
                inner.field_5874.field_6023 = false;
                sync();
            } else {
                throw new WrongUsageException("参数应为 on 或 true 来开启，off 或 false 来关闭");
            }
        }, "开关分享控制", true));
    }

    public static RustedNetwork get() {
        return instance;
    }

    public RustedPlayer getHost() {
        return ((PlayerAccessor) inner.field_5848).get_wrapper();
    }

    public boolean isOp(RustedPlayer player) {
        return inner.field_5851 && player.inner == inner.field_5848;
    }

    public boolean executeCommand(String name, RustedPlayer player, RustedConnection conn, String arg) {
        UnionCommand command = commandMap.get(name);
        if (command != null) {
            if (command.op && !isOp(player)) {
                sendMessage("仅有房主能使用该指令！", conn);
                return false;
            }
            try {
                command.exec.execute(player, conn, arg);
            } catch (NumberFormatException e) {
                sendMessage("错误：" + arg + " 不是有效的数字！", conn);
            } catch (WrongUsageException e) {
                sendMessage("错误：" + e.getMessage(), conn);
            } catch (Exception e) {
                sendMessage("未知的错误：" + e.getMessage(), conn);
            }
            return true;
        }
        return false;
    }

    public void registerCommand(String name, UnionCommand command) {
        commandMap.put(name, command);
    }

    public void sendTeamMessage(String message, int team) {
        ConcurrentLinkedQueue<class_1037> connections = inner.connections;
        for (class_1037 conn : connections) {
            if (conn.field_6181 && !conn.field_6166 && conn.field_6142.team == team) {
                sendMessage(message, null, ((ConnectionAccessor) conn).get_wrapper(), -1);
            }
        }
        if (inner.field_5848.team == team) {
            sendMessage(message, null);
        }
    }

    public void sendMessage(String message, int index) {
        ConcurrentLinkedQueue<class_1037> connections = inner.connections;
        for (class_1037 conn : connections) {
            if (conn.field_6142 != null && conn.field_6142.teamId == index) {
                sendMessage(message, null, ((ConnectionAccessor) conn).get_wrapper(), -1);
            }
        }
        if (inner.field_5848 != null && inner.field_5848.teamId == index) {
            sendMessage(message, null);
        }
    }

    @Deprecated
    public void broadcastHostChat(String message) {
        inner.method_2816(message);
    }

    public void broadcastMessage(String message) {
        inner.method_2809(message);
    }

    public void sendMessage(String message, RustedConnection conn) {
        sendMessage(message, null, conn, -1);
    }

    public void sendMessage(String message, String sender, RustedConnection conn) {
        sendMessage(message, sender, conn, -1);
    }

    public void sendMessage(String message, String sender, RustedConnection conn, int color) {
        if (conn == null) {
            ((NetworkAccessor) inner).invoke_method_2740(null, color, sender, message);
        } else {
            class_1030 pk = new class_1030();
            pk.method_2880(message);
            pk.method_2877(3);
            pk.method_2871(sender);
            pk.method_2865(conn.inner);
            pk.method_2883(color);
            conn.inner.method_2894(pk.method_2856(141));
        }
    }

    public boolean isInGame() {
        return inner.field_5898;
    }

    public void sync() {
        if (isInGame())
            for (Object o : inner.connections) {
                class_1037 p = (class_1037) o;
                p.field_6187 = true;
                p.field_6188 = true;
            }
    }

    public void setDelayedSync() {
        if (isInGame()) delayedSync = true;
    }

    public void spawnUnit(RustedPlayer player, String name, float x, float y) {
        spawnUnit(player, name, x, y, 0);
    }

    public void spawnUnit(RustedPlayer player, String name, float x, float y, int tech) {
        NetworkEngine network = inner;

        //Waypoint
        Waypoint waypoint = new Waypoint();
        waypoint.type = WaypointType.build;
        waypoint.info = class_431.method_1082(name);
        waypoint.techLevel = tech;
        waypoint.x = x;
        waypoint.y = y;

        //Action
        Action action = GameEngine.get().actionManager.createAction();
        action.field_5084 = network.field_5873 + network.field_5866;
        action.player = player.inner;
        action.waypoint = waypoint;
        action.id = ActionId.none;
        action.field_5100 = true;
        action.field_5103 = 5;

        network.method_2734(action);
    }
}
