package cn.tesseract.union.api.rusted;

import cn.tesseract.union.accessor.UnitAccessor;
import cn.tesseract.union.accessor.WaypointAccessor;
import com.corrodinggames.rts.union.game.units.Waypoint;

public class RustedWaypoint extends RustedWrapper<Waypoint> {
    public RustedWaypoint(Waypoint instance) {
        super(instance);
    }

    public static RustedWaypoint warp(Waypoint inner) {
        return inner == null ? null : ((WaypointAccessor) inner).get_wrapper();
    }

    public String getType() {
        return inner.type.toString();
    }

    public int getTechLevel() {
        return inner.techLevel;
    }

    public float getX() {
        return inner.x;
    }

    public float getY() {
        return inner.y;
    }

    public long getTargetId() {
        return inner.field_3933;
    }

    public RustedUnit getTargetUnit() {
        return RustedUnit.warp(inner.unit);
    }

    public String getBuildUnit() {
        return inner.info == null ? null : inner.info.getUnitName();
    }
}
