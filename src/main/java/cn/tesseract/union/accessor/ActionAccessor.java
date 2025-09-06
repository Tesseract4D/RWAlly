package cn.tesseract.union.accessor;

import cn.tesseract.union.api.rusted.RustedAction;
import cn.tesseract.union.api.rusted.RustedWaypoint;
import cn.tesseract.union.asm.Accessor;
import com.corrodinggames.rts.union.game.units.Waypoint;
import com.corrodinggames.rts.union.gameFramework.Action;

@Accessor.Target(Action.class)
public interface ActionAccessor {
    RustedAction get_wrapper();

    void set_wrapper(RustedAction wrapper);
}
