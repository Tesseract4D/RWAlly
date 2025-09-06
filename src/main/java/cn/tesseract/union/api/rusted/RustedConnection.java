package cn.tesseract.union.api.rusted;

import cn.tesseract.union.accessor.ActionAccessor;
import cn.tesseract.union.accessor.ConnectionAccessor;
import cn.tesseract.union.accessor.PlayerAccessor;
import com.corrodinggames.rts.union.gameFramework.Action;
import com.corrodinggames.rts.union.gameFramework.GameEngine;
import com.corrodinggames.rts.union.gameFramework.j.class_1037;

import java.util.concurrent.ConcurrentLinkedQueue;

public class RustedConnection extends RustedWrapper<class_1037> {
    public RustedConnection(class_1037 instance) {
        super(instance);
    }

    public static RustedConnection warp(class_1037 inner) {
        return inner == null ? null : ((ConnectionAccessor) inner).get_wrapper();
    }

    public static RustedConnection getConnection(RustedPlayer p) {
        ConcurrentLinkedQueue<class_1037> connections = GameEngine.get().field_6352.connections;
        for (class_1037 conn : connections) {
            if (conn.field_6142 == p.inner) {
                return ((ConnectionAccessor) conn).get_wrapper();
            }
        }
        return null;
    }

    public RustedPlayer getPlayer() {
        return ((PlayerAccessor) inner.field_6142).get_wrapper();
    }
}
