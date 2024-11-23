package _oormthon.univ.flakeide.backend.global.exception;

public class ErrorResponse {
    private boolean ok;
    private int statusCode;
    private int errorCode;
    private String message;

    public ErrorResponse(boolean ok, int statusCode, int errorCode, String message) {
        this.ok = ok;
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.message = message;
    }

    public boolean isOk() {
        return ok;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
