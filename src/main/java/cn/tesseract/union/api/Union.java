package cn.tesseract.union.api;

import com.corrodinggames.rts.union.game.class_324;
import com.corrodinggames.rts.union.gameFramework.class_1061;
import com.corrodinggames.rts.union.gameFramework.j.class_1001;
import com.corrodinggames.rts.union.gameFramework.j.class_1037;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Union {

    public static class_1061 getGameEngine() {
        return class_1061.method_3076();
    }

    public static class_1001 getNetworkEngine() {
        return getGameEngine().field_6352;
    }

    public static class_324 getPlayer(int n) {
        return class_324.method_526(n);
    }

    public static class_1037 getConnection(class_324 p) {
        ConcurrentLinkedQueue<class_1037> connections = getNetworkEngine().field_5888;
        for (class_1037 conn : connections) {
            if (conn.field_6142 == p) {
                return conn;
            }
        }
        return null;
    }
}
