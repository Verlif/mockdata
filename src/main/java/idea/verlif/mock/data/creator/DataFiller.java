package idea.verlif.mock.data.creator;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.domain.MockSrc;

/**
 * @author Verlif
 */
public interface DataFiller<T> extends DataCreator<T> {

    void fill(T t, MockDataCreator.Creator creator);

    T newInstance(Class<?> cla, MockDataCreator.Creator creator);

    @Override
    default T mock(MockSrc src, MockDataCreator.Creator creator) {
        T t = newInstance(src.getClassGrc().getTarget(), creator);
        fill(t, creator);
        return t;
    }

}
