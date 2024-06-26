package idea.verlif.mock.data.virtualpool;

import idea.verlif.mock.data.util.RandomUtil;
import idea.verlif.reflection.domain.ClassGrc;

public class DictSimplePool<T> implements SimplePool {

    private final T[] values;

    public DictSimplePool(T[] values) {
        this.values = values;
    }

    public T mock() {
        return RandomUtil.next(values);
    }

    @Override
    public Object fetch(ClassGrc classGrc, String key) {
        return mock();
    }
}
