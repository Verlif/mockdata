package idea.verlif.mock.data.transfer;

import idea.verlif.mock.data.transfer.base.ToStringTranspiler;
import idea.verlif.reflection.util.ReflectUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据转义器
 */
public class DataTranspiler {

    private final List<ObjectTranspiler<?>> objectTranspilerList;

    public DataTranspiler() {
        this.objectTranspilerList = new ArrayList<>();
        // 添加基础的toString转义
        addTranspiler(new ToStringTranspiler());
    }

    /**
     * 添加转义器
     */
    public void addTranspiler(ObjectTranspiler<?> transpiler) {
        objectTranspilerList.add(transpiler);
    }

    public List<ObjectTranspiler<?>> getObjectTranspilerList() {
        return objectTranspilerList;
    }

    /**
     * 转义数据
     *
     * @param o      源数据
     * @param target 目标类型
     * @return 转移后的数据，与 target 参数匹配。若没有合适的转义器则会返回 null 。
     */
    public <R> R trans(Object o, Class<R> target) {
        if (!objectTranspilerList.isEmpty()) {
            // 倒序匹配
            for (int i = objectTranspilerList.size() - 1; i > -1; i--) {
                ObjectTranspiler transpiler = objectTranspilerList.get(i);
                Object result = transpiler.trans(o, target);
                if (result != null && ReflectUtil.likeClass(result.getClass(), target)) {
                    return (R) result;
                }
            }
        }
        return null;
    }

}
