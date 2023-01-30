package idea.verlif.mock.data.domain;

import idea.verlif.mock.data.util.ReflectUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 类型获取器
 *
 * @author Verlif
 */
public interface TypeGetter {

    default List<Class<?>> types() {
        List<Class<?>> list = new ArrayList<>();
        Class<?> cla = getClass();
        Type[] types = cla.getGenericInterfaces();
        for (Type type : types) {
            ReflectUtil.addActualTypeToList(type, list);
        }
        return list;
    }
}
