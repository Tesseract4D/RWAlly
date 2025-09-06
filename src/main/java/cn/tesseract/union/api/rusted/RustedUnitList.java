package cn.tesseract.union.api.rusted;

import com.corrodinggames.rts.union.game.units.Unit;
import com.corrodinggames.rts.union.gameFramework.utility.class_1331;

import java.util.AbstractList;

public class RustedUnitList extends AbstractList<RustedUnit> {
    public final class_1331 inner;

    public RustedUnitList(class_1331 inner) {
        this.inner = inner;
    }

    public RustedUnit set(int index, RustedUnit element) {
        if (inner.set(index, element.inner) instanceof Unit u)
            return RustedUnit.warp(u);
        return null;
    }

    public RustedUnit get(int index) {
        if (inner.get(index) instanceof Unit u)
            return RustedUnit.warp(u);
        return null;
    }

    public RustedUnit remove(int index) {
        if (inner.remove(index) instanceof Unit u)
            return RustedUnit.warp(u);
        return null;
    }

    public int indexOf(Object o) {
        if (o instanceof RustedUnit r)
            return inner.indexOf(r.inner);
        return -1;
    }

    public int lastIndexOf(Object o) {
        if (o instanceof RustedUnit r)
            return inner.lastIndexOf(r.inner);
        return -1;
    }

    public int size() {
        return inner.size();
    }

    public boolean isEmpty() {
        return inner.isEmpty();
    }

    public boolean contains(Object o) {
        if (o instanceof Unit u)
            return inner.contains(RustedUnit.warp(u));
        return false;
    }

    public String toString() {
        return inner.toString();
    }
}
