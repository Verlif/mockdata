package idea.verlif.mock.data.exception;

/**
 * 值设定错误
 */
public class SetValueException extends MockDataException {
    public SetValueException(String message) {
        super(message);
    }

    public SetValueException(Throwable cause) {
        super(cause);
    }
}
