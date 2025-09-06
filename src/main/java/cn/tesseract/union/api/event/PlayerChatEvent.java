package cn.tesseract.union.api.event;

import cn.tesseract.union.api.rusted.RustedConnection;
import cn.tesseract.union.api.rusted.RustedPlayer;

public class PlayerChatEvent extends PlayerEvent{
    public final RustedConnection conn;
    public final String msg;

    public PlayerChatEvent(RustedPlayer player, RustedConnection conn,String msg) {
        super(player);
        this.conn = conn;
        this.msg = msg;
    }
}
