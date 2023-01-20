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
public class ByteRandomCreator implements DataCreator<Byte> {

    private final int max;

    private final Random random;

    public ByteRandomCreator() {
        this(Byte.MAX_VALUE);
    }

    public ByteRandomCreator(byte max) {
        this.max = max + 128;
        random = new Random();
    }

    @Override
    public Byte mock(Field field, MockDataCreator.Creator creator) {
        int i = random.nextInt(max) - 128;
        if (random.nextBoolean()) {
            return (byte) (i + (random.nextBoolean() ? 0 : 1));
        } else {
            return (byte) -i;
        }
    }

    @Override
    public List<Class<?>> types() {
        List<Class<?>> list = new ArrayList<>();
        list.add(Byte.class);
        list.add(byte.class);
        return list;
    }
}
