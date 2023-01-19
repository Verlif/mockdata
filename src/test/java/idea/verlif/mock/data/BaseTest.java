package idea.verlif.mock.data;

import idea.verlif.mock.data.config.MockDataConfig;
import idea.verlif.mock.data.creator.DataCreator;
import idea.verlif.mock.data.creator.data.*;
import idea.verlif.mock.data.domain.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import stopwatch.Stopwatch;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Verlif
 */
public class BaseTest {

    @Test
    public void fieldTest() throws NoSuchMethodException {
        System.out.println(BaseTest.class.getDeclaredMethod("fieldTest").equals(BaseTest.class.getDeclaredMethod("fieldTest")));
    }

    @Test
    public void baseUseTest() throws IllegalAccessException {
        // 创建数据构造器
        MockDataCreator creator = new MockDataCreator();
        // 使用基础数据包
        creator.useBaseData();
        // 使用拓展包
        creator.useExtendData();
        // 获取构造器的当前配置
        MockDataConfig config = creator.getConfig();
        // 添加需要级联构造的类
        config.addCascadeCreateKey(Person.class);
        // 构造测试
        for (int i = 0; i < 10; i++) {
            System.out.println(creator.mock(Person.class));
        }

        System.out.println("------>>> 使用字典生成name属性");
        config.addFieldCreator(Person::getName, new DictDataCreator<>(new String[]{
                "小明", "小红", "小王", "小赵", "小李", "小周", "小强"
        }));
        System.out.println("------>>> 限制id属性生成范围");
        config.addFieldCreator(Person::getId, new LongRandomCreator(0L, 9999L));
        System.out.println("------>>> 限制age属性生成范围");
        config.addFieldCreator(Person::getAge, new IntegerRandomCreator(0, 200));
        System.out.println("------>>> 忽略birthday属性");
        config.addIgnoredField(Person::getBirthday);
        System.out.println("------>>> 自定义secondChild属性");
        config.addFieldCreator(Person::getSecondChild, new DataCreator<Person>() {

            private final Random random = new Random();

            @Override
            public Person mock(Field field, MockDataCreator creator) {
                if (random.nextBoolean()) {
                    return new Person("啊哈");
                } else {
                    return null;
                }
            }
        });
//        System.out.println("------>>> 关闭private属性构建");
//        config.setAllowPrivate(true);
//        System.out.println("------>>> 关闭public属性构建");
//        config.setAllowPublic(false);
        Person[][] personArray = creator.mock(new Person[2][5]);
        for (int i = 0; i < 10; i++) {
            System.out.println(creator.mock(Person.class));
        }

    }

    @Test
    public void mockTest() throws IllegalAccessException {
        MockDataCreator creator = new MockDataCreator();
        creator.useBaseData();
        MockDataConfig config = new MockDataConfig();
        config.addCascadeCreateKey(A.class);
        config.addCascadeCreateKey(B.class);
        config.ignoredUnknownField();
        creator.setConfig(config);
        config.addCascadeCreateKey(Person.class);
//        config.addFieldCreator(IListExtend::getList, (DataCreator<List<?>>) (field, creator1) -> {
//            System.out.println("Hello");
//            return new ArrayList<>();
//        });
        config.setCircleCount(2);
        config.addFieldCreator(Person::getId, new LongRandomCreator(100, 300));
        Person[][] mock = creator.mock(Person[][].class);
        System.out.println(Arrays.toString(mock));
        Person[][] mock2 = creator.mock(new Person[2][5]);
        System.out.println(Arrays.toString(mock2));
        IList<String> list = creator.mock(new IListExtend());
        System.out.println(Arrays.toString(list.getList().toArray()));
    }

    @Test
    public void randomTest() throws IllegalAccessException {
        MockDataCreator creator = new MockDataCreator();
        creator.addDefaultCreator(new LocalDateCreator());
        creator.useExtendData();
        for (int i = 0; i < 100; i++) {
            List data = creator.mock(new ArrayList<Person>());
            System.out.println(data);
        }
    }

    @Before
    public void startStopwatch() {
        Stopwatch.start("this");
    }

    @After
    public void stopStopwatch() {
        Stopwatch stopwatch = Stopwatch.get("this");
        stopwatch.stop();
        System.out.println("---- 累计耗时 >> " + stopwatch.getIntervalLine(TimeUnit.MILLISECONDS) + "  毫秒");
    }
}
