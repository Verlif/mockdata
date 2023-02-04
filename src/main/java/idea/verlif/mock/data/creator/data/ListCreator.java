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

    private Integer size;

    private Class<?> target;

    public ListCreator() {
    }

    public ListCreator(int size) {
        this.size = size;
    }

    @Override
    public List<?> mock(Class<?> cla, Field field, MockDataCreator.Creator creator) {
        this.target = cla == List.class ? ArrayList.class : cla;
        if (this.size == null) {
            this.size = creator.getMockConfig().getArraySize(cla);
        }
        Type rawType = Object.class;
        if (field != null) {
            Type type = field.getGenericType();
            if (type instanceof ParameterizedType) {
                Type argument = ((ParameterizedType) type).getActualTypeArguments()[0];
                return fillList(argument, creator);
            } else if (type instanceof Class) {
                return (List<?>) creator.mockClass(((Class<?>) type));
            }
        } else {
            Type genericSuperclass = cla.getGenericSuperclass();
            if (genericSuperclass instanceof ParameterizedType) {
                rawType = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
            }
            return fillList(rawType, creator);
        }
        return newInstance(cla, creator);
    }

    private List<?> fillList(Type type, MockDataCreator.Creator creator) {
        List<Object> list = newInstance(target, creator);
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

    public List<Object> newInstance(Class<?> cla, MockDataCreator.Creator creator) {
        return (List<Object>) creator.newInstance(cla);
    }

    @Override
    public List<Class<?>> types() {
        List<Class<?>> list = new ArrayList<>();
        list.add(List.class);
        return list;
    }
}
