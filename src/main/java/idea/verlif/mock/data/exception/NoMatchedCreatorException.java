package idea.verlif.mock.data.exception;

import idea.verlif.mock.data.domain.MockField;

/**
 * @author Verlif
 */
public class NoMatchedCreatorException extends RuntimeException {

    public NoMatchedCreatorException(MockField field) {
        super("No matched creator of - " + field.getKey());
    }

    public NoMatchedCreatorException(String fieldKey) {
        super("No matched creator of - " + fieldKey);
    }
}
