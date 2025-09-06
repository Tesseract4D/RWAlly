package cn.tesseract.union.accessor;

import cn.tesseract.union.asm.Accessor;
import com.corrodinggames.rts.union.appFramework.MultiplayerBattleroomActivity;

@Accessor.Target(MultiplayerBattleroomActivity.class)
public interface BattleroomAccessor {
    void invoke_refreshServerInfo();
}