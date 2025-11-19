package cn.tesseract.union.hook;

import cn.tesseract.union.util.NetHelper;
import cn.tesseract.union.asm.Hook;
import cn.tesseract.union.asm.ReturnCondition;
import com.corrodinggames.rts.union.game.class_324;
import com.corrodinggames.rts.union.gameFramework.class_1061;
import com.corrodinggames.rts.union.gameFramework.j.class_1001;
import com.corrodinggames.rts.union.gameFramework.j.class_1021;
import com.corrodinggames.rts.union.gameFramework.j.class_1037;

public class ResyncHook {
    @Hook(returnCondition = ReturnCondition.ON_TRUE)
    public static boolean method_2752(class_1001 c, String str, boolean z) {
        return NetHelper.delayedSync;
    }

    @Hook(injector = "simple:method_2809")
    public static void method_2759(class_1001 c, float f) {
        NetHelper.delayedSync = false;
    }

    @Hook(injector = "exit")
    public static void method_2842(class_1021 c) {
        if (NetHelper.delayedSync) class_1061.method_3076().field_6352.field_5915.field_6036 = 0;
    }

    @Hook(returnCondition = ReturnCondition.ON_TRUE)
    public static boolean method_2741(class_1001 c, class_1037 conn, class_324 class_324, String str, String msg) {
        return NetHelper.delayedSync && msg.startsWith("desync:");
    }

    @Hook
    public static void method_2720(class_1001 c) {
        NetHelper.delayedSync = false;
    }
}