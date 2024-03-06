package idea.verlif.mock.data;

import com.alibaba.fastjson2.JSONObject;
import idea.verlif.mock.data.config.FieldOption;
import idea.verlif.mock.data.config.MockDataConfig;
import idea.verlif.mock.data.domain.Person;
import idea.verlif.mock.data.example.PropertiesDataPool;
import org.junit.Test;

import java.io.IOException;

public class SimpleTest {

    @Test
    public void test() {
        MockDataConfig config = new MockDataConfig();
        System.out.println(config.getFieldOptions() + "-" + config.isForceNew());
        config.forceNew(true);
        System.out.println(config.getFieldOptions() + "-" + config.isForceNew());
        config.forceNew(false);
        System.out.println(config.getFieldOptions() + "-" + config.isForceNew());
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
