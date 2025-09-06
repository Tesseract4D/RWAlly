package cn.tesseract.union.hook;

import android.graphics.Paint;
import cn.tesseract.union.asm.Hook;
import cn.tesseract.union.asm.ReturnCondition;
import cn.tesseract.union.util.RangeType2Button;
import cn.tesseract.union.util.RangeTypeButton;
import com.corrodinggames.rts.union.game.Player;
import com.corrodinggames.rts.union.game.units.MovementType;
import com.corrodinggames.rts.union.game.units.Unit;
import com.corrodinggames.rts.union.gameFramework.GameEngine;
import com.corrodinggames.rts.union.gameFramework.class_907;
import com.corrodinggames.rts.union.gameFramework.f.class_961;
import com.corrodinggames.rts.union.gameFramework.utility.class_1294;

public class RangeHook {
    public static Paint red = new Paint(Unit.field_2007);
    public static Paint green = new Paint(Unit.field_2007);
    public static Paint yellow = new Paint(Unit.field_2007);
    public static Paint gray = new Paint(Unit.field_2007);

    static {
        red.setColor(class_907.method_2292(200, 183, 44, 44));
        green.setColor(class_907.method_2292(200, 0, 150, 0));
        yellow.setColor(class_907.method_2292(200, 150, 150, 0));
        gray.setColor(-1);
    }

    @Hook(returnCondition = ReturnCondition.ON_TRUE)
    public static boolean method_3505(class_1294 c, Unit ce) {
        int pt = GameEngine.get().userPlayer.team;
        int ut = ce.player.team;
        MovementType type = ce.getMovementType();

        return ce.hp > 0 && switch (RangeTypeButton.type) {
            case 1 -> ut >= 0 && ut != pt;
            case 2 -> ut == pt;
            case 3 -> true;
            default -> false;
        } && switch (RangeType2Button.type) {
            case 1 -> type == MovementType.BUILDING || type == MovementType.NONE;
            case 2 -> type != MovementType.BUILDING && type != MovementType.NONE;
            default -> true;
        };
    }

    /*@Hook
    public static void method_3240(class_1224 c, float float1, float float2, float float3, Paint paint) {
        Network.message(Arrays.toString(Thread.currentThread().getStackTrace()));
    }*/

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void method_3510(class_1294 c, Unit ce, float float2, boolean boolean3, boolean boolean4) {
        GameEngine game = GameEngine.get();
        if (class_1294.method_3505(ce) || boolean3) {
            float f = ce.x;
            float f2 = game.field_6429;
            float f3 = ce.y;
            float f4 = game.field_6430;
            Paint paint;

            Player p = GameEngine.get().userPlayer;
            if (ce.player == p)
                paint = green;
            else if (ce.player.team == p.team) {
                paint = yellow;
            } else if (ce.player.team < 0) {
                paint = gray;
            } else paint = red;

            game.field_6342.method_3240(f - f2, f3 - f4, float2, paint);
        }
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void method_1777(Unit c, float float1) {
        if (!c.field_1925 && c.field_1950 == null && c.field_1943) {
            GameEngine GameEngineVarget = GameEngine.get();
            if (c.player == GameEngineVarget.userPlayer || class_961.method_2556(c)) {
                if (GameEngineVarget.field_6345.showUnitWaypoints && GameEngineVarget.field_6481 <= 40) {
                    GameEngineVarget.field_6481++;
                    c.method_896();
                }
                c.method_938();
            }
        }
        if (class_1294.method_3505(c)) {
            c.method_939();
        }
    }
    
    /*@Hook(returnCondition = ReturnCondition.ALWAYS)
    public static boolean method_943(Unit c) {
        return true;
    }*/
}
