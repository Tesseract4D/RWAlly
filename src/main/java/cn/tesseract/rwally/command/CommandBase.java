package cn.tesseract.rwally.command;

import com.corrodinggames.rts.ally.gameFramework.j.class_1054;

public abstract class CommandBase {
    public int args;
    public boolean requireOp;
    public String description;

    public CommandBase(int args, boolean requireOp, String description) {
        this.args = args;
        this.requireOp = requireOp;
        this.description = description;
    }

    public abstract String processCommand(class_1054 sender, String[] args);
}
