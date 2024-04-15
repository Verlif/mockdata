package idea.verlif.mock.data.pool.template;

import idea.verlif.mock.data.pool.SimplePool;
import idea.verlif.reflection.domain.ClassGrc;

import java.util.Arrays;
import java.util.List;

/**
 * 连续数字池
 */
public class ContinuousIntPool implements SimplePool {

    /**
     * 起始数字
     */
    private int start;
    /**
     * 步进值
     */
    private final int step;

    public ContinuousIntPool() {
        this(0, 1);
    }

    public ContinuousIntPool(int start) {
        this(start, 1);
    }

    public ContinuousIntPool(int start, int step) {
        this.start = start;
        this.step = step;
    }

    public Integer mock() {
        return start += step;
    }

    @Override
    public Object fetch(ClassGrc classGrc, String key) {
        return mock();
    }

    @Override
    public List<Class<?>> types() {
        return Arrays.asList(int.class, Integer.class);
    }
}
