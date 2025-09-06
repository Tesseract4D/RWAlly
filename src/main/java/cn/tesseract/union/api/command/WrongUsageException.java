package cn.tesseract.union.api.command;

public class WrongUsageException extends RuntimeException {
    public WrongUsageException(String message) {
        super(message);
    }
}
