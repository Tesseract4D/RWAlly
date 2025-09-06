package cn.tesseract.union.api.event;

import cn.tesseract.union.api.rusted.RustedAction;
import cn.tesseract.union.api.rusted.RustedPlayer;

public class PlayerActionEvent extends PlayerEvent {
    public final RustedAction action;

    public PlayerActionEvent(RustedPlayer player, RustedAction action) {
        super(player);
        this.action = action;
    }
}
