package cn.tesseract.union.util;

public class CallbackInfo {
    private boolean prevented = false;

    public void prevent() {
        this.prevented = true;
    }

    public boolean isPrevented() {
        return prevented;
    }
}
