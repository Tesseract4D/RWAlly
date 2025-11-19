package cn.tesseract.union.util;

import cn.tesseract.union.accessor.BattleroomAccessor;
import cn.tesseract.union.accessor.NetworkAccessor;
import cn.tesseract.union.accessor.PlayerAccessor;
import cn.tesseract.union.command.CommandException;
import cn.tesseract.union.command.UnionCommand;
import com.corrodinggames.rts.union.appFramework.MultiplayerBattleroomActivity;
import com.corrodinggames.rts.union.game.class_324;
import com.corrodinggames.rts.union.game.units.a.class_333;
import com.corrodinggames.rts.union.game.units.class_431;
import com.corrodinggames.rts.union.game.units.class_706;
import com.corrodinggames.rts.union.game.units.class_707;
import com.corrodinggames.rts.union.gameFramework.class_1061;
import com.corrodinggames.rts.union.gameFramework.class_898;
import com.corrodinggames.rts.union.gameFramework.j.class_1001;
import com.corrodinggames.rts.union.gameFramework.j.class_1030;
import com.corrodinggames.rts.union.gameFramework.j.class_1037;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class NetHelper {
    public static final class_1001 net = class_1061.method_3076().field_6352;
    private static final Map<String, UnionCommand> commandMap = new HashMap<>();
    public static boolean delayedSync = false;

    static {
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
            if (i < 10 || i > 100) throw new CommandException("数值必须在10到100间！");
            class_324.method_488(i, true);
            sendMessage("房间最大人数设置为：" + i, conn);
        }, "设置最大人数", true));
        registerCommand("unitcap", new UnionCommand((player, conn, arg) -> {
            int i = Integer.parseInt(arg);
            net.field_5925 = i;
            net.field_5926 = i;
            sync(false);
            net.field_5952 = 3601f;
            net.field_5953 = 14401f;
            sendMessage("单位上限设置为：" + i, conn);
        }, "设置单位上限", true));
        registerCommand("rs", new UnionCommand((player, conn, arg) -> {
            sync(true);
        }, "立刻同步", true));
        registerCommand("income", new UnionCommand((player, conn, arg) -> {
            float i = Float.parseFloat(arg);
            net.field_5874.field_6019 = i;
            ((BattleroomAccessor) MultiplayerBattleroomActivity.lastLoaded).invoke_refreshServerInfo();
            sync(true);
            sendMessage("经济倍率已设置为：" + arg, conn);
        }, "设置经济倍率", true));
        registerCommand("rl", new UnionCommand((player, conn, arg) -> {
            ScriptManager.reload();
        }, "重载脚本", true));
        registerCommand("nukes", new UnionCommand((player, conn, arg) -> {
            if ("on".equals(arg) || "true".equals(arg)) {
                net.field_5874.field_6020 = true;
                sync(true);
            } else if ("off".equals(arg) || "false".equals(arg)) {
                net.field_5874.field_6020 = false;
                sync(true);
            } else {
                throw new CommandException("参数应为 on 或 true 来开启，off 或 false 来关闭");
            }
            sendMessage("禁核已 " + (net.field_5874.field_6020 ? "开启" : "关闭"), conn);
        }, "开关禁核弹", true));
        registerCommand("op", new UnionCommand((player, conn, arg) -> {
            class_324 target;
            if ("on".equals(arg) || "true".equals(arg)) {
                net.field_5874.field_6020 = true;
                sync(true);
            } else if ("off".equals(arg) || "false".equals(arg)) {
                net.field_5874.field_6020 = false;
                sync(true);
            } else {
                throw new CommandException("参数应为 on 或 true 来开启，off 或 false 来关闭");
            }
            sendMessage("禁核已 " + (net.field_5874.field_6020 ? "开启" : "关闭"), conn);
        }, "开关禁核弹", true));
        registerCommand("sh", new UnionCommand((player, conn, arg) -> {
            if ("on".equals(arg) || "true".equals(arg)) {
                net.field_5874.field_6023 = true;
                sync(true);
            } else if ("off".equals(arg) || "false".equals(arg)) {
                net.field_5874.field_6023 = false;
                sync(true);
            } else {
                throw new CommandException("参数应为 on 或 true 来开启，off 或 false 来关闭");
            }
            sendMessage("全局分享控制 " + (net.field_5874.field_6020 ? "开启" : "关闭"), conn);
        }, "开关分享控制", true));
        registerCommand("op", new UnionCommand((player, conn, arg) -> {
            var target = PlayerHelper.get(arg);
            if (target == null) throw new CommandException("未找到该玩家！");
            ((PlayerAccessor) target).set_op(true);
            sendMessage("已将玩家 " + target.field_1468 + " 设为管理！", conn);
        }, "给予玩家管理权限", true));
        registerCommand("deop", new UnionCommand((player, conn, arg) -> {
            var target = PlayerHelper.get(arg);
            if (target == null) throw new CommandException("未找到该玩家！");
            ((PlayerAccessor) target).set_op(false);
            sendMessage("已将玩家 " + target.field_1468 + " 取消管理！", conn);
        }, "剥夺玩家管理权限", true));
        registerCommand("mute", new UnionCommand((player, conn, arg) -> {
            var target = PlayerHelper.get(arg);
            if (target == null) throw new CommandException("未找到该玩家！");
            ((PlayerAccessor) target).set_muted(true);
            sendMessage("已将玩家 " + target.field_1468 + " 禁言！", conn);
        }, "禁言玩家", true));
        registerCommand("unmute", new UnionCommand((player, conn, arg) -> {
            var target = PlayerHelper.get(arg);
            if (target == null) throw new CommandException("未找到该玩家！");
            ((PlayerAccessor) target).set_muted(false);
            sendMessage("已将玩家 " + target.field_1468 + " 解除禁言！", conn);
        }, "解除禁言", true));
        registerCommand("muteall", new UnionCommand((player, conn, arg) -> {
            for (var p : PlayerHelper.players()) if (p != null) ((PlayerAccessor) p).set_muted(false);
            sendMessage("已全体禁言！", conn);
        }, "全体禁言", true));
        registerCommand("unmuteall", new UnionCommand((player, conn, arg) -> {
            for (var p : PlayerHelper.players()) if (p != null) ((PlayerAccessor) p).set_muted(false);
            sendMessage("已解除全体禁言", conn);
        }, "解除全体禁言", true));
    }

    public static class_324 getHost() {
        return net.field_5848;
    }

    public static boolean isHost() {
        return net.field_5851;
    }

    public static boolean isOp(class_324 player) {
        return player != null && net.field_5851 && (player == net.field_5848 || ((PlayerAccessor) player).get_op());
    }

    public static boolean executeCommand(String name, class_324 player, class_1037 conn, String arg) {
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
            } catch (CommandException e) {
                sendMessage("错误：" + e, conn);
            } catch (Exception e) {
                sendMessage("未知的错误：" + e, conn);
            }
            return true;
        }
        return false;
    }

    public static void registerCommand(String name, UnionCommand command) {
        commandMap.put(name, command);
    }

    public static void sendTeamMessage(String message, int team) {
        ConcurrentLinkedQueue<class_1037> connections = net.field_5888;
        for (class_1037 conn : connections) {
            if (conn.field_6181 && !conn.field_6166 && conn.field_6142 != null && conn.field_6142.field_1464 == team) {
                sendMessage(message, null, conn, -1);
            }
        }
        if (net.field_5848.field_1464 == team) {
            sendMessage(message, null);
        }
    }

    public static void sendMessage(String message, int index) {
        ConcurrentLinkedQueue<class_1037> connections = net.field_5888;
        for (class_1037 conn : connections) {
            if (conn.field_6142 != null && conn.field_6142.field_1457 == index) {
                sendMessage(message, null, conn, -1);
            }
        }
        if (net.field_5848 != null && net.field_5848.field_1457 == index) {
            sendMessage(message, null);
        }
    }

    @Deprecated
    public static void broadcastHostChat(String message) {
        net.method_2816(message);
    }

    public static void broadcastMessage(String message) {
        if (message != null) net.method_2809(message);
    }

    public static void sendMessage(String message, class_1037 conn) {
        sendMessage(message, null, conn, -1);
    }

    public static void sendMessage(String message, String sender, class_1037 conn) {
        sendMessage(message, sender, conn, -1);
    }

    public static void sendMessage(String message, String sender, class_1037 conn, int color) {
        if (conn == null) {
            ((NetworkAccessor) net).invoke_method_2740(null, color, sender, message);
        } else {
            class_1030 pk = new class_1030();
            pk.method_2880(message);
            pk.method_2877(3);
            pk.method_2871(sender);
            pk.method_2865(conn);
            pk.method_2883(color);
            conn.method_2894(pk.method_2856(141));
        }
    }

    public static boolean isInGame() {
        return net.field_5898;
    }

    public static void sync(boolean immediately) {
        if (isInGame())
            if (immediately)
                for (Object o : net.field_5888) {
                    class_1037 p = (class_1037) o;
                    p.field_6187 = true;
                    p.field_6188 = true;
                }
            else {
                delayedSync = true;
                net.field_5954 = 1;
            }
    }

    public static void spawnUnit(class_324 player, String name, float x, float y) {
        spawnUnit(player, name, x, y, 0);
    }

    public static void spawnUnit(class_324 player, String name, float x, float y, int tech) {
        net.method_2734(spawnUnitAction(player, name, x, y, tech));
    }

    public static class_898 spawnUnitAction(class_324 player, String name, float x, float y, int tech) {
        class_1001 network = net;

        //Waypoint
        class_706 waypoint = new class_706();
        waypoint.field_3927 = class_707.field_3943;
        waypoint.field_3928 = class_431.method_1082(name);
        //waypoint.field_3930 = tech;
        waypoint.field_3931 = x;
        waypoint.field_3932 = y;

        //Action
        class_898 action = class_1061.method_3076().field_6412.method_2060();
        action.field_5084 = network.field_5873 + network.field_5866;
        action.field_5090 = player;
        action.field_5091 = waypoint;
        //action id
        action.field_5092 = class_333.method_560("-1");
        action.field_5100 = true;
        action.field_5103 = 5;

        return action;
    }
}
