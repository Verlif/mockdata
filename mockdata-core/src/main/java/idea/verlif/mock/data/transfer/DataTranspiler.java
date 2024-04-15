package idea.verlif.mock.data.transfer;

import idea.verlif.mock.data.transfer.base.ToStringTranspiler;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据转义器
 */
public class DataTranspiler {

    private final Map<Class<?>, Map<Class<?>, ObjectTranspiler>> transpilerMap;

    public DataTranspiler() {
        this.transpilerMap = new HashMap<>();
        // 添加基础的toString转义
        addTranspiler(new ToStringTranspiler());
    }

    /**
     * 添加转义器
     */
    public void addTranspiler(ObjectTranspiler<?> transpiler) {
        Map<Class<?>, ObjectTranspiler> resultMap = transpilerMap.computeIfAbsent(transpiler.handled(), cla -> new HashMap<>());
        for (Class<?> target : transpiler.targets()) {
            resultMap.put(target, transpiler);
        }
    }

    /**
     * 转义数据
     *
     * @param o      源数据
     * @param target 目标类型
     * @return 转移后的数据，与 target 参数匹配。若没有合适的转义器则会返回 null 。
     */
    public <R> R trans(Object o, Class<R> target) {
        Class<?> handleCla = o.getClass();
        Map<Class<?>, ObjectTranspiler> reslultMap;
        do {
            reslultMap = transpilerMap.get(handleCla);
            handleCla = handleCla.getSuperclass();
        } while (reslultMap == null && handleCla != null);
        if (reslultMap != null) {
            ObjectTranspiler objectTranspiler = reslultMap.get(target);
            if (objectTranspiler != null) {
                return (R) objectTranspiler.trans(o);
            }
        }
        return null;
    }

}
