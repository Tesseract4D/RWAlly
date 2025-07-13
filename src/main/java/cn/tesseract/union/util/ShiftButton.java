package cn.tesseract.union.util;

import android.view.KeyEvent;
import com.corrodinggames.rts.union.game.units.a.class_346;
import com.corrodinggames.rts.union.game.units.a.class_349;
import com.corrodinggames.rts.union.game.units.class_426;
import com.corrodinggames.rts.union.gameFramework.class_780;

public class ShiftButton extends class_346 {
    boolean disabled = true;

    public ShiftButton() {
        super("shift");
    }

    public String method_588() {
        return "按下后可以进行连续指令";
    }

    public String method_600() {
        return "连续指令\n" + (disabled ? "禁用" : "启用");
    }

    public final class_780 method_583() {
        return null;
    }

    public final boolean method_607(class_426 ce, boolean boolean2) {
        disabled = !disabled;
        if (disabled) RWHelper.getGameEngine().method_3027(59, new KeyEvent(1, 59));
        else RWHelper.getGameEngine().method_2992(59, new KeyEvent(0, 59));
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