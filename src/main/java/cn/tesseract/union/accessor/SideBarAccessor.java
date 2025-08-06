package cn.tesseract.union.accessor;

import cn.tesseract.union.asm.Accessor;
import cn.tesseract.union.util.GlobalChatButton;
import cn.tesseract.union.util.ShiftButton;
import cn.tesseract.union.util.StopButton;
import com.corrodinggames.rts.union.gameFramework.f.class_961;

@Accessor.Target(class_961.class)
public interface SideBarAccessor {
    GlobalChatButton get_chatButton();

    void set_chatButton(GlobalChatButton button);

    ShiftButton get_shiftButton();

    void set_shiftButton(ShiftButton button);

    StopButton get_stopButton();

    void set_stopButton(StopButton button);
}