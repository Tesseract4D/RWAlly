package cn.tesseract.union.api.event;

import cn.tesseract.union.api.rusted.RustedUnit;

public class UnitRemovedEvent extends Event {
    public final RustedUnit unit;

    public UnitRemovedEvent(RustedUnit unit) {
        this.unit = unit;
    }
}
