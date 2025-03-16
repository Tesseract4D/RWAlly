package cn.tesseract.rwally.hook;

import android.os.Bundle;
import android.util.Pair;
import android.widget.Toast;
import cn.tesseract.dragonfly.asm.Hook;
import cn.tesseract.dragonfly.asm.ReturnCondition;
import cn.tesseract.rwally.util.CallbackInfo;
import cn.tesseract.rwally.util.FileHelper;
import cn.tesseract.rwally.util.LogHelper;
import cn.tesseract.rwally.util.RWHelper;
import com.corrodinggames.rts.ally.appFramework.MainMenuActivity;
import com.corrodinggames.rts.ally.appFramework.MultiplayerBattleroomActivity;
import com.corrodinggames.rts.ally.game.class_315;
import com.corrodinggames.rts.ally.game.class_416;
import com.corrodinggames.rts.ally.gameFramework.class_340;
import com.corrodinggames.rts.ally.gameFramework.class_945;
import com.corrodinggames.rts.ally.gameFramework.j.class_1033;
import com.corrodinggames.rts.ally.gameFramework.j.class_1054;
import com.corrodinggames.rts.ally.gameFramework.j.class_1101;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class RhinoHook {
    public static Context CONTEXT = Context.enter();
    public static HashMap<String, Scriptable> SCOPE_MGR = new HashMap<>();
    private static ArrayList<Pair<String, Object[]>> TASKS = new ArrayList<>();
    private static String currentScript;

    static {
        new Thread(() -> {
            Context taskContext = Context.enter();
            while (true) {
                for (Pair<String, Object[]> task : TASKS) {
                    SCOPE_MGR.forEach((id, scope) -> {
                        if (scope.get(task.first, scope) instanceof Function func) {
                            try {
                                func.call(taskContext, scope, scope, task.second);
                            } catch (Throwable e) {
                                RWHelper.message("在执行 " + id + " 时发生错误，日志保存在 rwally.log 中!");
                                LogHelper.log(class_340.a(e));
                            }
                        }
                    });
                }
                TASKS.clear();
            }
        }).start();
    }

    public static Scriptable getScope(String id) {
        Scriptable scope = SCOPE_MGR.get(id);
        if (scope == null) {
            scope = CONTEXT.initStandardObjects();
            SCOPE_MGR.put(id, scope);
        }
        return scope;
    }

    public static void call(String name, Object... args) {
        TASKS.add(new Pair<>(name, args));
    }

    public static String getCurrentScript() {
        return currentScript;
    }

    @Hook(targetMethod = "b")
    public static void onChat(class_1101 c, class_1054 conn, class_315 player, String str, String msg) {
        call("onChat", player, msg);
    }

    @Hook(targetMethod = "a", injector = "simple:c,0")
    public static void onDisconnect(class_1054 c, boolean z, boolean z2, String str) {
        call("onDisconnect", c);
    }

    @Hook(targetMethod = "b", injector = "exit")
    public static void onTick(class_416 c, float f) {
        call("onTick", RWHelper.getGameEngine().bv);
    }

    @Hook(targetMethod = "V")
    public static void onStartGame(class_1101 c) {
        call("onStartGame");
    }

    private static String lastMap;

    @Hook(targetMethod = "readInterfaceIntoNetworkSettings", injector = "simple:values")
    public static void onSwitchMap(MultiplayerBattleroomActivity c) {
        String map = RWHelper.getNetworkEngine().aA.b;
        if (!map.equals(lastMap)) {
            call("onSwitchMap", map);
            lastMap = map;
        }
    }

    @Hook(targetMethod = "a", returnCondition = ReturnCondition.ON_TRUE)
    public static boolean onAction(class_1101 c, class_945 action) {
        CallbackInfo ci = new CallbackInfo();
        call("onAction", action, ci);
        return ci.isPrevented();
    }

    @Hook(targetMethod = "a", injector = "simple:t,0")
    public static void onPacket(class_1101 c, class_1033 packet) {
        call("onPacket", packet);
    }

    @Hook(createMethod = true)
    public static void onJoin(class_1101 c, class_1054 conn) {
        call("onJoin", conn);
    }

    @Hook
    public static void onCreate(MainMenuActivity c, Bundle bundle) {
        CONTEXT.setOptimizationLevel(-1);

        if (!FileHelper.dirExists("scripts"))
            FileHelper.mkdir("scripts");
        String[] scripts = FileHelper.listFiles("scripts");
        for (String script : scripts) {
            int i = script.indexOf('/');
            if (i != -1)
                script = script.substring(i + 1);
            if (script.endsWith(".js"))
                try {
                    currentScript = script;
                    Scriptable scope = getScope(script);
                    scope.put("id", scope, script);
                    CONTEXT.evaluateReader(scope, new InputStreamReader(FileHelper.getInputStream("scripts/" + script)), script, 0, null);
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
