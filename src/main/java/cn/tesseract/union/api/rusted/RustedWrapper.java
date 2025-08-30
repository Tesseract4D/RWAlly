package cn.tesseract.union.api.rusted;

public abstract class RustedWrapper<T> {
    protected final T object;

    public RustedWrapper(T instance) {
        this.object = instance;
    }
}
