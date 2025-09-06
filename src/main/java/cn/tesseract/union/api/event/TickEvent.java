package cn.tesseract.union.api.event;

public class TickEvent extends Event{
public final int tick;

    public TickEvent(int tick) {
        this.tick = tick;
    }
}
