package _oormthon.univ.flakeide.backend.global.exception;

public class CustomException extends RuntimeException {
    private final int errorCode;
    private final int statusCode;

    public CustomException(String message, int statusCode, int errorCode) {
        super(message);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
