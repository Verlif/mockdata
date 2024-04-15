package idea.verlif.mock.data.pool.template;

import idea.verlif.mock.data.pool.DictSimplePool;

/**
 * 性别字符串池（男、女）
 */
public class GenderStringPool extends DictSimplePool<String> {

    public GenderStringPool() {
        super(new String[]{"男", "女"});
    }

    @Override
    public Class<?> type() {
        return String.class;
    }
}
