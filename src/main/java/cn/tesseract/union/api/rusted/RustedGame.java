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
}