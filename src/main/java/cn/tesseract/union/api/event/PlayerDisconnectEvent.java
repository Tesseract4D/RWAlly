package cn.tesseract.union.api.event;

import cn.tesseract.union.api.rusted.RustedConnection;
import cn.tesseract.union.api.rusted.RustedPlayer;

public class PlayerDisconnectEvent extends PlayerEvent{
    public final RustedConnection conn;

    public PlayerDisconnectEvent(RustedPlayer player, RustedConnection conn) {
        super(player);
        this.conn = conn;
    }
}
