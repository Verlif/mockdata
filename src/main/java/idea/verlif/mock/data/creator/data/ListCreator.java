package idea.verlif.mock.data.creator.data;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.creator.DataCreator;
import idea.verlif.mock.data.domain.MockSrc;

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
    public List<?> mock(MockSrc src, MockDataCreator.Creator creator) {
        Type type = src.getType();
        this.target = src.getRawClass();
        if (this.target == List.class) {
            this.target = ArrayList.class;
        }
        if (this.size == null) {
            this.size = creator.getMockConfig().getArraySize(this.target);
        }
        if (type instanceof ParameterizedType) {
            Type[] arguments = ((ParameterizedType) type).getActualTypeArguments();
            return fillList(arguments[0], creator);
        } else if (type instanceof Class) {
            Type genericSuperclass = this.target.getGenericSuperclass();
            if (genericSuperclass instanceof ParameterizedType) {
                Type argument = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
                return fillList(argument, creator);
            }
        }
        return (List<?>) src.getOldObj();
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
            if (rawType instanceof Class) {
                if (List.class.isAssignableFrom((Class<?>) rawType)) {
                    for (int i = 0; i < size; i++) {
                        Object o = fillList(((ParameterizedType) type).getActualTypeArguments()[0], creator);
                        list.add(o);
                    }
                } else {
                    for (int i = 0; i < size; i++) {
                        Object o = creator.mockSrc(new MockSrc(type, null));
                        list.add(o);
                    }
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
        list.add(ArrayList.class);
        return list;
    }
}
