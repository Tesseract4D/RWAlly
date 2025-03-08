package cn.tesseract.rwally.accessor;


import cn.tesseract.dragonfly.asm.Accessor;
import com.corrodinggames.rts.ally.gameFramework.j.class_1054;
import com.corrodinggames.rts.ally.gameFramework.j.class_1101;

@Accessor.Target(class_1101.class)
public interface NetworkAccessor {
    void invoke_a(String msg, class_1054 conn);

    void invoke_a(class_1054 conn, int color, String sender, String msg);
}