package cn.tesseract.union.api.event;

import cn.tesseract.union.api.rusted.RustedUnit;

public class UnitActionEvent {
    public final RustedUnit unit;
    public final RustedUnit target;
    public final String type;

    public UnitActionEvent(RustedUnit unit, RustedUnit target, String type) {
        this.unit = unit;
        this.target = target;
        this.type = type;
    }
}
