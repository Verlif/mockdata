package idea.verlif.mock.data.creator.data;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.creator.DataCreator;
import idea.verlif.mock.data.domain.MockSrc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Verlif
 */
public class FloatRandomCreator implements DataCreator<Float> {

    private final float offset;

    private final float point;

    private final Random random;

    public FloatRandomCreator() {
        this(Float.MIN_VALUE, Float.MAX_VALUE);
    }

    public FloatRandomCreator(float min, float max) {
        offset = max / 2 - min / 2;
        point = (min / 2 + max / 2);
        random = new Random();
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
        float num = random.nextFloat() * offset;
        if (random.nextBoolean()) {
            return point - num;
        } else {
            return point + num;
        }
    }
}
