package idea.verlif.mock.data.tagpool;

import idea.verlif.mock.data.config.DataPool;
import idea.verlif.reflection.domain.ClassGrc;

import java.util.HashMap;
import java.util.Map;

/**
 * 标签数据池
 */
public class TagDataPool implements DataPool {

    private final Map<String, DataPool> dataPoolMap;

    public TagDataPool() {
        this.dataPoolMap = new HashMap<>();
    }

    @Override
    public Object[] getValues(ClassGrc classGrc, String key) {
        DataPool dataPool = dataPoolMap.get(TagSelector.getTag());
        if (dataPool != null) {
            Object[] values = dataPool.getValues(classGrc, key);
            if (values != null && values.length > 0) {
                return values;
            }
        }
        return null;
    }

    public void addDataPool(String tag, DataPool dataPool) {
        dataPoolMap.put(tag, dataPool);
    }

}
