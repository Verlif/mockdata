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
public class ShortRandomCreator implements DataCreator<Short> {

    private final int offset;

    private final int point;

    private final Random random;

    public ShortRandomCreator() {
        this(Short.MIN_VALUE, Short.MAX_VALUE);
    }

    public ShortRandomCreator(short min, short max) {
        offset = (max - min) / 2;
        point = (min + max) / 2;
        random = new Random();
    }

    @Override
    public List<Class<?>> types() {
        List<Class<?>> list = new ArrayList<>();
        list.add(Short.class);
        list.add(short.class);
        return list;
    }

    @Override
    public Short mock(Class<?> cla, Field field, MockDataCreator.Creator creator) {
        int ran = random.nextInt(offset);
        if (random.nextBoolean()) {
            return (short) (point - ran);
        } else {
            return (short) (point + ran);
        }
    }
}
