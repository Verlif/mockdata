package idea.verlif.mock.data.transfer;

/**
 * 类型转义器，转义指定类型
 */
public interface TypeTranspiler<R> extends ObjectTranspiler<R> {

    /**
     * 转义对象
     *
     * @param t 源对象。传入的对象类型只会与{@link #support(Class)}为true时的传入类型相同。
     * @return 转义后的目标类型对象
     */
    R trans(Object t);

    @Override
    default R trans(Object t, Class<R> target) {
        if (support(t.getClass())) {
            return trans(t);
        }
        return null;
    }

    /**
     * 是否支持源类型；当支持目标类型时，则会调用{@link #trans(Object)}方法进行转义。
     *
     * @param cla 目标类型
     * @return {@code true} - 支持目标类型；{@code false} - 不对此类型进行转义
     */
    boolean support(Class<?> cla);
}
