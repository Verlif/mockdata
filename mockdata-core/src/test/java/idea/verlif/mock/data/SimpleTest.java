package idea.verlif.mock.data;

import com.alibaba.fastjson2.JSONObject;
import idea.verlif.mock.data.config.DataPool;
import idea.verlif.mock.data.config.FieldDataPool;
import idea.verlif.mock.data.config.FieldOption;
import idea.verlif.mock.data.creator.DataCreator;
import idea.verlif.mock.data.creator.DataFiller;
import idea.verlif.mock.data.creator.InstanceCreator;
import idea.verlif.mock.data.domain.MockSrc;
import idea.verlif.mock.data.domain.MyMapExtend;
import idea.verlif.mock.data.domain.Person;
import idea.verlif.mock.data.example.PropertiesDataPool;
import idea.verlif.mock.data.transfer.DataTranspiler;
import idea.verlif.mock.data.transfer.ObjectTranspiler;
import idea.verlif.reflection.domain.MethodGrc;
import idea.verlif.reflection.util.MethodUtil;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class SimpleTest {

    @Test
    public void test() throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        List<Method> methods = MethodUtil.getAllMethods(MyMapExtend.class);
        for (Method method : methods) {
            MethodGrc methodGrc = MethodUtil.getMethodGrc(method, MyMapExtend.class);
            System.out.println(methodGrc.getResult());
        }
    }

    @Test
    public void PropertiesDataPoolTest() throws IOException {
        PropertiesDataPool dataPool = new PropertiesDataPool();
        dataPool.load("src/test/resources/data-pool.properties");
        MockDataCreator creator = new MockDataCreator();
        creator.getConfig()
                .appendFieldOption(FieldOption.IGNORED_VALUE | FieldOption.ALLOWED_PRIMITIVE)
                .autoCascade(true)
                .dataPool(dataPool);
        Person person = new Person();
        person.setName("啊这");
        System.out.println(JSONObject.toJSONString(creator.mock(person)));
    }

    @Test
    public void transpilerTest() {
        ObjectTranspiler<Integer> transpiler = new ObjectTranspiler<Integer>() {
            @Override
            public Object trans(Integer integer) {
                return integer + "-xixi";
            }

            @Override
            public Class<?>[] targets() {
                return new Class[]{String.class};
            }
        };
        DataTranspiler dataTranspiler = new DataTranspiler();
        dataTranspiler.addTranspiler(transpiler);
        System.out.println(dataTranspiler.trans(12, String.class));
    }
}
