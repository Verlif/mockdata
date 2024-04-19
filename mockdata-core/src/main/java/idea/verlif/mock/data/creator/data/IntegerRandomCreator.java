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
public class IntegerRandomCreator implements DataCreator<Integer> {

    private final int offset;

    private final int point;

    public IntegerRandomCreator() {
        this(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public IntegerRandomCreator(int min, int max) {
        offset = max / 2 - min / 2;
        point = (min / 2 + max / 2);
    }

    @Override
    public List<Class<?>> types() {
        List<Class<?>> list = new ArrayList<>();
        list.add(Integer.class);
        list.add(int.class);
        return list;
    }

    @Override
    public Integer mock(MockSrc src, MockDataCreator.Creator creator) {
        int ran = RandomUtil.nextInt(offset);
        if (RandomUtil.nextBoolean()) {
            return point - ran;
        } else {
            return point + ran;
        }
    }
}
