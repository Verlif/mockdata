package idea.verlif.mock.data.creator;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.domain.MockSrc;
import idea.verlif.mock.data.exception.MockDataException;
import idea.verlif.reflection.util.ReflectUtil;

import java.lang.reflect.InvocationTargetException;

/**
 * 数据填充器，用于生成数据值
 */
public interface DataFiller<R> extends DataCreator<R> {

    void fill(R t, MockDataCreator.Creator creator);

    default R newInstance(Class<R> cla) {
        try {
            return ReflectUtil.newInstance(cla);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new MockDataException(e);
        }
    }

    @Override
    default R mock(MockSrc src, MockDataCreator.Creator creator) {
        R t = newInstance((Class<R>) src.getClassGrc().getTarget());
        fill(t, creator);
        return t;
    }

}
