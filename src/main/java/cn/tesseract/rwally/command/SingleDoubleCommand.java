package cn.tesseract.rwally.command;

import com.corrodinggames.rts.ally.game.class_315;

public abstract class SingleDoubleCommand extends CommandBase {
    public SingleDoubleCommand(int args, boolean requireOp, String description) {
        super(args, requireOp, description);
    }

    public String processCommand(class_315 sender, String[] args) {
        try {
            if (args.length == 0)
                return "参数不能为空！";
            else
                return processCommand(Double.parseDouble(args[0]));
        } catch (NumberFormatException e) {
            return "\"" + args[0] + "\"不是正确的数字！";
        }
    }

    public abstract String processCommand(double n);
}
