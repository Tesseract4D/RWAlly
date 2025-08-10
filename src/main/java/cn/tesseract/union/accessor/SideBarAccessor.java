package cn.tesseract.union.accessor;

import cn.tesseract.union.asm.Accessor;
import cn.tesseract.union.util.*;
import com.corrodinggames.rts.union.gameFramework.f.class_961;

@Accessor.Target(class_961.class)
public interface SideBarAccessor {
    GlobalChatButton get_chatButton();

    void set_chatButton(GlobalChatButton button);

    RangeTypeButton get_rangeTypeButton();

    void set_rangeTypeButton(RangeTypeButton button);

    RangeType2Button get_rangeTypeButton2();

    void set_rangeTypeButton2(RangeType2Button button);

    ShiftButton get_shiftButton();

    void set_shiftButton(ShiftButton button);

    StopButton get_stopButton();

    void set_stopButton(StopButton button);
}