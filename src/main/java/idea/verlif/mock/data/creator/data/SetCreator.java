package idea.verlif.mock.data.creator.data;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.creator.DataCreator;
import idea.verlif.mock.data.domain.MockSrc;

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
    public Set<?> mock(MockSrc src, MockDataCreator.Creator creator) {
        Type type = src.getType();
        this.target = src.getRawClass();
        if (this.target == Set.class) {
            this.target = HashSet.class;
        }
        if (this.size == null) {
            this.size = creator.getMockConfig().getArraySize(this.target);
        }
        if (type instanceof ParameterizedType) {
            Type[] arguments = ((ParameterizedType) type).getActualTypeArguments();
            return fillSet(arguments[0], creator);
        } else if (type instanceof Class) {
            Type genericSuperclass = this.target.getGenericSuperclass();
            if (genericSuperclass instanceof ParameterizedType) {
                Type argument = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
                return fillSet(argument, creator);
            }
        }
        return (Set<?>) src.getOldObj();
    }

    private Set<?> fillSet(Type type, MockDataCreator.Creator creator) {
        Set<Object> Set = newInstance(target, creator);
        if (type instanceof Class) {
            for (int i = 0; i < size; i++) {
                Object o = creator.mockClass((Class<?>) type);
                Set.add(o);
            }
        } else if (type instanceof ParameterizedType) {
            Type rawType = ((ParameterizedType) type).getRawType();
            if (rawType instanceof Class) {
                if (Set.class.isAssignableFrom((Class<?>) rawType)) {
                    for (int i = 0; i < size; i++) {
                        Object o = fillSet(((ParameterizedType) type).getActualTypeArguments()[0], creator);
                        Set.add(o);
                    }
                } else {
                    for (int i = 0; i < size; i++) {
                        Object o = creator.mockSrc(new MockSrc(type, null));
                        Set.add(o);
                    }
                }
            }
        }
        return Set;
    }

    public Set<Object> newInstance(Class<?> cla, MockDataCreator.Creator creator) {
        return (Set<Object>) creator.newInstance(cla);
    }

    @Override
    public List<Class<?>> types() {
        List<Class<?>> set = new ArrayList<>();
        set.add(Set.class);
        set.add(HashSet.class);
        return set;
    }
}
