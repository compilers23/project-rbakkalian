package Parser;

public class ParserError extends Exception {
    public ParserError() {
    }

    public ParserError(String message) {
        super(message);
    }

    public ParserError(String message, Throwable cause) {
        super(message, cause);
    }

    public ParserError(Throwable cause) {
        super(cause);
    }

    public ParserError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
