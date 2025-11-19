package cn.tesseract.union.button;

import android.view.KeyEvent;
import com.corrodinggames.rts.union.game.units.a.class_349;
import com.corrodinggames.rts.union.game.units.a.class_350;
import com.corrodinggames.rts.union.game.units.a.class_351;
import com.corrodinggames.rts.union.game.units.class_426;
import com.corrodinggames.rts.union.game.units.class_704;
import com.corrodinggames.rts.union.gameFramework.class_1061;


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

    public final boolean method_607(class_426 ce, boolean boolean2) {
        disabled = !disabled;
        if (disabled) class_1061.method_3076().method_3027(59, new KeyEvent(1, 59));
        else class_1061.method_3076().method_2992(59, new KeyEvent(0, 59));
        return true;
    }

    public final class_704 method_616() {
        return null;
    }

    public final int method_603(class_426 ce, boolean boolean2) {
        return -1;
    }

    public final int method_604() {
        return 0;
    }

    public final class_351 method_608() {
        return class_351.field_1567;
    }

    public final class_350 method_610() {
        return class_350.field_1557;
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