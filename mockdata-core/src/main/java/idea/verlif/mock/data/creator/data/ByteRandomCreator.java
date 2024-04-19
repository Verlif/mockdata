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
public class ByteRandomCreator implements DataCreator<Byte> {

    private final int max;

    public ByteRandomCreator() {
        this(Byte.MAX_VALUE);
    }

    public ByteRandomCreator(byte max) {
        this.max = max + 128;
    }

    @Override
    public Byte mock(MockSrc src, MockDataCreator.Creator creator) {
        int i = RandomUtil.nextInt(max) - 128;
        if (RandomUtil.nextBoolean()) {
            return (byte) (i + (RandomUtil.nextBoolean() ? 0 : 1));
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
