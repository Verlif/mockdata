package idea.verlif.mock.data.creator;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.domain.TypeGetter;

import java.lang.reflect.Field;

/**
 * @author Verlif
 */
public interface DataCreator<R> extends TypeGetter<R> {

    /**
     * 生成数据
     *
     * @return 生成的数据
     */
    R mock(Field field, MockDataCreator creator);
}
