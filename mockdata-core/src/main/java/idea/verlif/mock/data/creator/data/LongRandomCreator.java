package idea.verlif.mock.data.creator.data;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.creator.DataCreator;
import idea.verlif.mock.data.domain.MockSrc;
import idea.verlif.mock.data.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Verlif
 */
public class LongRandomCreator implements DataCreator<Long> {

    private final long offset;

    private final long point;

    public LongRandomCreator() {
        this(Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public LongRandomCreator(long min, long max) {
        offset = max / 2 - min / 2;
        point = (min / 2 + max / 2);
    }

    @Override
    public List<Class<?>> types() {
        List<Class<?>> list = new ArrayList<>();
        list.add(Long.class);
        list.add(long.class);
        return list;
    }

    @Override
    public Long mock(MockSrc src, MockDataCreator.Creator creator) {
        long num = (long) (RandomUtil.nextDouble() * offset);
        if (RandomUtil.nextBoolean()) {
            return point - num;
        } else {
            return point + num;
        }
    }
}
