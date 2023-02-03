package idea.verlif.mock.data;

import com.alibaba.fastjson2.JSONObject;
import idea.verlif.mock.data.config.filter.impl.ClassKeyFilter;
import idea.verlif.mock.data.config.filter.impl.FieldKeyFilter;
import idea.verlif.mock.data.config.filter.impl.FieldModifierFilter;
import idea.verlif.mock.data.creator.DataCreator;
import idea.verlif.mock.data.domain.*;
import org.junit.*;
import stopwatch.Stopwatch;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MockDataTest {

    private final MockDataCreator creator = new MockDataCreator();

    {
        creator.getConfig()
                .autoCascade(true)
                .forceNew(true)
                .arraySize(2)
                .creatingDepth(3);
    }

    /**
     * 基础类型测试
     */
    @Test
    public void primitiveTest() {
        println(creator.mock(int.class));
        println(creator.mock(Integer.class));
        println(creator.mock(short.class));
        println(creator.mock(Short.class));
        println(creator.mock(long.class));
        println(creator.mock(Long.class));
        println(creator.mock(char.class));
        println(creator.mock(Character.class));
        println(creator.mock(float.class));
        println(creator.mock(Float.class));
        println(creator.mock(double.class));
        println(creator.mock(Double.class));
        println(creator.mock(boolean.class));
        println(creator.mock(Boolean.class));
    }

    /**
     * 其他数据测试
     */
    @Test
    public void otherDataTest() {
        println(creator.mock(Date.class));
        println(creator.mock(LocalDate.class));
        println(creator.mock(LocalTime.class));
        println(creator.mock(LocalDateTime.class));
        println(creator.mock(IEnum.class));
        println(creator.mock(BigDecimal.class));
        println(creator.mock(BigInteger.class));
        println(creator.mock(LocalDate.class));
    }

    /**
     * 基本数组测试
     */
    @Test
    public void intArrayTest() {
        println(creator.mock(int[].class));
        println(creator.mock(int[][].class));
        println(creator.mock(int[][][].class));
        println(creator.mock(Integer[].class));
        println(creator.mock(Integer[][].class));
        println(creator.mock(Integer[][][].class));
        println(creator.mock(new Integer[2]));
        println(creator.mock(new Integer[2][3]));
        println(creator.mock(new Integer[2][3][4]));
    }

    /**
     * 简单对象测试
     */
    @Test
    public void simpleObjectTest() {
        println(creator.mock(SimpleObject.class));
        println(creator.mock(new SimpleObject()));
        println(creator.mock(SimpleObject[].class));
        println(creator.mock(new SimpleObject[2]));
    }

    @Test
    public void complexObjectTest() {
        creator.addDefaultCreator(new DataCreator<MyArrayList>() {
            @Override
            public MyArrayList mock(Class<?> cla, Field field, MockDataCreator.Creator creator) {
                return new MyArrayList();
            }
        });
        println(creator.mock(ComplexObject.class));
        println(creator.mock(new ComplexObject()));
    }

    /**
     * List测试
     */
    @Test
    public void ListTest() {
        creator.getConfig()
                .fieldValue(MyArrayList.class, new DataCreator<MyArrayList>() {
                    @Override
                    public MyArrayList mock(Class<?> cla, Field field, MockDataCreator.Creator creator) {
                        return new MyArrayList();
                    }
                });
        println(creator.mock(List.class));
        println(creator.mock(IList.class));
        println(creator.mock(new IList()));
        println(creator.mock(IListExtend.class));
        println(creator.mock(new IListExtend()));
    }

    /**
     * map测试
     */
    @Test
    public void MapTest() {
        println(creator.mock(Map.class));
        println(creator.mock(IMap.class));
        println(creator.mock(new IMap()));
        println(creator.mock(MyMap.class));
        println(creator.mock(new MyMap<String, Double>()));
        println(creator.mock(MyMapExtend.class));
        println(creator.mock(new MyMapExtend()));
    }

    @Test
    public void circleTest() {
        println(creator.mock(AWithB.class));
        println(creator.mock(AWithB[].class));
        println(creator.mock(new AWithB()));
        println(creator.mock(BWithA.class));
        println(creator.mock(BWithA[].class));
        println(creator.mock(new BWithA()));
        println(creator.mock(SelfC.class));
        println(creator.mock(SelfC[].class));
        println(creator.mock(new SelfC()));
    }

    @Test
    public void customTest() {
        // 固定值测试
        creator.getConfig()
                .fieldValue(IEnum.class, IEnum.HELLO);
        boolean flag = true;
        for (int i = 0; i < 20; i++) {
            IEnum iEnum = creator.mock(IEnum.class);
            if (iEnum != IEnum.HELLO) {
                flag = false;
                break;
            }
        }
        printlnFormatted("IEnum testing result", flag);

        // 自定义数据构建器测试
        final String name = "测试生成数据";
        creator.getConfig()
                .fieldValue(AWithB.class, new DataCreator<AWithB>() {
                    @Override
                    public AWithB mock(Class<?> cla, Field field, MockDataCreator.Creator creator) {
                        AWithB a = new AWithB();
                        a.setName(name);
                        return a;
                    }
                });
        printlnFormatted("DataCreator testing result", name.equals(creator.mock(AWithB.class).getName()));

        // 特选属性固定值测试
        creator.getConfig()
                .fieldValue(AWithB::getName, name);
        printlnFormatted("DataCreator testing result", name.equals(creator.mock(AWithB.class).getName()));

        // 过滤器测试
        creator.getConfig()
                // 表示忽略所有的Double类型
                .filter(new ClassKeyFilter()
                        .ignoredClass(Double.class))
                // 表示仅忽略属性中的Integer类型
                .filter(new FieldKeyFilter()
                        .ignoredField(Integer.class))
                .filter(new FieldModifierFilter()
                        .allowedModifiers(Modifier.PROTECTED, Modifier.PUBLIC)
                        .blockedModifiers(Modifier.PRIVATE));
        println(creator.mock(ModifierObject.class));
        printlnFormatted("Integer not ignored testing result", creator.mock(Integer.class));
        printlnFormatted("Double ignored testing result", creator.mock(Double.class) == null);
    }

    private void println(Object o) {
        if (o == null) {
            printlnFormatted("null", null);
        } else {
            String claName = o.getClass().getName();
            printlnFormatted(claName, o);
        }
    }

    private void printlnFormatted(String desc, Object o) {
        StringBuilder builder = new StringBuilder(desc);
        for (int i = desc.length(); i < 50; i++) {
            builder.append(" ");
        }
        System.out.println(builder + "\t--->\t" + JSONObject.toJSONString(o));
    }

    @Before
    public void pin() {
        Stopwatch.get("this").pin();
    }

    @After
    public void pinOut() {
        Stopwatch stopwatch = Stopwatch.get("this");
        stopwatch.pin();
        System.out.println("---------------------- 本次耗时 >> " + stopwatch.getLastInterval(TimeUnit.MILLISECONDS) + "\n");
    }

    @BeforeClass
    public static void startStopwatch() {
        Stopwatch.start("this");
    }

    @AfterClass
    public static void stopStopwatch() {
        Stopwatch stopwatch = Stopwatch.get("this");
        stopwatch.stop();
        System.out.println("\n---------------------- 累计耗时 >> " + stopwatch.getIntervalLine(TimeUnit.MILLISECONDS) + "  毫秒");
    }
}
