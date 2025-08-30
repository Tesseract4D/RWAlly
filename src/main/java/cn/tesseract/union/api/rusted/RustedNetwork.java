package cn.tesseract.union.api.rusted;

import cn.tesseract.union.accessor.NetworkAccessor;
import cn.tesseract.union.api.Union;
import com.corrodinggames.rts.union.game.Player;
import com.corrodinggames.rts.union.game.units.Waypoint;
import com.corrodinggames.rts.union.game.units.WaypointType;
import com.corrodinggames.rts.union.game.units.a.ActionId;
import com.corrodinggames.rts.union.game.units.class_431;
import com.corrodinggames.rts.union.gameFramework.Action;
import com.corrodinggames.rts.union.gameFramework.GameEngine;
import com.corrodinggames.rts.union.gameFramework.j.NetworkEngine;
import com.corrodinggames.rts.union.gameFramework.j.class_1030;
import com.corrodinggames.rts.union.gameFramework.j.class_1037;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RustedNetwork extends RustedWrapper<NetworkEngine> {
    private static RustedNetwork instance;

    public RustedNetwork(NetworkEngine object) {
        super(object);
        instance = this;
    }

    public static RustedNetwork get() {
        return instance;
    }

    public void sendTeamMessage(String message, int team) {
        ConcurrentLinkedQueue<class_1037> connections = object.connections;
        for (class_1037 conn : connections) {
            if (conn.field_6181 && !conn.field_6166 && conn.field_6142.team == team) {
                sendMessage(message, null, conn, -1);
            }
        }
        if (object.field_5848.team == team) {
            sendMessage(message, null);
        }
    }

    public void sendMessage(String message, int index) {
        ConcurrentLinkedQueue<class_1037> connections = object.connections;
        for (class_1037 conn : connections) {
            if (conn.field_6142 != null && conn.field_6142.teamId == index) {
                sendMessage(message, null, conn, -1);
            }
        }
        if (object.field_5848 != null && object.field_5848.teamId == index) {
            sendMessage(message, null);
        }
    }

    public void setMaxPlayer(int n) {
        Player.setMaxTeamId(n, true);
    }

    public void message(String message) {
        sendMessage(message, null);
    }

    public void sendChat(String message) {
        object.method_2816(message);
    }

    public void sendMessage(String message) {
        object.method_2809(message);
    }

    public void sendMessage(String message, class_1037 conn) {
        ((NetworkAccessor) object).invoke_method_2750(message, conn);
    }

    public void sendMessage(String message, String sender, class_1037 conn) {
        sendMessage(message, sender, conn, -1);
    }

    public void sendMessage(String message, String sender, class_1037 conn, int color) {
        class_1030 pk = new class_1030();
        pk.method_2880(message);
        pk.method_2877(3);
        pk.method_2871(sender);
        pk.method_2865(conn);
        pk.method_2883(color);
        conn.method_2894(pk.method_2856(141));
    }

    public void receiveMessage(String sender, String message, int color) {
        ((NetworkAccessor) object).invoke_method_2740(null, color, sender, message);
    }

    public boolean isInGame() {
        return object.field_5898;
    }

    public void sync() {
        NetworkEngine c = object;
        Iterator it = c.connections.iterator();
        while (it.hasNext()) {
            Object o = it.next();
            class_1037 p = (class_1037) o;
            p.field_6187 = true;
            p.field_6188 = true;
        }
    }

    public void spawnUnit(Player player, String name, float x, float y) {
        spawnUnit(player, name, x, y, 0);
    }

    public void spawnUnit(Player player, String name, float x, float y, int tech) {
        NetworkEngine network = object;

        //Waypoint
        Waypoint waypoint = new Waypoint();
        waypoint.type = WaypointType.build;
        waypoint.info = class_431.method_1082(name);
        waypoint.techLevel = tech;
        waypoint.x = x;
        waypoint.y = y;

        //Action
        Action action = GameEngine.get().actionManager.createAction();
        action.field_5084 = network.field_5873 + network.field_5866;
        action.player = player;
        action.waypoint = waypoint;
        action.id = ActionId.none;
        action.field_5100 = true;
        action.field_5103 = 5;

        network.method_2734(action);
    }
}
