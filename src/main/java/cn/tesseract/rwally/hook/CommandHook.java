package cn.tesseract.rwally.hook;

import cn.tesseract.dragonfly.asm.Hook;
import cn.tesseract.rwally.Reference;
import cn.tesseract.rwally.accessor.BattleroomAccessor;
import cn.tesseract.rwally.command.CommandBase;
import cn.tesseract.rwally.command.SingleDoubleCommand;
import cn.tesseract.rwally.command.SingleIntegerCommand;
import cn.tesseract.rwally.util.RWHelper;
import com.corrodinggames.rts.ally.appFramework.MultiplayerBattleroomActivity;
import com.corrodinggames.rts.ally.game.class_315;
import com.corrodinggames.rts.ally.gameFramework.j.class_1054;
import com.corrodinggames.rts.ally.gameFramework.j.class_1101;

import java.util.HashMap;

public class CommandHook {
    public static HashMap<String, CommandBase> commands = new HashMap<>();

    static {
        commands.put("echo", new CommandBase(1, true, "发送信息，这是结盟版加入的第一个指令") {
            @Override
            public String processCommand(class_315 sender, String[] args) {
                RWHelper.sendSysMessage(args[0]);
                return null;
            }
        });
        commands.put("bu", new CommandBase(1, true, "禁用单位") {
            @Override
            public String processCommand(class_315 sender, String[] args) {
                Reference.bannedUnits.add(args[0]);
                return "已禁用单位 " + args[0] + " ！";
            }
        });
        commands.put("ub", new CommandBase(1, true, "解禁单位") {
            @Override
            public String processCommand(class_315 sender, String[] args) {
                Reference.bannedUnits.remove(args[0]);
                return "已解禁单位 " + args[0] + " ！";
            }
        });
        commands.put("max", new SingleIntegerCommand(1, true, "设置最大人数") {
            @Override
            public String processCommand(int n) {
                n = Math.min(n, 10);
                RWHelper.setMaxPlayer(n);
                return "最大人数已设置为 " + n + " 人！";
            }
        });
        commands.put("in", new SingleDoubleCommand(1, true, "设置收入倍数") {
            @Override
            public String processCommand(double n) {
                RWHelper.getNetworkEngine().aA.h = (float) n;
                ((BattleroomAccessor) MultiplayerBattleroomActivity.lastLoaded).invoke_refreshServerInfo();
                RWHelper.sync();
                return "收入倍率已设为 " + n + " ！";
            }
        });
        commands.put("sync", new CommandBase(0, true, "立刻同步") {
            @Override
            public String processCommand(class_315 sender, String[] args) {
                RWHelper.sync();
                return null;
            }
        });
        commands.put("debug", new CommandBase(0, true, "调试指令") {

            @Override
            public String processCommand(class_315 sender, String[] args) {
                for (Object o : RWHelper.getNetworkEngine().aO) {
                    RWHelper.sendMessage((class_1054) o, "[提示]", "测试");
                }
                return null;
            }
        });
        commands.put("uc", new SingleIntegerCommand(0, true, "设置单位上线") {
            @Override
            public String processCommand(int n) {
                RWHelper.getNetworkEngine().ay = n;
                RWHelper.getNetworkEngine().az = n;
                RWHelper.sync();
                return "单位上限已设为 " + n + " ！";
            }
        });
    }

    @Hook
    public static void b(class_1101 c, class_1054 class_1054Var, class_315 class_315Var, String str, String msg) {
        boolean isHost = c.D && class_315Var == c.A;
        msg = msg.trim();
        if (msg.startsWith(".") || msg.startsWith("-")) {
            String[] arr = msg.substring(1).split(" ", 2);
            CommandBase cmd;
            if ((cmd = commands.get(arr[0])) != null) {
                if (cmd.requireOp && !isHost) RWHelper.sendSysMessage("仅房主可使用该指令！", class_1054Var);
                else {
                    String r = cmd.processCommand(class_315Var, arr.length == 1 ? new String[0] : arr[1].split(" ", cmd.args));
                    if (r != null && !r.isEmpty()) RWHelper.sendSysMessage(r, class_1054Var);
                }
            }
        }
    }
}
