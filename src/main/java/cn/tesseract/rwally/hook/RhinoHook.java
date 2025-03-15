package cn.tesseract.rwally.hook;

import android.os.Bundle;
import android.widget.Toast;
import cn.tesseract.dragonfly.asm.Hook;
import cn.tesseract.rwally.util.FileHelper;
import cn.tesseract.rwally.util.LogHelper;
import cn.tesseract.rwally.util.RWHelper;
import com.corrodinggames.rts.ally.appFramework.MainMenuActivity;
import com.corrodinggames.rts.ally.game.class_315;
import com.corrodinggames.rts.ally.game.class_416;
import com.corrodinggames.rts.ally.gameFramework.j.class_1054;
import com.corrodinggames.rts.ally.gameFramework.j.class_1101;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

import java.io.InputStreamReader;
import java.util.HashMap;

public class RhinoHook {
    public static Context CONTEXT = Context.enter();
    public static HashMap<String, Scriptable> SCOPE_MGR = new HashMap<>();
    private static String currentScript;

    public static Scriptable getScope(String id) {
        Scriptable scope = SCOPE_MGR.get(id);
        if (scope == null) {
            scope = CONTEXT.initStandardObjects();
            SCOPE_MGR.put(id, scope);
        }
        return scope;
    }

    public static void call(String name, Object... args) {
        SCOPE_MGR.forEach((id, scope) -> {
            if (scope.get(name, scope) instanceof Function func) {
                try {
                    func.call(CONTEXT, scope, scope, args);
                } catch (Throwable e) {
                    RWHelper.sendSysMessage("在执行 " + id + " 时发生错误，日志保存在 rwally.log 中!");
                    LogHelper.log(e.toString());
                }
            }
        });
    }

    public static String getCurrentScript() {
        return currentScript;
    }

    @Hook(targetMethod = "b")
    public static void onChat(class_1101 c, class_1054 conn, class_315 player, String str, String msg) {
        call("onChat", player, msg);
    }

    @Hook(targetMethod = "b", injector = "exit")
    public static void onTick(class_416 c, float f) {
        call("onTick", RWHelper.getGameEngine().bv);
    }

    @Hook(targetMethod = "V")
    public static void onStartGame(class_1101 c) {
        call("onStartGame");
    }

    @Hook(targetMethod = "s", injector = "exit")
    public static void onSwitchMap(class_1101 c) {
        call("onSwitchMap", c.aA.b);
    }

    @Hook
    public static void onCreate(MainMenuActivity c, Bundle bundle) {
        CONTEXT.setOptimizationLevel(-1);

        if (!FileHelper.dirExists("js"))
            FileHelper.mkdir("js");
        String[] scripts = FileHelper.listFiles("js");
        for (String script : scripts) {
            int i = script.indexOf('/');
            if (i != -1)
                script = script.substring(i + 1);
            if (script.endsWith(".js"))
                try {
                    currentScript = script;
                    CONTEXT.evaluateReader(getScope(script), new InputStreamReader(FileHelper.getInputStream("js/" + script)), script, 0, null);
                } catch (Throwable e) {
                    Toast.makeText(c, "在加载 " + script + " 时发生错误，日志保存在 rwally.log 中!", Toast.LENGTH_LONG).show();
                    LogHelper.log(e.toString());
                }
        }
        currentScript = "done";

        /*WebView wv = new WebView(c);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        wv.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                CONTEXT.evaluateString(SCOPE, message, url, 0, null);
                return true;
            }
        });
        wv.loadUrl("http://tesseract.byethost24.com/rw/rhino.html");*/
    }
}
