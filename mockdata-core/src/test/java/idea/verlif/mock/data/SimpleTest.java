package idea.verlif.mock.data;

import com.alibaba.fastjson2.JSONObject;
import idea.verlif.mock.data.config.DataPool;
import idea.verlif.mock.data.config.FieldDataPool;
import idea.verlif.mock.data.config.FieldOption;
import idea.verlif.mock.data.creator.DataCreator;
import idea.verlif.mock.data.creator.DataFiller;
import idea.verlif.mock.data.creator.InstanceCreator;
import idea.verlif.mock.data.domain.MockSrc;
import idea.verlif.mock.data.domain.Person;
import idea.verlif.mock.data.example.PropertiesDataPool;
import idea.verlif.mock.data.transfer.DataTranspiler;
import idea.verlif.mock.data.transfer.ObjectTranspiler;
import org.junit.Test;

import java.io.IOException;

public class SimpleTest {

    @Test
    public void test() {
        MockDataCreator creator = new MockDataCreator();
        creator.getConfig().autoCascade(true);
        DataCreator<Person> dataCreator = new DataCreator<Person>() {
            @Override
            public Person mock(MockSrc src, MockDataCreator.Creator creator) {
                Person person = new Person();
                person.setName("DataCreator");
                return person;
            }
        };
        System.out.println(dataCreator.type());
        DataFiller<Person> dataFiller = new DataFiller<Person>() {
            @Override
            public void fill(Person person, MockDataCreator.Creator creator) {
                person.setName("DataFiller测试");
            }
        };
        System.out.println(dataFiller.type());
        InstanceCreator<Person> instanceCreator = new InstanceCreator<Person>() {
            @Override
            public Person newInstance(MockDataCreator creator) {
                return new Person();
            }
        };
        DataPool dataPool = new FieldDataPool()
                .typeName(String.class, "name", "小明", "小红", "小刚", "小丽")
                .next()
                .likeName(String.class, "address", "这里", "那里")
                .next();
        creator.dataPool(dataPool);
        creator.getConfig().forceNew(false).useGetter(true);
        System.out.println(instanceCreator.matched());
        Person mock = creator.mock(Person.class);
        System.out.println(mock.getName());
        System.out.println(mock.getNickname());
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
