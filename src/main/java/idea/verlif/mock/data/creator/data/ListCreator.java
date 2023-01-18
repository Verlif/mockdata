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

    @Override
    public List<?> mock(Field field, MockDataCreator creator) {
        List<Object> list = new ArrayList<>();
        ParameterizedType type = (ParameterizedType) field.getGenericType();
        Type[] arguments = type.getActualTypeArguments();
        if (arguments.length > 0) {
            try {
                Object o = creator.mock(Class.forName(arguments[0].getTypeName()));
                list.add(o);
            } catch (ClassNotFoundException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }

}
