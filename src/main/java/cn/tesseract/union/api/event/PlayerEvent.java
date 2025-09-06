package cn.tesseract.union.api.event;

import cn.tesseract.union.api.rusted.RustedPlayer;

public abstract class PlayerEvent extends Event{
    public final RustedPlayer player;

    public PlayerEvent(RustedPlayer player) {
        this.player = player;
    }
}
