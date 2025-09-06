package cn.tesseract.union.api.rusted;

import android.graphics.PointF;
import cn.tesseract.union.accessor.ActionAccessor;
import cn.tesseract.union.accessor.UnitAccessor;
import com.corrodinggames.rts.union.gameFramework.Action;

import java.util.ArrayList;
import java.util.List;

public class RustedAction extends RustedWrapper<Action> {
    public RustedAction(Action instance) {
        super(instance);
    }

    public static RustedAction warp(Action inner) {
        return inner == null ? null : ((ActionAccessor) inner).get_wrapper();
    }

    public String getName() {
        return inner.id.name;
    }

    public RustedPlayer getPlayer() {
        return RustedPlayer.warp(inner.player);
    }

    public RustedWaypoint getWaypoint() {
        return RustedWaypoint.warp(inner.waypoint);
    }

    public List<RustedUnit> getControlledUnits() {
        ArrayList<RustedUnit> list = new ArrayList<>();
        if (inner.field_5081.isEmpty()) inner.field_5104.forEach(u -> list.add(((UnitAccessor) u).get_wrapper()));
        else ((List<Long>) inner.field_5081).forEach(u -> {
            var unit = RustedGame.getUnitById(u);
            if (unit != null) list.add(unit);
        });
        return list;
    }

    public PointF getPos() {
        return inner.pos;
    }

    public PointF getEndPos() {
        return inner.field_5096;
    }
}
