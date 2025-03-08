package cn.tesseract.rwally.accessor;

import cn.tesseract.dragonfly.asm.Accessor;
import com.corrodinggames.rts.ally.gameFramework.n.class_1262;

@Accessor.Target(class_1262.class)
public interface MapTextAccessor {
    float get_baseSize();

    void set_baseSize(float size);
}
