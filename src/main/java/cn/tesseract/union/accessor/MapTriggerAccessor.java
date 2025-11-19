package cn.tesseract.union.accessor;

import cn.tesseract.union.asm.Accessor;
import com.corrodinggames.rts.union.game.class_324;
import com.corrodinggames.rts.union.gameFramework.n.class_1255;

@Accessor.Target(class_1255.class)
public interface MapTriggerAccessor {
    float get_baseSize();

    void set_baseSize(float f);

    class_324 get_currentPlayer();

    void set_currentPlayer(class_324 c);
}