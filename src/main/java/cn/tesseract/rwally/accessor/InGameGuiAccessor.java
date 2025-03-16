package cn.tesseract.rwally.accessor;

import android.graphics.Paint;
import cn.tesseract.dragonfly.asm.Accessor;
import cn.tesseract.rwally.util.GlobalChatButton;
import com.corrodinggames.rts.ally.gameFramework.f.class_1002;

@Accessor.Target(class_1002.class)
public interface InGameGuiAccessor {
    void invoke_a(int var1, int var2, int var3, String var4, String var5, Paint var6, float var7);
}
