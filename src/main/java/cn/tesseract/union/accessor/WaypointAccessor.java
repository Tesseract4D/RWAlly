package cn.tesseract.union.accessor;

import cn.tesseract.union.api.rusted.RustedConnection;
import cn.tesseract.union.api.rusted.RustedWaypoint;
import cn.tesseract.union.asm.Accessor;
import com.corrodinggames.rts.union.game.units.Waypoint;
import com.corrodinggames.rts.union.gameFramework.j.class_1037;

@Accessor.Target(Waypoint.class)
public interface WaypointAccessor {
    RustedWaypoint get_wrapper();

    void set_wrapper(RustedWaypoint wrapper);
}
