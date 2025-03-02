package cn.tesseract.rwally.command;

import cn.tesseract.rwally.RWHelper;
import com.corrodinggames.rts.ally.game.class_315;

public abstract class PlayerCommand extends CommandBase {
    public PlayerCommand(int args, boolean requireOp, String description) {
        super(args, requireOp, description);
    }

    @Override
    public String processCommand(class_315 sender, String[] args) {
        try {
            if (args.length == 0)
                return "参数不能为空！";
            else {
                class_315 player = RWHelper.getPlayer(Integer.parseInt(args[0]));
                if (player == null)
                    return "该玩家不存在！";
                else
                    return processCommand(args, player);
            }
        } catch (NumberFormatException e) {
            return "\"" + args[0] + "\"不是正确的数字！";
        }
    }

    public abstract String processCommand(String[] args, class_315 player);
}
