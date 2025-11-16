package cn.tesseract.union.hook;

import cn.tesseract.union.api.event.*;
import cn.tesseract.union.api.rusted.*;
import cn.tesseract.union.api.util.ScriptManager;
import cn.tesseract.union.asm.Hook;
import cn.tesseract.union.asm.ReturnCondition;
import com.corrodinggames.rts.union.game.Player;
import com.corrodinggames.rts.union.game.class_317;
import com.corrodinggames.rts.union.game.units.Unit;
import com.corrodinggames.rts.union.game.units.custom.class_471;
import com.corrodinggames.rts.union.gameFramework.Action;
import com.corrodinggames.rts.union.gameFramework.GameEngine;
import com.corrodinggames.rts.union.gameFramework.j.NetworkEngine;
import com.corrodinggames.rts.union.gameFramework.j.class_1037;

public class EventHook {
    private static String lastMap;

    @Hook(targetMethod = "method_2741", returnCondition = ReturnCondition.ON_TRUE)
    public static boolean onChat(NetworkEngine c, class_1037 conn, Player player, String str, String msg) {
        var event = new PlayerChatEvent(RustedPlayer.warp(player), RustedConnection.warp(conn), msg);
        ScriptManager.call("onChat", event);
        return event.isCanceled();
    }

    @Hook(targetMethod = "method_2897", injector = "simple:method_2901,0")
    public static void onDisconnect(class_1037 c, boolean z, boolean z2, String str) {
        ScriptManager.call("onDisconnect", new PlayerDisconnectEvent(RustedPlayer.warp(c.field_6142), RustedConnection.warp(c)));
    }

    @Hook(targetMethod = "method_425", injector = "exit")
    public static void onTick(class_317 c, float f) {
        ScriptManager.call("onTick", new TickEvent(GameEngine.get().field_6379));
    }

    @Hook(targetMethod = "method_2720")
    public static void onStartGame(NetworkEngine c) {
        for (int i = 0; i < RustedPlayer.getMaxTeamId(); i++) {
            RustedPlayer p = RustedPlayer.getPlayer(i);
            if (p != null) p.data.clear();
        }
        ScriptManager.call("onStartGame", new StartGameEvent());
    }

//    @Hook(targetMethod = "readInterfaceIntoNetworkSettings", injector = "simple:values")
//    public static void onSwitchMap(MultiplayerBattleroomActivity c) {
//        String map = GameEngine.get().field_6352.field_5874.field_6013;
//        if (!map.equals(lastMap)) {
//            ScriptHook.call("onSwitchMap", map);
//            lastMap = map;
//        }
//    }

    @Hook(targetMethod = "method_2734", returnCondition = ReturnCondition.ON_TRUE)
    public static boolean onAction(NetworkEngine c, Action action) {
        PlayerActionEvent event = new PlayerActionEvent(RustedPlayer.warp(action.player), RustedAction.warp(action));
        ScriptManager.call("onAction", event);
        return event.isCanceled();
    }

    @Hook
    public static void method_912(Unit c, class_471 af, Unit ce) {
        ScriptManager.call("onUnitAction", new UnitActionEvent(RustedUnit.warp(c), RustedUnit.warp(ce), af.name()));
    }

    @Hook
    public static void method_961(Unit unit) {
        ScriptManager.call("onUnitDefeated", new UnitDefeatedEvent(RustedUnit.warp(unit)));
    }

    @Hook
    public static void method_946(Unit unit) {
        ScriptManager.call("onUnitRemoved", new UnitRemovedEvent(RustedUnit.warp(unit)));
    }

    @Hook(createMethod = true)
    public static void onPlayerJoin(NetworkEngine c, class_1037 conn) {
        c.method_2765(conn);
        if (conn != null) {
            ScriptManager.call("onPlayerJoin", new PlayerJoinEvent(RustedPlayer.warp(conn.field_6142), RustedConnection.warp(conn)));
            RustedNetwork.get().sendMessage("""
                    这个房间使用了联盟版，能够提供包括但不限于屏蔽广告房，更多指令，拓展地图宾语的功能。
                    你可以从QQ群927263395群文件获取该版本。
                    作者：洗玻璃呀
                    """, RustedConnection.warp(conn));
        }
    }
}
