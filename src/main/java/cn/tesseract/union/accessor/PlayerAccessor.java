package cn.tesseract.union.accessor;

import cn.tesseract.union.api.rusted.RustedNetwork;
import cn.tesseract.union.api.rusted.RustedPlayer;
import cn.tesseract.union.asm.Accessor;
import com.corrodinggames.rts.union.game.Player;

@Accessor.Target(Player.class)
public interface PlayerAccessor {
    RustedPlayer get_wrapper();

    void set_wrapper(RustedPlayer wrapper);
}
