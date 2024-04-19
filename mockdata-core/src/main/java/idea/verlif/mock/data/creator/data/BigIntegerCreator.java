package idea.verlif.mock.data.creator.data;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.creator.DataCreator;
import idea.verlif.mock.data.domain.MockSrc;
import idea.verlif.mock.data.util.RandomUtil;

import java.math.BigInteger;

/**
 * @author Verlif
 */
public class BigIntegerCreator implements DataCreator<BigInteger> {

    public BigIntegerCreator() {
    }

    @Override
    public BigInteger mock(MockSrc src, MockDataCreator.Creator creator) {
        long ran = RandomUtil.nextLong();
        return BigInteger.valueOf(ran);
    }
}
