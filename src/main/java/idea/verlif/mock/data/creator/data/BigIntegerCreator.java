package idea.verlif.mock.data.creator.data;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.creator.DataCreator;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Random;

/**
 * @author Verlif
 */
public class BigIntegerCreator implements DataCreator<BigInteger> {

    private final Random random;

    public BigIntegerCreator() {
        random = new Random();
    }

    @Override
    public BigInteger mock(Field field, MockDataCreator creator) {
        long ran = random.nextLong();
        return BigInteger.valueOf(ran);
    }
}
