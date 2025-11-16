package cn.tesseract.union.api.rusted;

import cn.tesseract.union.accessor.MapAccessor;
import com.corrodinggames.rts.union.gameFramework.GameEngine;
import com.corrodinggames.rts.union.gameFramework.class_773;

public class RustedGame extends RustedWrapper<GameEngine> {
    private static RustedGame instance;

    public RustedGame(GameEngine object) {
        super(object);
        instance = this;
    }

    public static RustedGame get() {
        return instance;
    }

    public void toast(String s) {
        inner.method_3056(s);
    }

    public RustedMap getMap() {
        return ((MapAccessor) inner.field_6339).get_wrapper();
    }

    public static RustedUnit getUnitById(long id) {
        return RustedUnit.warp(class_773.method_1767(id, true));
    }

    public boolean isUserPlayer(RustedPlayer player) {
        return RustedPlayer.warp(inner.userPlayer) == player;
    }

    public String getCurrentMapName() {
        return inner.field_6352.field_5874.field_6013;
    }

    public void setCurrentMapName(String name) {
        inner.field_6352.field_5874.field_6013 = name;
    }

    /*public void setMapText(String id, String s) {
        for (class_1255 mt : (ArrayList<class_1255>) inner.field_6411.field_7084) {
            if (mt.field_7018 != null && mt.field_7018.equalsIgnoreCase(id)) {
                mt.field_7042 = mt.field_7036.method_292(s);
            }
        }
    }

    public Paint getMapTextPaint(String id) {
        for (class_1255 mt : (ArrayList<class_1255>) inner.field_6411.field_7084) {
            if (mt.field_7018 != null && mt.field_7018.equalsIgnoreCase(id)) {
                return mt.field_7015;
            }
        }
        return null;
    }*/
}