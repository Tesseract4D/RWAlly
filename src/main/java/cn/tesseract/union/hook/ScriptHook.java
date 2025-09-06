package cn.tesseract.union.hook;

import android.os.Bundle;
import cn.tesseract.union.api.util.ScriptManager;
import cn.tesseract.union.asm.Hook;
import com.corrodinggames.rts.union.appFramework.MainMenuActivity;

public class ScriptHook {
    @Hook
    public static void onCreate(MainMenuActivity c, Bundle bundle) {
        ScriptManager.reload();
    }
}