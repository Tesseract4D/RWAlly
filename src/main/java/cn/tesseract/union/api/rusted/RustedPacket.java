package cn.tesseract.union.api.rusted;

import cn.tesseract.union.accessor.ConnectionAccessor;
import cn.tesseract.union.accessor.PacketAccessor;
import com.corrodinggames.rts.union.gameFramework.Action;
import com.corrodinggames.rts.union.gameFramework.j.class_1032;

public class RustedPacket extends RustedWrapper<class_1032> {
    public RustedPacket(class_1032 instance) {
        super(instance);
    }

    public static RustedPacket warp(class_1032 inner) {
        return inner == null ? null : ((PacketAccessor) inner).get_wrapper();
    }

    public RustedConnection getConnection() {
        return ((ConnectionAccessor) inner.field_6123).get_wrapper();
    }

    public byte[] getData() {
        return inner.field_6125;
    }

    public int getType() {
        return inner.field_6124;
    }

    public void setType(int type) {
        inner.field_6124 = type;
    }
}
