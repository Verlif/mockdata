package idea.verlif.mock.data.creator.data;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.creator.DataCreator;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 列表构造器
 *
 * @author Verlif
 */
public class SetCreator implements DataCreator<Set<?>> {

    private Integer size;

    private Class<?> target;

    public SetCreator() {
    }

    public SetCreator(int size) {
        this.size = size;
    }

    @Override
    public Set<?> mock(Class<?> cla, Field field, MockDataCreator.Creator creator) {
        this.target = cla == Set.class ? HashSet.class : cla;
        if (this.size == null) {
            this.size = creator.getMockConfig().getArraySize(cla);
        }
        Type rawType = Object.class;
        if (field != null) {
            Type type = field.getGenericType();
            if (type instanceof ParameterizedType) {
                Type argument = ((ParameterizedType) type).getActualTypeArguments()[0];
                return fillSet(argument, creator);
            } else if (type instanceof Class) {
                return (Set<?>) creator.mockClass(((Class<?>) type));
            }
        } else {
            Type genericSuperclass = cla.getGenericSuperclass();
            if (genericSuperclass instanceof ParameterizedType) {
                rawType = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
            }
            return fillSet(rawType, creator);
        }
        return newInstance(cla, creator);
    }

    private Set<?> fillSet(Type type, MockDataCreator.Creator creator) {
        Set<Object> set = newInstance(target, creator);
        if (type instanceof Class) {
            for (int i = 0; i < size; i++) {
                Object o = creator.mockClass((Class<?>) type);
                set.add(o);
            }
        } else if (type instanceof ParameterizedType) {
            Type rawType = ((ParameterizedType) type).getRawType();
            if (rawType instanceof Class && Set.class.isAssignableFrom((Class<?>) rawType)) {
                for (int i = 0; i < size; i++) {
                    Object o = fillSet(((ParameterizedType) type).getActualTypeArguments()[0], creator);
                    set.add(o);
                }
            }
        }
        return set;
    }

    public Set<Object> newInstance(Class<?> cla, MockDataCreator.Creator creator) {
        return (Set<Object>) creator.newInstance(cla);
    }

    @Override
    public List<Class<?>> types() {
        List<Class<?>> list = new ArrayList<>();
        list.add(Set.class);
        return list;
    }
}
