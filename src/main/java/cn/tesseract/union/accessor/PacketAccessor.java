package cn.tesseract.union.accessor;

import cn.tesseract.union.api.rusted.RustedPacket;
import cn.tesseract.union.asm.Accessor;
import com.corrodinggames.rts.union.gameFramework.j.class_1032;

@Accessor.Target(class_1032.class)
public interface PacketAccessor {
    RustedPacket get_wrapper();

    void set_wrapper(RustedPacket wrapper);
}
