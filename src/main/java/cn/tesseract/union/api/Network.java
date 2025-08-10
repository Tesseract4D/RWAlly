package cn.tesseract.union.api;

import cn.tesseract.union.accessor.NetworkAccessor;
import com.corrodinggames.rts.union.game.Player;
import com.corrodinggames.rts.union.game.units.Actions;
import com.corrodinggames.rts.union.game.units.Waypoint;
import com.corrodinggames.rts.union.game.units.a.class_333;
import com.corrodinggames.rts.union.game.units.class_431;
import com.corrodinggames.rts.union.gameFramework.class_898;
import com.corrodinggames.rts.union.gameFramework.j.NetworkEngine;
import com.corrodinggames.rts.union.gameFramework.j.class_1030;
import com.corrodinggames.rts.union.gameFramework.j.class_1037;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Network {
    public static void sendTeamMessage(String message, int team) {
        ConcurrentLinkedQueue<class_1037> connections = Union.getNetworkEngine().connections;
        for (class_1037 conn : connections) {
            if (conn.field_6181 && !conn.field_6166 && conn.field_6142.team == team) {
                sendMessage(message, null, conn, -1);
            }
        }
        if (Union.getNetworkEngine().field_5848.team == team) {
            sendMessage(message, null);
        }
    }

    public static void sendMessage(String message, int index) {
        ConcurrentLinkedQueue<class_1037> connections = Union.getNetworkEngine().connections;
        for (class_1037 conn : connections) {
            if (conn.field_6142 != null && conn.field_6142.field_1457 == index) {
                sendMessage(message, null, conn, -1);
            }
        }
        if (Union.getNetworkEngine().field_5848 != null && Union.getNetworkEngine().field_5848.field_1457 == index) {
            sendMessage(message, null);
        }
    }

    public static void setMaxPlayer(int n) {
        Player.method_488(n, true);
    }

    public static void message(String message) {
        sendMessage(message, null);
    }

    public static void sendChat(String message) {
        Union.getNetworkEngine().method_2816(message);
    }

    public static void sendMessage(String message) {
        Union.getNetworkEngine().method_2809(message);
    }

    public static void sendMessage(String message, class_1037 conn) {
        ((NetworkAccessor) Union.getNetworkEngine()).invoke_method_2750(message, conn);
    }

    public static void sendMessage(String message, String sender, class_1037 conn) {
        sendMessage(message, sender, conn, -1);
    }

    public static void sendMessage(String message, String sender, class_1037 conn, int color) {
        class_1030 pk = new class_1030();
        pk.method_2880(message);
        pk.method_2877(3);
        pk.method_2871(sender);
        pk.method_2865(conn);
        pk.method_2883(color);
        conn.method_2894(pk.method_2856(141));
    }

    public static void receiveMessage(String sender, String message, int color) {
        ((NetworkAccessor) Union.getNetworkEngine()).invoke_method_2740(null, color, sender, message);
    }

    public static boolean isInGame() {
        return Union.getNetworkEngine().field_5898;
    }

    public static void sync() {
        NetworkEngine c = Union.getNetworkEngine();
        Iterator it = c.connections.iterator();
        while (it.hasNext()) {
            Object o = it.next();
            class_1037 p = (class_1037) o;
            p.field_6187 = true;
            p.field_6188 = true;
        }
    }

    public static void spawnUnit(Player player, String name, float x, float y) {
        NetworkEngine network = Union.getNetworkEngine();

        //Waypoint
        Waypoint waypoint = new Waypoint();
        waypoint.action = Actions.build;
        waypoint.type = class_431.method_1082(name);
        //waypoint.field_3930 = tech;
        waypoint.x = x;
        waypoint.y = y;

        //Action
        class_898 action = Union.getGameEngine().field_6412.method_2060();
        action.field_5084 = network.field_5873 + network.field_5866;
        action.field_5090 = player;
        action.field_5091 = waypoint;
        //action id
        action.field_5092 = class_333.method_560("-1");
        action.field_5100 = true;
        action.field_5103 = 5;

        network.method_2734(action);
    }
}
