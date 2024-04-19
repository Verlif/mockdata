package idea.verlif.mock.data;

import com.alibaba.fastjson2.JSONObject;
import idea.verlif.mock.data.config.FieldOption;
import idea.verlif.mock.data.domain.Person;
import idea.verlif.mock.data.example.PropertiesDataPool;
import idea.verlif.mock.data.transfer.DataTranspiler;
import idea.verlif.mock.data.transfer.ObjectTranspiler;
import org.junit.Test;

import java.io.IOException;

public class SimpleTest {

    @Test
    public void test() throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        MockDataCreator creator = new MockDataCreator();
        creator.getConfig().autoCascade(true);
        creator.fieldObject(Person::getName, "嘻嘻", "哈哈");
        for (int i = 0; i < 10; i++) {
            Person mock = creator.mock(Person.class);
            System.out.println(mock.getName());
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
