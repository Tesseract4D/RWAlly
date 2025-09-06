package cn.tesseract.union.api.event;

public class Event {
    private boolean canceled = false;

    public void cancel() {
        this.canceled = true;
    }

    public boolean isCanceled() {
        return canceled;
    }
}
