package idea.verlif.mock.data.creator.data;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.creator.DataCreator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 字典型数据构建器
 *
 * @author Verlif
 */
public class DictDataCreator<T> implements DataCreator<T> {

    private static final List<Class<?>> LIST = new ArrayList<>();

    private final T[] arrays;

    public DictDataCreator(T[] arrays) {
        this.arrays = arrays;
    }

    @Override
    public T mock(Field field, MockDataCreator.Creator creator) {
        if (arrays.length == 0) {
            return null;
        }
        return arrays[(int) (arrays.length * Math.random())];
    }

    @Override
    public List<Class<?>> types() {
        return LIST;
    }
}
