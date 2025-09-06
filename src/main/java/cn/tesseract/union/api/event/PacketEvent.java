package cn.tesseract.union.api.event;

import cn.tesseract.union.api.rusted.RustedPacket;

public class PacketEvent extends Event {
    public final RustedPacket packet;

    public PacketEvent(RustedPacket packet) {
        this.packet = packet;
    }
}
