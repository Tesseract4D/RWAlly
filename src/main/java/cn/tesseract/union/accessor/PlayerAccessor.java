package cn.tesseract.union.accessor;

import cn.tesseract.union.asm.Accessor;
import com.corrodinggames.rts.union.game.class_324;

@Accessor.Target(class_324.class)
public interface PlayerAccessor {
    boolean get_op();

    void set_op(boolean f);
    boolean get_muted();

    void set_muted(boolean f);
}
