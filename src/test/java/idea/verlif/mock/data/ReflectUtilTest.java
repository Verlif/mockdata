package idea.verlif.mock.data;

import idea.verlif.mock.data.domain.test.Dog;
import idea.verlif.mock.data.util.ReflectUtil;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

public class ReflectUtilTest {

    @Test
    public void reflectTest() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Dog dog = ReflectUtil.newInstance(Dog.class, 2);
        System.out.println(dog);
    }
}
