package cn.tesseract.rwally.util;

import cn.tesseract.rwally.accessor.NetworkAccessor;
import com.corrodinggames.rts.ally.game.class_315;
import com.corrodinggames.rts.ally.gameFramework.class_340;
import com.corrodinggames.rts.ally.gameFramework.j.class_1042;
import com.corrodinggames.rts.ally.gameFramework.j.class_1054;
import com.corrodinggames.rts.ally.gameFramework.j.class_1101;

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

    public static void sendSysMessage(Object str) {
        sendSysMessage(String.valueOf(str), null);
    }

    public static void sendSysMessage(String str, class_1054 conn) {
        ((NetworkAccessor) getNetworkEngine()).invoke_a(str, conn);
    }

    public static class_315 getPlayer(int n) {
        return class_315.i(n);
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

    public static void sendMessage(class_1054 conn, String sender, String msg, int color) {
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

    public static void sendMessage(class_1054 conn, String sender, String msg) {
        sendMessage(conn, sender, msg, -1);
    }
}
