package idea.verlif.mock.data.creator.data;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.creator.DataCreator;
import idea.verlif.mock.data.domain.MockSrc;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Verlif
 */
public class MapCreator implements DataCreator<Map<?, ?>> {

    private Integer size;
    private Class<?> target;

    public MapCreator() {
    }

    public MapCreator(int size) {
        this.size = size;
    }

    @Override
    public Map<?, ?> mock(MockSrc src, MockDataCreator.Creator creator) {
        Type type = src.getType();
        this.target = src.getRawClass();
        if (this.target == Map.class) {
            this.target = HashMap.class;
        }
        if (this.size == null) {
            this.size = creator.getMockConfig().getArraySize(this.target);
        }
        if (type instanceof ParameterizedType) {
            Type[] arguments = ((ParameterizedType) type).getActualTypeArguments();
            return fillMap(arguments[0], arguments[1], creator);
        } else if (type instanceof Class) {
            Type genericSuperclass = this.target.getGenericSuperclass();
            if (genericSuperclass instanceof ParameterizedType) {
                Type[] arguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
                return fillMap(arguments[0], arguments[1], creator);
            }
        }
        return new HashMap<>();
    }

    private Map<Object, Object> fillMap(Type keyType, Type valueType, MockDataCreator.Creator creator) {
        Map<Object, Object> map = newInstance(target, creator);
        for (int i = 0; i < size; i++) {
            Object key = getObjectOfType(keyType, creator);
            Object value = getObjectOfType(valueType, creator);
            map.put(key, value);
        }
        return map;
    }

    private Object getObjectOfType(Type type, MockDataCreator.Creator creator) {
        if (type instanceof Class) {
            return creator.mockClass((Class<?>) type);
        } else if (type instanceof ParameterizedType) {
            Type rawType = ((ParameterizedType) type).getRawType();
            if (rawType instanceof Class) {
                if (Map.class.isAssignableFrom((Class<?>) rawType)) {
                    Type[] arguments = ((ParameterizedType) type).getActualTypeArguments();
                    Object o = fillMap(arguments[0], arguments[1], creator);
                } else {
                    return creator.mockSrc(new MockSrc(type, null));
                }
            }
        }
        return null;
    }

    public Map<Object, Object> newInstance(Class<?> cla, MockDataCreator.Creator creator) {
        return (Map<Object, Object>) creator.newInstance(cla);
    }

    @Override
    public List<Class<?>> types() {
        List<Class<?>> list = new ArrayList<>();
        list.add(Map.class);
        list.add(HashMap.class);
        return list;
    }
}
