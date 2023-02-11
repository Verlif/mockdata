package idea.verlif.mock.data;

import com.alibaba.fastjson2.JSONObject;
import idea.verlif.mock.data.domain.Person;
import idea.verlif.mock.data.example.PropertiesDataPool;
import org.junit.Test;

import java.io.IOException;

public class SimpleTest {

    @Test
    public void test() throws IOException, ClassNotFoundException {
        PropertiesDataPool dataPool = new PropertiesDataPool();
        dataPool.load("src/test/resources/data-pool.properties");
        MockDataCreator creator = new MockDataCreator();
        creator.getConfig().forceNew(true).autoCascade(true).fieldDataPool(dataPool);
        System.out.println(JSONObject.toJSONString(creator.mock(Person.class)));
    }

}
