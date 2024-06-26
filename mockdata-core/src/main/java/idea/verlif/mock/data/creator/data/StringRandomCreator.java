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
public class StringRandomCreator implements DataCreator<String> {

    private static final Character[] TABLE = new Character[]{
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '.', '[', ']', '{', '}', '!', '@', '#', '$', '%', '&', '*', '(', ')', '-', '_', '+', ':',
    };

    /**
     * 最小字符数
     */
    private final int min;

    /**
     * 最大字符数
     */
    private final int max;

    public StringRandomCreator() {
        this(0, 9);
    }

    public StringRandomCreator(int size) {
        this(size, size);
    }

    public StringRandomCreator(int min, int max) {
        this.min = min;
        if (max < Integer.MAX_VALUE) {
            this.max = max + 1;
        } else {
            this.max = max;
        }
    }

    @Override
    public String mock(MockSrc src, MockDataCreator.Creator creator) {
        StringBuilder sb = new StringBuilder();
        int size;
        if (min == max) {
            size = 5;
        } else {
            size = RandomUtil.nextInt(max - min) + min;
        }
        for (int i = 0; i < size; i++) {
            sb.append(TABLE[RandomUtil.nextInt(TABLE.length)]);
        }
        return sb.toString();
    }

    @Override
    public List<Class<?>> types() {
        List<Class<?>> list = new ArrayList<>();
        list.add(String.class);
        return list;
    }
}
