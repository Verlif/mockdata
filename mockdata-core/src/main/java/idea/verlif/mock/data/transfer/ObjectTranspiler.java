package idea.verlif.mock.data.transfer;

import idea.verlif.mock.data.exception.MockDataException;
import idea.verlif.reflection.domain.ClassGrc;
import idea.verlif.reflection.util.ReflectUtil;

import java.util.Map;

public interface ObjectTranspiler<T> {

    String GENERICS_KEY = "T";

    Object trans(T t);

    /**
     * 转义器支持的类型
     */
    default Class<?> handled() {
        Map<String, ClassGrc> genericsMap;
        try {
            genericsMap = ReflectUtil.getGenericsMap(this.getClass());
        } catch (NoSuchFieldException | IllegalAccessException exception) {
            throw new MockDataException(exception);
        }
        ClassGrc grc = genericsMap.get(GENERICS_KEY);
        if (grc != null) {
            return grc.getTarget();
        }
        return null;
    }

    Class<?>[] targets();
}
