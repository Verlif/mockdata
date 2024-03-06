package idea.verlif.mock.data;

import com.alibaba.fastjson2.JSONObject;
import idea.verlif.mock.data.config.FieldOption;
import idea.verlif.mock.data.creator.InstanceCreator;
import idea.verlif.mock.data.domain.Person;
import idea.verlif.mock.data.example.PropertiesDataPool;
import org.junit.Test;

import java.io.IOException;

public class SimpleTest {

    @Test
    public void test() {
        InstanceCreator<?> creator = new InstanceCreator<Person>() {
            @Override
            public Person newInstance(MockDataCreator creator) {
                return new Person();
            }
        };
        System.out.println(creator.matched());
    }

    @Test
    public void PropertiesDataPoolTest() throws IOException, ClassNotFoundException {
        PropertiesDataPool dataPool = new PropertiesDataPool();
        dataPool.load("src/test/resources/data-pool.properties");
        MockDataCreator creator = new MockDataCreator();
        creator.getConfig()
                .appendFieldOption(FieldOption.IGNORED_VALUE | FieldOption.ALLOWED_PRIMITIVE)
                .autoCascade(true)
                .fieldDataPool(dataPool);
        Person person = new Person();
        person.setName("啊这");
        System.out.println(JSONObject.toJSONString(creator.mock(person)));
    }

}
