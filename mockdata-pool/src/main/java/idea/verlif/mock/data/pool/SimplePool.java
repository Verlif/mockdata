package idea.verlif.mock.data.pool;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.creator.DataCreator;
import idea.verlif.mock.data.domain.MockSrc;
import idea.verlif.reflection.domain.ClassGrc;

public interface SimplePool extends DataCreator<Object> {

    /**
     * 获取数据
     *
     * @param classGrc 目标类型
     * @param key      目标属性名
     */
    Object fetch(ClassGrc classGrc, String key);

    default Object mock(MockSrc src, MockDataCreator.Creator creator) {
        return fetch(src.getClassGrc(), null);
    }
}
