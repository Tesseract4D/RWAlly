package cn.tesseract.union.util;

import android.view.KeyEvent;
import cn.tesseract.union.api.Union;
import com.corrodinggames.rts.union.game.units.a.class_349;
import com.corrodinggames.rts.union.game.units.a.UnitAction;
import com.corrodinggames.rts.union.game.units.a.PlayerAction;
import com.corrodinggames.rts.union.game.units.Unit;
import com.corrodinggames.rts.union.game.units.WaypointInfo;


public class ShiftButton extends class_349 {
    public boolean disabled = true;

    public ShiftButton() {
        super("c_shift");
    }

    public String method_588() {
        return "按下后可以进行连续指令";
    }

    public String method_600() {
        return "连续" + (disabled ? "禁用" : "启用");
    }

    public final boolean method_607(Unit ce, boolean boolean2) {
        disabled = !disabled;
        if (disabled) GameEngine.get().method_3027(59, new KeyEvent(1, 59));
        else GameEngine.get().method_2992(59, new KeyEvent(0, 59));
        return true;
    }

    public final WaypointInfo method_616() {
        return null;
    }

    public final int method_603(Unit ce, boolean boolean2) {
        return -1;
    }

    public final int method_604() {
        return 0;
    }

    public final PlayerAction method_608() {
        return PlayerAction.none;
    }

    public final UnitAction method_610() {
        return UnitAction.none;
    }

    public final boolean method_612() {
        return false;
    }

    public final boolean method_634() {
        return true;
    }

    public final float method_624() {
        return 1f;
    }

    public final boolean method_614() {
        return true;
    }

    public final boolean method_635() {
        return true;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof class_349 c) {
            return Float.compare(method_636() - c.method_636(), 0);
        }
        return 0;
    }
}