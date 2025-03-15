package cn.tesseract.rwally.hook;

import android.graphics.Canvas;
import android.os.Bundle;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;
import cn.tesseract.dragonfly.asm.Hook;
import cn.tesseract.dragonfly.asm.ReturnCondition;
import cn.tesseract.rwally.Reference;
import cn.tesseract.rwally.util.RWHelper;
import com.corrodinggames.rts.ally.appFramework.MainMenuActivity;
import com.corrodinggames.rts.ally.game.class_315;
import com.corrodinggames.rts.ally.gameFramework.m.class_1224;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MiscHook {
    @Hook
    public static void onCreate(MainMenuActivity c, Bundle bundle) {
        Reference.roomBlacklist.read();
    }

    @Hook(targetMethod = "<clinit>", injector = "exit")
    public static void clinit(class_315 c) {
        class_315.c = 30;
    }

    @Hook(targetClass = "com.corrodinggames.rts.ally.gameFramework.m.class_1195", returnCondition = ReturnCondition.ALWAYS)
    public static void a(Object c, Canvas canvas, class_1224 class_1224Var) {
        try {
            canvas.restore();
        } catch (IllegalStateException ignored) {
        }
    }
}