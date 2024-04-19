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
public class FloatRandomCreator implements DataCreator<Float> {

    private final float offset;

    private final float point;

    public FloatRandomCreator() {
        this(Float.MIN_VALUE, Float.MAX_VALUE);
    }

    public FloatRandomCreator(float min, float max) {
        offset = max / 2 - min / 2;
        point = (min / 2 + max / 2);
    }

    @Override
    public List<Class<?>> types() {
        List<Class<?>> list = new ArrayList<>();
        list.add(Float.class);
        list.add(float.class);
        return list;
    }

    @Override
    public Float mock(MockSrc src, MockDataCreator.Creator creator) {
        float num = RandomUtil.nextFloat() * offset;
        if (RandomUtil.nextBoolean()) {
            return point - num;
        } else {
            return point + num;
        }
    }
}
