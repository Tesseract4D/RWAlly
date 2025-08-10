package cn.tesseract.union.hook;

import android.os.Bundle;
import android.widget.Toast;
import cn.tesseract.union.api.Files;
import cn.tesseract.union.api.Network;
import cn.tesseract.union.api.Union;
import cn.tesseract.union.asm.Hook;
import cn.tesseract.union.asm.ReturnCondition;
import cn.tesseract.union.util.CallbackInfo;
import com.corrodinggames.rts.union.appFramework.MainMenuActivity;
import com.corrodinggames.rts.union.appFramework.MultiplayerBattleroomActivity;
import com.corrodinggames.rts.union.game.Player;
import com.corrodinggames.rts.union.game.class_317;
import com.corrodinggames.rts.union.gameFramework.GameEngine;
import com.corrodinggames.rts.union.gameFramework.class_898;
import com.corrodinggames.rts.union.gameFramework.j.NetworkEngine;
import com.corrodinggames.rts.union.gameFramework.j.class_1032;
import com.corrodinggames.rts.union.gameFramework.j.class_1037;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

import java.io.InputStreamReader;
import java.util.HashMap;

public class ScriptHook {
    public static Context CONTEXT = Context.enter();
    private static HashMap<String, Scriptable> SCOPES = new HashMap<>();
    private static String lastMap;

    public static Scriptable getScope(String id) {
        Scriptable scope = SCOPES.get(id);
        if (scope == null) {
            scope = CONTEXT.initStandardObjects();
            SCOPES.put(id, scope);
        }
        return scope;
    }

    public static void call(String name, Object... args) {
        Context cx = Context.enter();
        cx.setOptimizationLevel(-1);
        SCOPES.forEach((id, scope) -> {
            if (scope.get(name, scope) instanceof Function func) {
                try {
                    func.call(cx, scope, scope, args);
                } catch (Throwable e) {
                    Network.message("在执行 " + id + " 时发生错误，日志保存在 union.log 中!");
                    Files.log(GameEngine.method_3011(e));
                }
            }
        });
        cx.close();
    }

    @Hook(targetMethod = "method_2741")
    public static void onChat(NetworkEngine c, class_1037 conn, Player player, String str, String msg) {
        call("onChat", player, msg);
    }

    @Hook(targetMethod = "method_2897", injector = "simple:method_2901,0")
    public static void onDisconnect(class_1037 c, boolean z, boolean z2, String str) {
        call("onDisconnect", c);
    }

    @Hook(targetMethod = "method_425", injector = "exit")
    public static void onTick(class_317 c, float f) {
        call("onTick", Union.getGameEngine().field_6379);
    }

    @Hook(targetMethod = "method_2720")
    public static void onStartGame(NetworkEngine c) {
        call("onStartGame");
    }

    @Hook(targetMethod = "readInterfaceIntoNetworkSettings", injector = "simple:values")
    public static void onSwitchMap(MultiplayerBattleroomActivity c) {
        String map = Union.getNetworkEngine().field_5874.field_6013;
        if (!map.equals(lastMap)) {
            call("onSwitchMap", map);
            lastMap = map;
        }
    }

    @Hook(targetMethod = "method_2734", returnCondition = ReturnCondition.ON_TRUE)
    public static boolean onAction(NetworkEngine c, class_898 action) {
        if (!c.field_5851) return false;
        CallbackInfo ci = new CallbackInfo();
        call("onAction", action, ci);
        return ci.isPrevented();
    }

    @Hook(targetMethod = "method_2737", injector = "simple:method_3076,0")
    public static void onPacket(NetworkEngine c, class_1032 packet) {
        call("onPacket", packet);
    }

    public static void onJoin(class_1032 packet) {
        call("onJoin", packet.field_6123);
    }

    @Hook
    public static void onCreate(MainMenuActivity c, Bundle bundle) {
        CONTEXT.setOptimizationLevel(-1);
        if (!Files.dirExists("scripts")) {
            Files.mkdir("scripts");
        }
        String[] scripts = Files.listFiles("scripts");
        for (String script : scripts) {
            int i = script.indexOf('/');
            if (i != -1) {
                script = script.substring(i + 1);
            }
            if (script.endsWith(".js")) {
                try {
                    Scriptable scope = getScope(script);
                    scope.put("id", scope, script);
                    CONTEXT.evaluateReader(scope, new InputStreamReader(Files.getInputStream("scripts/" + script)), script, 0, null);
                } catch (Throwable e) {
                    Toast.makeText(c, "在加载 " + script + " 时发生错误，日志保存在 union.log 中!", 1).show();
                    Files.log(e.toString());
                }
            }
        }
    }
}