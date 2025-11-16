package cn.tesseract.union.hook;

import android.content.Context;
import cn.tesseract.union.accessor.*;
import cn.tesseract.union.api.rusted.*;
import cn.tesseract.union.asm.Hook;
import com.corrodinggames.rts.union.game.Player;
import com.corrodinggames.rts.union.game.b.GameMap;
import com.corrodinggames.rts.union.game.units.Unit;
import com.corrodinggames.rts.union.game.units.Waypoint;
import com.corrodinggames.rts.union.gameFramework.Action;
import com.corrodinggames.rts.union.gameFramework.ActionManager;
import com.corrodinggames.rts.union.gameFramework.GameEngine;
import com.corrodinggames.rts.union.gameFramework.j.NetworkEngine;
import com.corrodinggames.rts.union.gameFramework.j.class_1032;
import com.corrodinggames.rts.union.gameFramework.j.class_1037;
import com.corrodinggames.rts.union.gameFramework.n.class_1255;

import java.net.Socket;

public class WrapperHook {
    @Hook(targetMethod = "<init>")
    public static void init(NetworkEngine c) {
        ((NetworkAccessor) c).set_wrapper(new RustedNetwork(c));
    }

    @Hook(targetMethod = "<init>")
    public static void init(Player c) {
        ((PlayerAccessor) c).set_wrapper(new RustedPlayer(c));
    }

    @Hook(targetMethod = "<init>")
    public static void init(Unit c, boolean b) {
        ((UnitAccessor) c).set_wrapper(new RustedUnit(c));
    }

    @Hook(targetMethod = "<init>")
    public static void init(GameEngine c, Context context) {
        ((GameAccessor) c).set_wrapper(new RustedGame(c));
    }

    @Hook(targetMethod = "<init>")
    public static void init(class_1037 c, NetworkEngine ae, Socket socket) {
        ((ConnectionAccessor) c).set_wrapper(new RustedConnection(c));
    }

    @Hook(targetMethod = "<init>")
    public static void init(Waypoint c) {
        ((WaypointAccessor) c).set_wrapper(new RustedWaypoint(c));
    }

    @Hook(targetMethod = "<init>")
    public static void init(Action c, ActionManager mgr) {
        ((ActionAccessor) c).set_wrapper(new RustedAction(c));
    }

    @Hook(targetMethod = "<init>")
    public static void init(class_1032 c,int type) {
        ((PacketAccessor) c).set_wrapper(new RustedPacket(c));
    }

    @Hook(targetMethod = "<init>")
    public static void init(GameMap c) {
        ((MapAccessor) c).set_wrapper(new RustedMap(c));
    }

    @Hook(targetMethod = "<init>")
    public static void init(class_1255 c) {
        ((MapTriggerAccessor) c).set_wrapper(new RustedTrigger(c));
    }
}
