package cn.tesseract.union.api;

import com.corrodinggames.rts.union.game.Player;
import com.corrodinggames.rts.union.gameFramework.GameEngine;
import com.corrodinggames.rts.union.gameFramework.j.NetworkEngine;
import com.corrodinggames.rts.union.gameFramework.j.class_1037;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Union {

    public static GameEngine getGameEngine() {
        return GameEngine.get();
    }

    public static NetworkEngine getNetworkEngine() {
        return getGameEngine().field_6352;
    }

    public static Player getPlayer(int n) {
        return Player.method_526(n);
    }

    public static class_1037 getConnection(Player p) {
        ConcurrentLinkedQueue<class_1037> connections = getNetworkEngine().connections;
        for (class_1037 conn : connections) {
            if (conn.field_6142 == p) {
                return conn;
            }
        }
        return null;
    }
}
