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
        Map<Object, Object> map = new HashMap<>();
        if (field == null) {
            return map;
        }
        ParameterizedType genericType = (ParameterizedType) field.getGenericType();
        Type[] arguments = genericType.getActualTypeArguments();
        if (arguments[0] instanceof Class && arguments[1] instanceof Class) {
            try {
                Class<?> keyCla = Class.forName(arguments[0].getTypeName());
                Class<?> valueCla = Class.forName(arguments[1].getTypeName());
                for (int i = 0; i < size; i++) {
                    map.put(creator.mockClass(keyCla), creator.mockClass(valueCla));
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return map;
    }

    @Override
    public List<Class<?>> types() {
        List<Class<?>> list = new ArrayList<>();
        list.add(Map.class);
        list.add(HashMap.class);
        return list;
    }
}
