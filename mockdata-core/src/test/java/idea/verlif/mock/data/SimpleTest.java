package idea.verlif.mock.data;

import com.alibaba.fastjson2.JSONObject;
import idea.verlif.mock.data.config.DataPool;
import idea.verlif.mock.data.config.FieldDataPool;
import idea.verlif.mock.data.config.FieldOption;
import idea.verlif.mock.data.config.MockDataConfig;
import idea.verlif.mock.data.config.creator.RangeSizeCreator;
import idea.verlif.mock.data.creator.data.DictDataCreator;
import idea.verlif.mock.data.domain.IList;
import idea.verlif.mock.data.domain.IListExtend;
import idea.verlif.mock.data.domain.Person;
import idea.verlif.mock.data.example.PropertiesDataPool;
import idea.verlif.mock.data.transfer.DataTranspiler;
import idea.verlif.mock.data.transfer.ObjectTranspiler;
import idea.verlif.mock.data.transfer.TypeTranspiler;
import org.junit.Test;

import java.io.IOException;

public class SimpleTest {

    @Test
    public void test() throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {

        MockDataCreator creator = new MockDataCreator();
        MockDataConfig config = creator.getConfig();

        MockDataConfig copy = config.copy();
        if (copy == config) {

        }
    }

    @Test
    public void fieldDataPoolTest() {
        MockDataCreator creator = new MockDataCreator();
        // 使用属性池的方式
        DataPool dataPool = new FieldDataPool()
                // 只对所有的名为name的String属性填充["小明", "小红", "小刚", "小丽"]
                .typeName(String.class, "name", "小明", "小红", "小刚", "小丽")
                .next()
                // 只对所有的名称中带有address的String属性填充["这里", "那里"]
                .likeName(String.class, "address", "这里", "那里")
                .next();
        // 设置属性数据池
        creator.dataPool(dataPool);
        // 与下面的方式类似，但数据池方式更灵活
        creator.fieldValue(Person::getName, new DictDataCreator<>(new String[]{"小明", "小红", "小刚", "小丽"}))
                .fieldValue(Person::getAddress, new DictDataCreator<>(new String[]{"这里", "那里"}));
        // 开始构建
        Person person = creator.mock(Person.class);
        System.out.println(JSONObject.toJSONString(person));
    }

    @Test
    public void transferTest() {
        ObjectTranspiler<Object> jsonTranspiler = new TypeTranspiler<Object>() {
            @Override
            public Object trans(Object t) {
                return JSONObject.parseObject(t.toString());
            }

            @Override
            public boolean support(Class<?> cla) {
                return String.class == cla;
            }
        };
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
        ObjectTranspiler<String> transpiler = new TypeTranspiler<String>() {
            @Override
            public String trans(Object t) {
                return t + "-xixi";
            }

            @Override
            public boolean support(Class<?> cla) {
                return true;
            }
        };
        DataTranspiler dataTranspiler = new DataTranspiler();
        dataTranspiler.addTranspiler(transpiler);
        System.out.println(dataTranspiler.trans(12, String.class));
        System.out.println(dataTranspiler.trans("321", String.class));
    }
}
