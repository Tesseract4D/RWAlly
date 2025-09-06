package cn.tesseract.union.api.rusted;

public abstract class RustedWrapper<T> {
    public final T inner;

    public RustedWrapper(T instance) {
        this.inner = instance;
    }
}