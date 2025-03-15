package cn.tesseract.rwally.hook;

import cn.tesseract.dragonfly.asm.Hook;
import cn.tesseract.rwally.Reference;
import cn.tesseract.rwally.accessor.BattleroomAccessor;
import cn.tesseract.rwally.command.CommandBase;
import cn.tesseract.rwally.command.JSCommand;
import cn.tesseract.rwally.command.SingleDoubleCommand;
import cn.tesseract.rwally.command.SingleIntegerCommand;
import cn.tesseract.rwally.util.RWHelper;
import com.corrodinggames.rts.ally.appFramework.MultiplayerBattleroomActivity;
import com.corrodinggames.rts.ally.game.class_315;
import com.corrodinggames.rts.ally.gameFramework.j.class_1054;
import com.corrodinggames.rts.ally.gameFramework.j.class_1101;
import org.mozilla.javascript.Function;

import java.util.HashMap;

public class CommandHook {
    private static HashMap<String, CommandBase> commands = new HashMap<>();

    public static void register(String name, CommandBase command) {
        commands.put(name, command);
    }

    public static void register(String name, int args, boolean requireOp, String description, Function func) {
        if (RhinoHook.getCurrentScript().equals("done"))
            throw new IllegalStateException("不应该在脚本初始化以外的地方注册指令！");
        register(name, new JSCommand(args, requireOp, description, func, RhinoHook.getCurrentScript()));
    }

    static {
        register("echo", new CommandBase(1, true, "发送信息，这是结盟版加入的第一个指令") {
            @Override
            public String processCommand(class_1054 sender, String[] args) {
                RWHelper.sendMessage(args[0], sender);
                return null;
            }
        });
        register("help", new CommandBase(0, false, "查看指令帮助") {
            @Override
            public String processCommand(class_1054 sender, String[] args) {
                StringBuilder help = new StringBuilder();
                commands.forEach((name, command) -> {
                    help.append("\n").append(".").append(name).append(command.requireOp ? " (需要权限)" : "").append(" 用法: ").append(command.description);
                });
                RWHelper.sendMessage(help.toString(), sender);
                return null;
            }
        });
        register("debug", new CommandBase(0, true, "调试指令") {
            @Override
            public String processCommand(class_1054 sender, String[] args) {
                /*new Thread(() -> {
                    try {
                        String url = args[0];
                        BufferedReader reader = class_1084.a(new ArrayList<BasicNameValuePair>(), url, false).a;
                        StringBuilder sb = new StringBuilder();
                        String line;

                        while ((line = reader.readLine()) != null) {
                            if (sb.length() == 0) sb.append("\n");
                            sb.append(line);
                        }

                        RWHelper.sendSysMessage(sb);
                    } catch (IOException e) {
                        RWHelper.sendSysMessage(e);
                    }
                }).start();*/

                /*for (Object o : RWHelper.getNetworkEngine().aO) {
                    RWHelper.sendMessage((class_1054) o, "[提示]", "测试");
                }*/
                return null;
            }
        });
        register("bu", new CommandBase(1, true, "禁用单位") {
            @Override
            public String processCommand(class_1054 sender, String[] args) {
                Reference.bannedUnits.add(args[0]);
                return "已禁用单位 " + args[0] + " ！";
            }
        });
        register("ub", new CommandBase(1, true, "解禁单位") {
            @Override
            public String processCommand(class_1054 sender, String[] args) {
                Reference.bannedUnits.remove(args[0]);
                return "已解禁单位 " + args[0] + " ！";
            }
        });
        register("max", new SingleIntegerCommand(1, true, "设置最大人数") {
            @Override
            public String processCommand(int n) {
                n = Math.max(n, 10);
                RWHelper.setMaxPlayer(n);
                return "最大人数已设置为 " + n + " 人！";
            }
        });
        register("in", new SingleDoubleCommand(1, true, "设置收入倍数") {
            @Override
            public String processCommand(double n) {
                RWHelper.getNetworkEngine().aA.h = (float) n;
                ((BattleroomAccessor) MultiplayerBattleroomActivity.lastLoaded).invoke_refreshServerInfo();
                RWHelper.sync();
                return "收入倍率已设为 " + n + " ！";
            }
        });
        register("sync", new CommandBase(0, true, "立刻同步") {
            @Override
            public String processCommand(class_1054 sender, String[] args) {
                RWHelper.sync();
                return null;
            }
        });
        register("uc", new SingleIntegerCommand(0, true, "设置单位上限") {
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
    public static void b(class_1101 c, class_1054 conn, class_315 player, String str, String msg) {
        boolean isHost = c.D && player == c.A;
        msg = msg.trim();
        if (msg.startsWith(".") || msg.startsWith("-")) {
            String[] arr = msg.substring(1).split(" ", 2);
            CommandBase cmd;
            if ((cmd = commands.get(arr[0])) != null) {
                if (cmd.requireOp && !isHost) RWHelper.sendMessage("仅房主可使用该指令！", conn);
                else {
                    String r = cmd.processCommand(conn, arr.length == 1 ? new String[0] : arr[1].split(" ", cmd.args));
                    if (r != null && !r.isEmpty()) RWHelper.sendMessage(r, conn);
                }
            }
        }
    }
}
