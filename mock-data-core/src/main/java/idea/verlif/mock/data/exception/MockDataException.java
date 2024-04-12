package idea.verlif.mock.data.exception;

public class MockDataException extends RuntimeException {
    public MockDataException() {
    }

    public MockDataException(String message) {
        super(message);
    }

    public MockDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public MockDataException(Throwable cause) {
        super(cause);
    }

    public MockDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
