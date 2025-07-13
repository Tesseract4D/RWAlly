package cn.tesseract.union.util;

import cn.tesseract.union.accessor.NetworkAccessor;
import com.corrodinggames.rts.union.game.class_324;
import com.corrodinggames.rts.union.gameFramework.class_1061;
import com.corrodinggames.rts.union.gameFramework.j.class_1001;
import com.corrodinggames.rts.union.gameFramework.j.class_1030;
import com.corrodinggames.rts.union.gameFramework.j.class_1037;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RWHelper {
    public static boolean shouldSync = false;

    public static class_1061 getGameEngine() {
        return class_1061.method_3076();
    }

    public static class_1001 getNetworkEngine() {
        return getGameEngine().field_6352;
    }

    public static void setMaxPlayer(int n) throws IOException {
        class_324.method_488(n, true);
    }

    public static void sendTeamMessage(String message, int team) {
        ConcurrentLinkedQueue<class_1037> connections = getNetworkEngine().field_5888;
        Iterator<class_1037> it = connections.iterator();
        while (it.hasNext()) {
            class_1037 conn = it.next();
            if (conn.field_6181 && !conn.field_6166 && conn.field_6142.field_1464 == team) {
                sendMessage(message, null, conn, -1);
            }
        }
        if (getNetworkEngine().field_5848.field_1464 == team) {
            sendMessage(message, (class_1037) null);
        }
    }

    public static void sendMessage(String message, int index) {
        ConcurrentLinkedQueue<class_1037> connections = getNetworkEngine().field_5888;
        Iterator<class_1037> it = connections.iterator();
        while (it.hasNext()) {
            class_1037 conn = it.next();
            if (conn.field_6142 != null && conn.field_6142.field_1457 == index) {
                sendMessage(message, null, conn, -1);
            }
        }
        if (getNetworkEngine().field_5848 != null && getNetworkEngine().field_5848.field_1457 == index) {
            sendMessage(message, (class_1037) null);
        }
    }

    public static void message(String message) {
        sendMessage(message, (class_1037) null);
    }

    public static void sendChat(String message) {
        getNetworkEngine().method_2816(message);
    }

    public static void sendMessage(String message) {
        getNetworkEngine().method_2809(message);
    }

    public static void sendMessage(String message, class_1037 conn) {
        ((NetworkAccessor) getNetworkEngine()).invoke_method_2750(message, conn);
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
        ((NetworkAccessor) getNetworkEngine()).invoke_method_2740((class_1037) null, color, sender, message);
    }

    public static class_324 getPlayer(int n) {
        return class_324.method_526(n);
    }

    public static class_1037 getConnection(class_324 p) {
        ConcurrentLinkedQueue<class_1037> connections = getNetworkEngine().field_5888;
        Iterator<class_1037> it = connections.iterator();
        while (it.hasNext()) {
            class_1037 conn = it.next();
            if (conn.field_6142 == p) {
                return conn;
            }
        }
        return null;
    }

    public static boolean isInGame() {
        return getNetworkEngine().field_5898;
    }

    public static void sync() {
        class_1001 c = getNetworkEngine();
        Iterator it = c.field_5888.iterator();
        while (it.hasNext()) {
            Object o = it.next();
            class_1037 p = (class_1037) o;
            p.field_6187 = true;
            p.field_6188 = true;
        }
    }
}