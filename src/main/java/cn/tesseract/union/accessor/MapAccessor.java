package cn.tesseract.union.accessor;

import cn.tesseract.union.api.rusted.RustedMap;
import cn.tesseract.union.api.rusted.RustedWaypoint;
import cn.tesseract.union.asm.Accessor;
import com.corrodinggames.rts.union.game.b.GameMap;
import com.corrodinggames.rts.union.game.units.Waypoint;

@Accessor.Target(GameMap.class)
public interface MapAccessor {
    RustedMap get_wrapper();

    void set_wrapper(RustedMap wrapper);
}
