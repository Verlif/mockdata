package idea.verlif.mock.data.config;

/**
 * 属性选项
 */
public interface FieldOption {

    /**
     * 允许空值的属性
     */
    int ALLOWED_NULL = 1;

    /**
     * 允许有值的属性
     */
    int ALLOWED_NOTNULL = 1 << 1;

    /**
     * 忽略属性值是否存在
     */
    int IGNORED_VALUE = ALLOWED_NULL + ALLOWED_NOTNULL;

    /**
     * 允许类属性，包括String类型
     */
    int ALLOWED_CLASS = 1 << 2;

    /**
     * 允许基础类型，不包括String类型
     */
    int ALLOWED_PRIMITIVE = 1 << 3;

    /**
     * 忽略属性类型
     */
    int IGNORED_TYPE = ALLOWED_CLASS + ALLOWED_PRIMITIVE;
}
