package cn.tesseract.union.accessor;

import cn.tesseract.union.api.rusted.RustedConnection;
import cn.tesseract.union.api.rusted.RustedUnit;
import cn.tesseract.union.asm.Accessor;
import com.corrodinggames.rts.union.game.units.Unit;
import com.corrodinggames.rts.union.gameFramework.j.class_1037;

@Accessor.Target(class_1037.class)
public interface ConnectionAccessor {
    RustedConnection get_wrapper();

    void set_wrapper(RustedConnection wrapper);
}
