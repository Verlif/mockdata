package idea.verlif.mock.data.creator.data;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.creator.DataCreator;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 列表构造器
 *
 * @author Verlif
 */
public class ListCreator implements DataCreator<List<?>> {

    private final int size;

    public ListCreator() {
        this(5);
    }

    public ListCreator(int size) {
        this.size = size;
    }

    @Override
    public List<?> mock(Class<?> cla, Field field, MockDataCreator.Creator creator) {
        Type rawType = Object.class;
        if (field != null) {
            ParameterizedType type = (ParameterizedType) field.getGenericType();
            Type argument = type.getActualTypeArguments()[0];
            return fillList(argument, creator);
        } else {
            Type genericSuperclass = cla.getGenericSuperclass();
            if (genericSuperclass instanceof ParameterizedType) {
                rawType = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
            }
            return fillList(rawType, creator);
        }
    }

    private List<?> fillList(Type type, MockDataCreator.Creator creator) {
        List<Object> list = new ArrayList<>();
        if (type instanceof Class) {
            for (int i = 0; i < size; i++) {
                Object o = creator.mockClass((Class<?>) type);
                list.add(o);
            }
        } else if (type instanceof ParameterizedType) {
            Type rawType = ((ParameterizedType) type).getRawType();
            if (rawType instanceof Class && List.class.isAssignableFrom((Class<?>) rawType)) {
                for (int i = 0; i < size; i++) {
                    Object o = fillList(((ParameterizedType) type).getActualTypeArguments()[0], creator);
                    list.add(o);
                }
            }
        }
        return list;
    }

    @Override
    public List<Class<?>> types() {
        List<Class<?>> list = new ArrayList<>();
        list.add(List.class);
        list.add(ArrayList.class);
        return list;
    }
}
