package idea.verlif.mock.data.creator;

import idea.verlif.mock.data.MockDataCreator;

import java.lang.reflect.Field;

/**
 * @author Verlif
 */
public interface DataFiller<T> extends DataCreator<T> {

    void fill(T t, MockDataCreator.Creator creator);

    T newInstance(Class<?> cla, MockDataCreator.Creator creator);

    @Override
    default T mock(Class<?> cla, Field field, MockDataCreator.Creator creator) {
        T t = newInstance(cla, creator);
        fill(t, creator);
        return t;
    }

}
