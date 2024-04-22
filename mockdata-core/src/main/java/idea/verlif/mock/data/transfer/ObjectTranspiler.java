package idea.verlif.mock.data.transfer;

public interface ObjectTranspiler<R> {

    /**
     * 转义对象到目标类型
     *
     * @param t      源对象
     * @param target 目标类型
     * @return 转义后的对象；为null则表示转义失败，继续后续流程
     */
    R trans(Object t, Class<R> target);

}
