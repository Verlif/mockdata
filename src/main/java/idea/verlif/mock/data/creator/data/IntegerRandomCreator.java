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
public class IntegerRandomCreator implements DataCreator<Integer> {

    private final int offset;

    private final int point;

    private final Random random;

    public IntegerRandomCreator() {
        this(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public IntegerRandomCreator(int min, int max) {
        offset = max / 2 - min / 2;
        point = (min / 2 + max / 2);
        random = new Random();
    }

    @Override
    public List<Class<?>> types() {
        List<Class<?>> list = new ArrayList<>();
        list.add(Integer.class);
        list.add(int.class);
        return list;
    }

    @Override
    public Integer mock(Field field, MockDataCreator.Creator creator) {
        int ran = random.nextInt(offset);
        if (random.nextBoolean()) {
            return point - ran;
        } else {
            return point + ran;
        }
    }
}
