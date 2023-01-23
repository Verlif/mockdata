package idea.verlif.mock.data.util;

import java.lang.reflect.Field;

/**
 * 命名工具类
 *
 * @author Verlif
 */
public class NamingUtil {

    public static final String KEY_SUFFIX_CLASS = ".class";

    /**
     * 获取属性的key值
     *
     * @param field 属性对象
     * @return 属性的key值
     */
    public static String getKeyName(Field field) {
        return field.getDeclaringClass().getName() + "." + field.getName();
    }

    /**
     * 获取属性的key值
     *
     * @param cla 属性类
     * @return 属性的key值
     */
    public static String getKeyName(Class<?> cla) {
        return cla.getName() + KEY_SUFFIX_CLASS;
    }
}
