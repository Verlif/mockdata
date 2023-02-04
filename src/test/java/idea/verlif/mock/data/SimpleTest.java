package idea.verlif.mock.data;

import idea.verlif.mock.data.domain.MyMap;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class SimpleTest {

    @Test
    public void test() {
        debug(new MyMap<String, Integer>());
    }

    private void debug(Object o) {

        Class<?> cla = o.getClass();
        Type type = cla.getGenericSuperclass();
        Type[] types = ((ParameterizedType) type).getActualTypeArguments();

        System.out.println(types);
    }
}
