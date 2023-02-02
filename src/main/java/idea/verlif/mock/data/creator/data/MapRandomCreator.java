package idea.verlif.mock.data.creator.data;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.creator.DataCreator;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Verlif
 */
public class MapRandomCreator implements DataCreator<Map<?, ?>> {

    private final int size;

    public MapRandomCreator() {
        this(5);
    }

    public MapRandomCreator(int size) {
        this.size = size;
    }

    @Override
    public Map<?, ?> mock(Class<?> cla, Field field, MockDataCreator.Creator creator) {
        if (field != null) {
            ParameterizedType type = (ParameterizedType) field.getGenericType();
            Type[] arguments = type.getActualTypeArguments();
            return fillMap(arguments[0], arguments[1], creator);
        } else {
            Type genericSuperclass = cla.getGenericSuperclass();
            if (genericSuperclass instanceof ParameterizedType) {
                Type[] arguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
                return fillMap(arguments[0], arguments[1], creator);
            }
        }
        return new HashMap<>();
    }

    private Map<Object, Object> fillMap(Type keyType, Type valueType, MockDataCreator.Creator creator) {
        Map<Object, Object> map = new HashMap<>();
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
            if (rawType instanceof Class && Map.class.isAssignableFrom((Class<?>) rawType)) {
                Type[] arguments = ((ParameterizedType) type).getActualTypeArguments();
                return fillMap(arguments[0], arguments[1], creator);
            }
        }
        return null;
    }

    @Override
    public List<Class<?>> types() {
        List<Class<?>> list = new ArrayList<>();
        list.add(Map.class);
        list.add(HashMap.class);
        return list;
    }
}
