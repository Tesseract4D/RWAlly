package cn.tesseract.union.api.command;

import cn.tesseract.union.api.rusted.RustedConnection;
import cn.tesseract.union.api.rusted.RustedPlayer;

public class UnionCommand {
    public final Executor exec;
    public final String desc;
    public final boolean op;

    @FunctionalInterface
    public interface Executor {
        void execute(RustedPlayer player, RustedConnection sender, String arg);
    }

    public UnionCommand(Executor exec, String desc, boolean op) {
        this.exec = exec;
        this.desc = desc;
        this.op = op;
    }
}
