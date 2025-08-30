package cn.tesseract.union.accessor;

import cn.tesseract.union.api.rusted.RustedGame;
import cn.tesseract.union.asm.Accessor;
import com.corrodinggames.rts.union.gameFramework.GameEngine;

@Accessor.Target(GameEngine.class)
public interface GameAccessor {
    RustedGame get_wrapper();

    void set_wrapper(RustedGame wrapper);
}
