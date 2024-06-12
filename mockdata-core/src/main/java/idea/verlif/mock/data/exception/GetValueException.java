package idea.verlif.mock.data.exception;

/**
 * 值获取错误
 */
public class GetValueException extends MockDataException {
    public GetValueException(String message) {
        super(message);
    }

    public GetValueException(Throwable cause) {
        super(cause);
    }
}
