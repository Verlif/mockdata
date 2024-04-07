package idea.verlif.mock.data.creator;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.domain.MockSrc;
import idea.verlif.mock.data.domain.TypeGetter;
import idea.verlif.mock.data.exception.MockDataException;
import idea.verlif.reflection.domain.ClassGrc;
import idea.verlif.reflection.util.ReflectUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据构建器
 *
 * @author Verlif
 */
public interface DataCreator<R> extends TypeGetter {

    String GENERICS_KEY = "R";

    /**
     * 生成数据
     *
     * @param src     构建源信息。当此构造器绑定的类是通过{@link MockDataCreator#mock(Class)}的方式传入时，field为空。<br>
     *                例如此构造器绑定Person对象，而此时通过MockDataCreator.mock(Person.class)方式进入此构造器时，field对象为空。但其内部可能存在的Person则不会为空。<br>
     *                也就是说，只有在{@link MockDataCreator#mock(Class)}传入的类与此构造器绑定类相同时，返回的目标对象在构造时传入的field为空。
     * @param creator 当前所使用的数据创建器。
     * @return 生成的数据
     */
    R mock(MockSrc src, MockDataCreator.Creator creator);

    @Override
    default List<Class<?>> types() {
        List<Class<?>> list = new ArrayList<>();
        Class<?> type = type();
        list.add(type);
        return list;
    }

    default Class<?> type() {
        Map<String, ClassGrc> genericsMap;
        try {
            genericsMap = ReflectUtil.getGenericsMap(this.getClass());
        } catch (NoSuchFieldException | IllegalAccessException exception) {
            throw new MockDataException(exception);
        }
        ClassGrc grc = genericsMap.get(GENERICS_KEY);
        if (grc != null) {
            return grc.getTarget();
        }
        return null;
    }
}
