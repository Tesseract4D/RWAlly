package cn.tesseract.union.hook;

import cn.tesseract.union.api.rusted.RustedNetwork;
import cn.tesseract.union.asm.Hook;
import cn.tesseract.union.asm.ReturnCondition;
import com.corrodinggames.rts.union.game.Player;
import com.corrodinggames.rts.union.gameFramework.GameEngine;
import com.corrodinggames.rts.union.gameFramework.j.NetworkEngine;
import com.corrodinggames.rts.union.gameFramework.j.class_1021;
import com.corrodinggames.rts.union.gameFramework.j.class_1037;

public class ResyncHook {
    @Hook(returnCondition = ReturnCondition.ON_TRUE)
    public static boolean method_2752(NetworkEngine c, String str, boolean z) {
        return RustedNetwork.delayedSync;
    }

    @Hook(injector = "simple:method_2809")
    public static void method_2759(NetworkEngine c, float f) {
        RustedNetwork.delayedSync = false;
    }

    @Hook(injector = "exit")
    public static void method_2842(class_1021 c) {
        if (RustedNetwork.delayedSync) GameEngine.get().field_6352.field_5915.field_6036 = 0;
    }

    @Hook(returnCondition = ReturnCondition.ON_TRUE)
    public static boolean method_2741(NetworkEngine c, class_1037 conn, Player player, String str, String msg) {
        return RustedNetwork.delayedSync && msg.startsWith("desync:");
    }

    @Hook
    public static void method_2720(NetworkEngine c) {
        RustedNetwork.delayedSync = false;
    }
}