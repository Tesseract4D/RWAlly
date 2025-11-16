package cn.tesseract.union.api.event;

import cn.tesseract.union.api.rusted.RustedUnit;

public class UnitDefeatedEvent extends Event {
    public final RustedUnit unit;

    public UnitDefeatedEvent(RustedUnit unit) {
        this.unit = unit;
    }
}
