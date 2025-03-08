package cn.tesseract.rwally.accessor;

import cn.tesseract.dragonfly.asm.Accessor;
import com.corrodinggames.rts.ally.appFramework.MultiplayerBattleroomActivity;

@Accessor.Target(MultiplayerBattleroomActivity.class)
public interface BattleroomAccessor {
    void invoke_refreshServerInfo();
}
