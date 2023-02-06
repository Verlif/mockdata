package idea.verlif.mock.data.creator;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.domain.MockSrc;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Verlif
 */
public interface DataFiller<T> extends DataCreator<T> {

    void fill(T t, MockDataCreator.Creator creator);

    T newInstance(Class<?> cla, MockDataCreator.Creator creator);

    @Override
    default T mock(MockSrc src, MockDataCreator.Creator creator) {
        Type type = src.getType();
        Class<?> cla;
        if (type instanceof ParameterizedType) {
            cla = (Class<?>) ((ParameterizedType) type).getRawType();
        } else {
            cla = (Class<?>) type;
        }
        Field field = src.getField();
        T t = newInstance(cla, creator);
        fill(t, creator);
        return t;
    }

}
