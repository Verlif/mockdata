package idea.verlif.mock.data.creator.data;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.creator.DataCreator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Verlif
 */
public class LongRandomCreator implements DataCreator<Long> {

    private final long offset;

    private final long point;

    private final Random random;

    public LongRandomCreator() {
        this(Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public LongRandomCreator(long min, long max) {
        offset = max / 2 - min / 2;
        point = (min / 2 + max / 2);
        random = new Random();
    }

    @Override
    public List<Class<?>> types() {
        List<Class<?>> list = new ArrayList<>();
        list.add(Long.class);
        list.add(long.class);
        return list;
    }

    @Override
    public Long mock(Field field, MockDataCreator creator) {
        long num = (long) (Math.random() * offset);
        if (random.nextBoolean()) {
            return point - num;
        } else {
            return point + num;
        }
    }
}
