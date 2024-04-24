package idea.verlif.mock.data.virtualpool.template;

import idea.verlif.mock.data.random.TypeStringRandom;
import idea.verlif.mock.data.virtualpool.SimplePool;
import idea.verlif.reflection.domain.ClassGrc;

/**
 * 受限的字符池
 */
public class LimitedStringPool extends TypeStringRandom implements SimplePool {

    public LimitedStringPool(int length, CharType... types) {
        super(length, types);
    }

    public LimitedStringPool(int minLength, int maxLength, CharType... types) {
        super(minLength, maxLength, types);
    }

    @Override
    public Object fetch(ClassGrc classGrc, String key) {
        return next();
    }

    @Override
    public Class<?> type() {
        return String.class;
    }
}
