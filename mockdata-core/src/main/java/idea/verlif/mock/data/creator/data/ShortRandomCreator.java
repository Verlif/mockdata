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
public class ShortRandomCreator implements DataCreator<Short> {

    private final int offset;

    private final int point;

    public ShortRandomCreator() {
        this(Short.MIN_VALUE, Short.MAX_VALUE);
    }

    public ShortRandomCreator(short min, short max) {
        offset = (max - min) / 2;
        point = (min + max) / 2;
    }

    @Override
    public List<Class<?>> types() {
        List<Class<?>> list = new ArrayList<>();
        list.add(Short.class);
        list.add(short.class);
        return list;
    }

    @Override
    public Short mock(MockSrc src, MockDataCreator.Creator creator) {
        int ran = RandomUtil.nextInt(offset);
        if (RandomUtil.nextBoolean()) {
            return (short) (point - ran);
        } else {
            return (short) (point + ran);
        }
    }
}
