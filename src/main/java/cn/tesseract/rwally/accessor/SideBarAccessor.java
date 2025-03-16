package cn.tesseract.rwally.accessor;

import android.graphics.Paint;
import cn.tesseract.dragonfly.asm.Accessor;
import cn.tesseract.rwally.util.GlobalChatButton;
import com.corrodinggames.rts.ally.gameFramework.f.class_1001;
import com.corrodinggames.rts.ally.gameFramework.f.class_1002;

@Accessor.Target(class_1001.class)
public interface SideBarAccessor {
    GlobalChatButton get_chat();

    void set_chat(GlobalChatButton chatButton);
}
