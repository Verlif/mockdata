package idea.verlif.mock.data.creator.data;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.creator.DataCreator;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
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
        ParameterizedType genericType = (ParameterizedType) field.getGenericType();
        Type[] arguments = genericType.getActualTypeArguments();
        Map<Object, Object> map = new HashMap<>();
        try {
            Class<?> keyCla = Class.forName(arguments[0].getTypeName());
            Class<?> valueCla = Class.forName(arguments[1].getTypeName());
            for (int i = 0; i < size; i++) {
                map.put(creator.mockClass(keyCla), creator.mockClass(valueCla));
            }
        } catch (ClassNotFoundException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

}
