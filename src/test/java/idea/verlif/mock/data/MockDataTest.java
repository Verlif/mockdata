package idea.verlif.mock.data;

import com.alibaba.fastjson2.JSONObject;
import idea.verlif.mock.data.creator.DataCreator;
import idea.verlif.mock.data.domain.*;
import org.junit.*;
import stopwatch.Stopwatch;

import java.lang.reflect.Field;
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

    private void println(Object o) {
        String claName = o.getClass().getName();
        printlnFormatted(claName, o);
    }

    private void printlnFormatted(String desc, Object o) {
        StringBuilder builder = new StringBuilder(desc);
        for (int i = desc.length(); i < 30; i++) {
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
