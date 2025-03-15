package cn.tesseract.rwally.util;

import cn.tesseract.rwally.accessor.NetworkAccessor;
import com.corrodinggames.rts.ally.game.class_315;
import com.corrodinggames.rts.ally.gameFramework.class_340;
import com.corrodinggames.rts.ally.gameFramework.j.class_1042;
import com.corrodinggames.rts.ally.gameFramework.j.class_1054;
import com.corrodinggames.rts.ally.gameFramework.j.class_1101;

import java.util.concurrent.ConcurrentLinkedQueue;

public class RWHelper {
    public static class_340 getGameEngine() {
        return class_340.t();
    }

    public static class_1101 getNetworkEngine() {
        return getGameEngine().bU;
    }

    public static void setMaxPlayer(int n) {
        class_315.b(n, true);
    }

    public static void message(Object str) {
        sendMessage(String.valueOf(str), null);
    }

    public static void sendMessage(Object str) {
        getNetworkEngine().k(String.valueOf(str));
    }

    public static void sendMessage(String str, class_1054 conn) {
        ((NetworkAccessor) getNetworkEngine()).invoke_a(str, conn);
    }

    public static void sendMessage(String msg, String sender, class_1054 conn) {
        sendMessage(msg, sender, conn, -1);
    }

    public static void sendMessage(String msg, String sender, class_1054 conn, int color) {
        class_1042 pk = new class_1042();
        pk.b(msg);
        pk.b(3);
        pk.a(sender);
        pk.a(conn);
        pk.c(color);
        conn.a(pk.a(141));
    }

    public static void receiveMessage(String sender, String msg, int color) {
        ((NetworkAccessor) getNetworkEngine()).invoke_a(null, color, sender, msg);
    }

    public static class_315 getPlayer(int n) {
        return class_315.i(n);
    }

    public static class_1054 getConnection(class_315 p) {
        ConcurrentLinkedQueue<class_1054> connections = getNetworkEngine().aO;
        for (class_1054 conn : connections) {
            if (conn.A == p)
                return conn;
        }
        return null;
    }

    public static boolean isInGame() {
        return getNetworkEngine().aY;
    }

    public static void syncFast() {

    }

    public static void sync() {
        class_1101 c = getNetworkEngine();
        for (Object o : c.aO) {
            class_1054 p = (class_1054) o;
            p.x = p.w = true;
        }
    }
}
