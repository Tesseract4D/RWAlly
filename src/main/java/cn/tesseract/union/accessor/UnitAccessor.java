package cn.tesseract.union.accessor;

import cn.tesseract.union.api.rusted.RustedUnit;
import cn.tesseract.union.asm.Accessor;
import com.corrodinggames.rts.union.game.units.Unit;

@Accessor.Target(Unit.class)
public interface UnitAccessor {
    RustedUnit get_wrapper();

    void set_wrapper(RustedUnit wrapper);
}
