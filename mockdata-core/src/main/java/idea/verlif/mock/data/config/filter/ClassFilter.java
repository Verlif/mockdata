package idea.verlif.mock.data.config.filter;

/**
 * 类过滤器
 */
public interface ClassFilter {

    /**
     * 是否允许此类进行构建
     *
     * @param cla 目标类
     * @return 是否允许构建
     */
    boolean accept(Class<?> cla);
}
