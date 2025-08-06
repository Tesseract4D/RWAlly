package cn.tesseract.union.hook;

import android.graphics.Paint;
import cn.tesseract.union.api.Union;
import cn.tesseract.union.asm.Hook;
import cn.tesseract.union.asm.ReturnCondition;
import com.corrodinggames.rts.union.game.class_324;
import com.corrodinggames.rts.union.game.units.class_426;
import com.corrodinggames.rts.union.gameFramework.class_1061;
import com.corrodinggames.rts.union.gameFramework.class_907;
import com.corrodinggames.rts.union.gameFramework.f.class_961;
import com.corrodinggames.rts.union.gameFramework.utility.class_1294;

public class RangeHook {
    public static Paint red = new Paint(class_426.field_2007);
    public static Paint green = new Paint(class_426.field_2007);
    public static Paint yellow = new Paint(class_426.field_2007);
    public static Paint gray = new Paint(class_426.field_2007);

    static {
        red.setColor(class_907.method_2292(200, 183, 44, 44));
        green.setColor(class_907.method_2292(200, 0, 150, 0));
        yellow.setColor(class_907.method_2292(200, 150, 150, 0));
        gray.setColor(-1);
    }
    /*@Hook(injector = "line:4263")
    public static void method_429(class_317 c, float float1, @Hook.LocalVariable(10) class_773 unit) {
        if (unit instanceof class_426 u) {
            unit.method_1773(float1);
        }
    }*/

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static boolean method_3505(class_1294 c, class_426 ce) {
        return true;
    }

    /*@Hook
    public static void method_3240(class_1224 c, float float1, float float2, float float3, Paint paint) {
        Network.message(Arrays.toString(Thread.currentThread().getStackTrace()));
    }*/

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void method_3510(class_1294 c, class_426 ce, float float2, boolean boolean3, boolean boolean4) {
        class_1061 game = class_1061.method_3076();
        if (class_1294.method_3505(ce) || boolean3) {
            float f = ce.field_4227;
            float f2 = game.field_6429;
            float f3 = ce.field_4228;
            float f4 = game.field_6430;
            Paint paint;

            class_324 p = Union.getGameEngine().field_6373;
            if (ce.field_1927 == p)
                paint = green;
            else if (ce.field_1927.field_1464 == p.field_1464) {
                paint = yellow;
            } else if (ce.field_1927.field_1464 < 0) {
                paint = gray;
            } else paint = red;

            game.field_6342.method_3240(f - f2, f3 - f4, float2, paint);
        }
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void method_1777(class_426 c, float float1) {
        if (!c.field_1925 && c.field_1950 == null && c.field_1943) {
            class_1061 class_1061VarMethod_3076 = class_1061.method_3076();
            if (c.field_1927 == class_1061VarMethod_3076.field_6373 || class_961.method_2556(c)) {
                if (class_1061VarMethod_3076.field_6345.showUnitWaypoints && class_1061VarMethod_3076.field_6481 <= 40) {
                    class_1061VarMethod_3076.field_6481++;
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
    public static boolean method_943(class_426 c) {
        return true;
    }*/
}
