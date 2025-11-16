package cn.tesseract.union.api.rusted;

import cn.tesseract.union.accessor.PacketAccessor;
import com.corrodinggames.rts.union.gameFramework.j.class_1032;

public class RustedPacket extends RustedWrapper<class_1032> {
    public RustedPacket(class_1032 instance) {
        super(instance);
    }

    public static RustedPacket warp(class_1032 inner) {
        return inner == null ? null : ((PacketAccessor) inner).get_wrapper();
    }

    public static RustedPacket create(int type) {
        return ((PacketAccessor) new class_1032(type)).get_wrapper();
    }

    public RustedConnection getConnection() {
        return RustedConnection.warp(inner.field_6123);
    }

    public byte[] getData() {
        return inner.field_6125;
    }

    public void setData(byte[] data) {
        inner.field_6125 = data;
    }

    public int getType() {
        return inner.field_6124;
    }

    public void setType(int type) {
        inner.field_6124 = type;
    }
}
