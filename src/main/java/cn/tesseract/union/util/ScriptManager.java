package cn.tesseract.union.util;

import com.corrodinggames.rts.union.gameFramework.class_1061;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

import java.io.InputStreamReader;
import java.util.HashMap;

public class ScriptManager {
    public static final HashMap<String, Scriptable> scopes = new HashMap<>();

    public static Context getContext() {
        Context context = Context.enter();
        context.setOptimizationLevel(-1);
        context.setLanguageVersion(Context.VERSION_ES6);
        return context;
    }

    public static Scriptable getScope(String id) {
        Scriptable scope = scopes.get(id);
        if (scope == null) {
            scope = getContext().initStandardObjects();
            scope.put("Union", scope, new ScriptUnion(scope));
            scopes.put(id, scope);
        }
        return scope;
    }

    public static void call(String name, Object... args) {
        Context cx = Context.enter();
        cx.setOptimizationLevel(-1);
        scopes.forEach((id, scope) -> {
            if (scope.get(name, scope) instanceof Function func) {
                try {
                    func.call(cx, scope, scope, args);
                } catch (Throwable e) {
                    GameHelper.toast("在执行 " + id + " 时发生错误，日志保存在 union.log 中!");
                    FileHelper.log(class_1061.method_3011(e));
                }
            }
        });
        cx.close();
    }

    public static void reload() {
        //Scriptable trigger = SCOPES.get("TRIGGER");
        scopes.clear();
        //if (trigger != null) SCOPES.put("TRIGGER", trigger);
        if (!FileHelper.dirExists("scripts")) {
            FileHelper.mkdir("scripts");
        }
        int count = 0;
        boolean error = false;
        String[] scripts = FileHelper.listFiles("scripts");
        for (String script : scripts) {
            int i = script.indexOf('/');
            if (i != -1) {
                script = script.substring(i + 1);
            }
            if (script.endsWith(".js")) {
                try {
                    Scriptable scope = ScriptManager.getScope(script);
                    scope.put("id", scope, script);
                    getContext().evaluateReader(scope, new InputStreamReader(FileHelper.getInputStream("scripts/" + script)), script, 0, null);
                    count++;
                } catch (Exception e) {
                    GameHelper.toast("在加载 " + script + " 时发生错误，日志保存在 union.log 中");
                    FileHelper.log(e.toString());
                    error = true;
                }
            }
        }
        if (count != 0 && !error) GameHelper.toast("成功加载了 " + count + " 个脚本");
        call("init");
    }
}
