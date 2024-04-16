package idea.verlif.mock.data.config;

import idea.verlif.reflection.domain.ClassGrc;

/**
 * 数据池接口
 */
public interface DataPool {

    /**
     * 从数据池中获取值
     *
     * @param classGrc 类型
     * @param key      区分key值，一般key用于描述属性名。
     * @return 数据池中的值，允许为null
     */
    Object[] getValues(ClassGrc classGrc, String key);
}
