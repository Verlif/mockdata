package idea.verlif.mock.data.creator.data;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.creator.DataCreator;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Random;

/**
 * @author Verlif
 */
public class BigDecimalCreator implements DataCreator<BigDecimal> {

    private final Random random;

    public BigDecimalCreator() {
        random = new Random();
    }

    @Override
    public BigDecimal mock(Field field, MockDataCreator.Creator creator) {
        return BigDecimal.valueOf(random.nextLong()).add(BigDecimal.valueOf(random.nextDouble()));
    }

}
