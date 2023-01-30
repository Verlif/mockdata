package idea.verlif.mock.data.config.filter;

import java.lang.reflect.Field;

/**
 * 属性过滤器
 */
public interface FieldFilter {

    /**
     * 是否允许此属性进行构建
     *
     * @param field 目标属性
     * @return 是否允许构建
     */
    boolean accept(Field field);
}
