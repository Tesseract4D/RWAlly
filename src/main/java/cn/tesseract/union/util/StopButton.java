package cn.tesseract.union.util;

import cn.tesseract.union.api.Union;
import com.corrodinggames.rts.union.game.units.Unit;
import com.corrodinggames.rts.union.game.units.a.PlayerAction;
import com.corrodinggames.rts.union.game.units.a.UnitAction;
import com.corrodinggames.rts.union.game.units.a.class_349;
import com.corrodinggames.rts.union.game.units.class_409;
import com.corrodinggames.rts.union.game.units.WaypointInfo;
import com.corrodinggames.rts.union.gameFramework.a.class_761;
import com.corrodinggames.rts.union.gameFramework.GameEngine;
import com.corrodinggames.rts.union.gameFramework.class_773;
import com.corrodinggames.rts.union.gameFramework.Action;
import com.corrodinggames.rts.union.gameFramework.f.class_961;


public class StopButton extends class_349 {
    public StopButton() {
        super("c_stop");
    }

    public final String method_588() {
        return "使选择的单位停止";
    }

    public final String method_600() {
        return "停止单位";
    }

    public final boolean method_607(Unit ce, boolean boolean2) {
        GameEngine game = GameEngine.get();
        Action m = class_961.method_2560();
        m.field_5097 = true;
        method_2522(m);
        game.field_6340.method_1749(class_761.field_4169, 0.2f);
        return true;
    }

    //class_961
    private static void method_2522(Action e) {
        for (Object o : class_773.field_4230) {
            class_773 class_773Var = (class_773) o;
            if (class_773Var instanceof class_409 i) {
                if (i.field_1943 && class_961.method_2556(i)) {
                    e.method_2139(i);
                }
            }
        }
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