package cn.tesseract.union.hook;

import cn.tesseract.union.accessor.PlayerAccessor;
import cn.tesseract.union.asm.Hook;
import cn.tesseract.union.asm.ReturnCondition;
import cn.tesseract.union.util.NetHelper;
import cn.tesseract.union.util.ScriptManager;
import com.corrodinggames.rts.union.game.class_317;
import com.corrodinggames.rts.union.game.class_324;
import com.corrodinggames.rts.union.game.units.class_426;
import com.corrodinggames.rts.union.game.units.custom.class_471;
import com.corrodinggames.rts.union.gameFramework.class_1061;
import com.corrodinggames.rts.union.gameFramework.class_898;
import com.corrodinggames.rts.union.gameFramework.j.class_1001;
import com.corrodinggames.rts.union.gameFramework.j.class_1037;

public class EventHook {
    public static boolean started;

    @Hook(targetMethod = "method_425", injector = "exit")
    public static void onTick(class_317 c, float f) {
        ScriptManager.call("onTick", class_1061.method_3076().field_6379);
    }

    @Hook(createMethod = true)
    public static void onPlayerJoin(class_1001 c, class_1037 conn) {
        c.method_2765(conn);
        if (conn != null) {
            ScriptManager.call("onConnect", conn);
            NetHelper.sendMessage("""
                    这个房间使用了铁锈战争联盟版
                    QQ群927263395  作者：洗玻璃呀
                    """, conn);
        }
    }

    @Hook(targetMethod = "method_2897", injector = "simple:method_2901,0")
    public static void onDisconnect(class_1037 c, boolean z, boolean z2, String str) {
        ScriptManager.call("onDisconnect", c);

    }

    @Hook(targetMethod = "method_2720")
    public static void onStartGame(class_1001 c) {
        started = true;
        ScriptManager.call("onStartGame");
    }

    @Hook(injector = "exit")
    public static void method_3014(class_317 c, boolean boolean1, boolean boolean2, int type) {
        if (NetHelper.isHost() && type == 2) {
            ScriptManager.call("onPostStartGame");
        }
    }

    @Hook(targetMethod = "method_2741", returnCondition = ReturnCondition.ON_TRUE)
    public static boolean onChat(class_1001 c, class_1037 conn, class_324 player, String str, String msg) {
        if (player == null) player = NetHelper.getHost();
        if (((PlayerAccessor) player).get_muted()) return true;
        var callback = new boolean[]{false};
        ScriptManager.call("onChat", conn, player, msg, callback);
        return callback[0];
    }

    @Hook(targetMethod = "method_2734", returnCondition = ReturnCondition.ON_TRUE)
    public static boolean onAction(class_1001 c, class_898 action) {
        var callback = new boolean[]{false};
        ScriptManager.call("onAction", action, callback);
        return callback[0];
    }

    @Hook
    public static void method_912(class_426 c, class_471 af, class_426 ce) {
        ScriptManager.call("onUnitAction", c, af, ce);
    }

    @Hook
    public static void method_961(class_426 unit) {
        ScriptManager.call("onUnitDefeated", unit);
    }

    @Hook
    public static void method_946(class_426 unit) {
        ScriptManager.call("onUnitRemoved", unit);
    }
}
