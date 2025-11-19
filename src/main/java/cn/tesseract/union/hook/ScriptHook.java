package cn.tesseract.union.hook;

import android.os.Bundle;
import cn.tesseract.union.util.ScriptManager;
import cn.tesseract.union.asm.Hook;
import com.corrodinggames.rts.union.appFramework.MainMenuActivity;
import com.corrodinggames.rts.union.gameFramework.n.class_1273;

public class ScriptHook {
    @Hook
    public static void onCreate(MainMenuActivity c, Bundle bundle) {
        ScriptManager.reload();
    }

    @Hook
    public static void method_3447(class_1273 c, boolean boolean1) {
        ScriptManager.scopes.remove("TRIGGER");
    }
}