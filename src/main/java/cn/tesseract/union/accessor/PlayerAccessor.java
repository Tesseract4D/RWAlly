package cn.tesseract.union.accessor;

import cn.tesseract.union.asm.Accessor;
import com.corrodinggames.rts.union.game.Player;

@Accessor.Target(Player.class)
public interface PlayerAccessor {
    Player wrapper_instance();
}
