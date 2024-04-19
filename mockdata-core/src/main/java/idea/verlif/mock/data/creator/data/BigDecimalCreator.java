package idea.verlif.mock.data.creator.data;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.creator.DataCreator;
import idea.verlif.mock.data.domain.MockSrc;
import idea.verlif.mock.data.util.RandomUtil;

import java.math.BigDecimal;

/**
 * @author Verlif
 */
public class BigDecimalCreator implements DataCreator<BigDecimal> {

    public BigDecimalCreator() {
    }

    @Override
    public BigDecimal mock(MockSrc src, MockDataCreator.Creator creator) {
        return BigDecimal.valueOf(RandomUtil.nextLong()).add(BigDecimal.valueOf(RandomUtil.nextDouble()));
    }

}
