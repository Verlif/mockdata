package idea.verlif.mock.data;

import idea.verlif.mock.data.domain.IListExtend;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class SimpleTest {

    @Test
    public void test() {
        Type tmp = IListExtend.class.getGenericSuperclass();
        Type type = ((ParameterizedType) tmp).getActualTypeArguments()[0];
        System.out.println(type.getTypeName());
        tmp = ArrayList.class.getGenericSuperclass();
        type = ((ParameterizedType) tmp).getActualTypeArguments()[0];
        System.out.println(type.getTypeName());
    }
}
