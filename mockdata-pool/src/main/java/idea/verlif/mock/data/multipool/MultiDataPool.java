package idea.verlif.mock.data.multipool;

import idea.verlif.mock.data.config.DataPool;
import idea.verlif.reflection.domain.ClassGrc;

import java.util.ArrayList;

/**
 * 序列数据池模型，通过ArrayList来增减和控制使用的数据池
 */
public class MultiDataPool extends ArrayList<DataPool> implements DataPool {
    @Override
    public Object[] getValues(ClassGrc classGrc, String key) {
        for (DataPool dataPool : this) {
            Object[] values = dataPool.getValues(classGrc, key);
            if (values != null && values.length > 0) {
                return values;
            }
        }
        return null;
    }
}
