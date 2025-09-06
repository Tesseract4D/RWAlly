package cn.tesseract.union.api.rusted;

import com.corrodinggames.rts.union.game.b.GameMap;

public class RustedMap extends RustedWrapper<GameMap> {
    public RustedMap(GameMap instance) {
        super(instance);
    }

    public float getMapHeight() {
        return inner.getMapHeight();
    }

    public float getMapWidth() {
        return inner.getMapWidth();
    }
}
