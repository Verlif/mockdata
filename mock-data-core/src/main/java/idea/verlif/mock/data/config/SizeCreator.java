package idea.verlif.mock.data.config;

/**
 * @author Verlif
 */
public interface SizeCreator {

    /**
     * 获取数字大小
     *
     * @param cla 类类型
     * @return 数字大小
     */
    int getSize(Class<?> cla);
}
