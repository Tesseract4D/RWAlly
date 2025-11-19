package cn.tesseract.union.command;

import com.corrodinggames.rts.union.game.class_324;
import com.corrodinggames.rts.union.gameFramework.j.class_1037;

public class UnionCommand {
    public final Executor exec;
    public final String desc;
    public final boolean op;

    @FunctionalInterface
    public interface Executor {
        void execute(class_324 player, class_1037 sender, String arg);
    }

    public UnionCommand(Executor exec, String desc, boolean op) {
        this.exec = exec;
        this.desc = desc;
        this.op = op;
    }
}
