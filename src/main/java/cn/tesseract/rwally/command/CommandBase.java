package cn.tesseract.rwally.command;

import com.corrodinggames.rts.ally.game.class_315;

public abstract class CommandBase {
    public int args;
    public boolean requireOp;
    public String description;

    public CommandBase(int args, boolean requireOp, String description) {
        this.args = args;
        this.requireOp = requireOp;
        this.description = description;
    }

    public abstract String processCommand(class_315 sender, String[] args);
}
