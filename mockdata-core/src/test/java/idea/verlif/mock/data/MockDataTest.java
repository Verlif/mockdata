package idea.verlif.mock.data;

import com.alibaba.fastjson2.JSONObject;
import idea.verlif.mock.data.config.FieldDataPool;
import idea.verlif.mock.data.config.MockDataConfig;
import idea.verlif.mock.data.config.filter.impl.ClassKeyFilter;
import idea.verlif.mock.data.config.filter.impl.FieldKeyFilter;
import idea.verlif.mock.data.config.filter.impl.FieldModifierFilter;
import idea.verlif.mock.data.creator.DataCreator;
import idea.verlif.mock.data.creator.InstanceCreator;
import idea.verlif.mock.data.creator.data.DictDataCreator;
import idea.verlif.mock.data.domain.*;
import idea.verlif.mock.data.transfer.ObjectTranspiler;
import org.junit.*;
import stopwatch.Stopwatch;

import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class MockDataTest {

    private static int total;
    private static int falseCount;
    private static int trueCount;

    private final MockDataCreator creator = new MockDataCreator();

    {
        creator.getConfig()
                .arraySize(2)
                .creatingDepth(3);
    }

    /**
     * 基础类型测试
     */
    @Test
    public void primitiveTest() {
        check(creator.mock(int.class), integer -> integer != 0);
        check(creator.mock(Integer.class), o -> o != 0);
        check(creator.mock(short.class), o -> o != 0);
        check(creator.mock(Short.class), o -> o != 0);
        check(creator.mock(long.class), o -> o != 0);
        check(creator.mock(Long.class), o -> o != 0);
        check(creator.mock(char.class), Objects::nonNull);
        check(creator.mock(Character.class), Objects::nonNull);
        check(creator.mock(float.class), o -> o != 0);
        check(creator.mock(Float.class), o -> o != 0);
        check(creator.mock(double.class), o -> o != 0);
        check(creator.mock(Double.class), o -> o != 0);
        check(creator.mock(boolean.class), o -> true);
        check(creator.mock(Boolean.class), Objects::nonNull);
        check(creator.mock(String.class), Objects::nonNull);
    }

    /**
     * 其他数据测试
     */
    @Test
    public void otherDataTest() {
        check(creator.mock(Date.class), Objects::nonNull);
        check(creator.mock(LocalDate.class), Objects::nonNull);
        check(creator.mock(LocalTime.class), Objects::nonNull);
        check(creator.mock(LocalDateTime.class), Objects::nonNull);
        check(creator.mock(IEnum.class), Objects::nonNull);
        check(creator.mock(BigDecimal.class), Objects::nonNull);
        check(creator.mock(BigInteger.class), Objects::nonNull);
        check(creator.mock(LocalDate.class), Objects::nonNull);
    }

    /**
     * 基本数组测试
     */
    @Test
    public void intArrayTest() {
        check(creator.mock(int[].class), o -> o != null && o.length > 0 && o[0] != 0);
        check(creator.mock(int[][].class), o -> o != null && o.length > 0 && o[0][0] != 0);
        check(creator.mock(int[][][].class), o -> o != null && o.length > 0 && o[0][0][0] != 0);
        check(creator.mock(Integer[].class), o -> o != null && o.length > 0 && o[0] != 0);
        check(creator.mock(Integer[][].class), o -> o != null && o.length > 0 && o[0][0] != 0);
        check(creator.mock(Integer[][][].class), o -> o != null && o.length > 0 && o[0][0][0] != 0);
        check(creator.mock(new Integer[2]), o -> o != null && o.length == 2 && o[0] != 0);
        check(creator.mock(new Integer[2][3]), o -> o != null && o.length == 2 && o[0].length == 3 && o[0][0] != 0);
        check(creator.mock(new Integer[2][3][4]), o -> o != null && o.length == 2 && o[0].length == 3 && o[0][0].length == 4 && o[0][0][0] != 0);
    }

    /**
     * 简单对象测试
     */
    @Test
    public void simpleObjectTest() {
        check(creator.mock(SimpleObject.class),
                o -> o != null && o.getAnEnum() != null && o.getSi() != 0
                        && o.getsSa().length > 0 && o.getsSa()[0] != 0
                        && o.getDoubleMap() != null && !o.getDoubleMap().isEmpty()
                        && o.getaWithB() != null && o.getaWithB().getB() != null
                        && o.getStrings() != null && !o.getStrings().isEmpty());
        check(creator.mock(new SimpleObject()),
                o -> o != null && o.getAnEnum() != null && o.getSi() != 0
                        && o.getsSa().length > 0 && o.getsSa()[0] != 0
                        && o.getsSa().length > 0 && o.getsSa()[0] != 0
                        && o.getDoubleMap() != null && !o.getDoubleMap().isEmpty()
                        && o.getaWithB() != null && o.getaWithB().getB() != null
                        && o.getStrings() != null && !o.getStrings().isEmpty());
        check(creator.mock(SimpleObject[].class),
                o -> o != null && o.length > 0 && o[0].getAnEnum() != null && o[0].getSi() != 0
                        && o[0].getsSa().length > 0 && o[0].getsSa()[0] != 0
                        && o[0].getDoubleMap() != null && !o[0].getDoubleMap().isEmpty()
                        && o[0].getaWithB() != null && o[0].getaWithB().getB() != null
                        && o[0].getStrings() != null && !o[0].getStrings().isEmpty());
        check(creator.mock(new SimpleObject[2]),
                o -> o != null && o.length == 2 && o[0].getAnEnum() != null && o[0].getSi() != 0
                        && o[0].getsSa().length > 0 && o[0].getsSa()[0] != 0
                        && o[0].getDoubleMap() != null && !o[0].getDoubleMap().isEmpty()
                        && o[0].getaWithB() != null && o[0].getaWithB().getB() != null
                        && o[0].getStrings() != null && !o[0].getStrings().isEmpty());
    }

    /**
     * 复杂对象测试
     */
    @Test
    public void complexObjectTest() {
        creator.getConfig().arraySize(1).creatingDepth(1);
        check(creator.mock(ComplexObject.class), o -> o != null
                && o.getMapExtend() != null && !o.getMapExtend().isEmpty()
                && o.getMyArrayList() != null && !o.getMyArrayList().isEmpty()
                && o.getListSetMap() != null && !o.getListSetMap().isEmpty()
                && o.getArrayListHashMap() != null && !o.getArrayListHashMap().isEmpty()
                && o.getMyListMyMap() != null && !o.getMyListMyMap().isEmpty());
        check(creator.mock(new ComplexObject()), o -> o != null
                && o.getMapExtend() != null && !o.getMapExtend().isEmpty()
                && o.getMyArrayList() != null && !o.getMyArrayList().isEmpty()
                && o.getListSetMap() != null && !o.getListSetMap().isEmpty()
                && o.getArrayListHashMap() != null && !o.getArrayListHashMap().isEmpty()
                && o.getMyListMyMap() != null && !o.getMyListMyMap().isEmpty());
        creator.getConfig().arraySize(2).creatingDepth(2);
    }

    /**
     * List测试
     */
    @Test
    public void listTest() {
        check(creator.mock(List.class), Objects::nonNull);
        check(creator.mock(MyArrayList.class), o -> o != null && !o.isEmpty() && o.get(0) != null);
        check(creator.mock(IList.class), o -> o != null
                && o.getListList() != null && !o.getListList().isEmpty() && !o.getListList().get(0).isEmpty()
                && o.getMapList() != null && !o.getMapList().isEmpty() && !o.getMapList().get(0).isEmpty()
                && o.getMyArrayList() != null && !o.getMyArrayList().isEmpty()
                && o.getDoubles() != null && !o.getDoubles().isEmpty() && o.getDoubles().get(0) != 0);
        check(creator.mock(new IList()), o -> o != null
                && o.getListList() != null && !o.getListList().isEmpty() && !o.getListList().get(0).isEmpty()
                && o.getMapList() != null && !o.getMapList().isEmpty() && !o.getMapList().get(0).isEmpty()
                && o.getMyArrayList() != null && !o.getMyArrayList().isEmpty()
                && o.getDoubles() != null && !o.getDoubles().isEmpty() && o.getDoubles().get(0) != 0);
        check(creator.mock(IListExtend.class), o -> o != null
                && o.getListList() != null && !o.getListList().isEmpty() && !o.getListList().get(0).isEmpty()
                && o.getMapList() != null && !o.getMapList().isEmpty() && !o.getMapList().get(0).isEmpty()
                && o.getMyArrayList() != null && !o.getMyArrayList().isEmpty()
                && o.getDoubles() != null && !o.getDoubles().isEmpty() && o.getDoubles().get(0) != 0);
        check(creator.mock(new IListExtend()), o -> o != null
                && o.getListList() != null && !o.getListList().isEmpty() && !o.getListList().get(0).isEmpty()
                && o.getMapList() != null && !o.getMapList().isEmpty() && !o.getMapList().get(0).isEmpty()
                && o.getMyArrayList() != null && !o.getMyArrayList().isEmpty()
                && o.getDoubles() != null && !o.getDoubles().isEmpty() && o.getDoubles().get(0) != 0);
    }

    /**
     * List测试
     */
    @Test
    public void setTest() {
        check(creator.mock(Set.class), Objects::nonNull);
        check(creator.mock(MySet.class), Objects::nonNull);
        check(creator.mock(MyHashSet.class), o -> o != null && !o.isEmpty());
        check(creator.mock(ISet.class), o -> o != null
                && o.getSetSet() != null && !o.getSetSet().isEmpty() && !o.getSetSet().toArray(new Set<?>[0])[0].isEmpty()
                && o.getListSet() != null && !o.getListSet().isEmpty() && o.getListSet().toArray()[0] != null
                && o.getMySet() != null && !o.getMySet().isEmpty()
                && o.getMyHashSet() != null && !o.getMyHashSet().isEmpty());
        check(creator.mock(new ISet()), o -> o != null
                && o.getSetSet() != null && !o.getSetSet().isEmpty() && !o.getSetSet().toArray(new Set<?>[0])[0].isEmpty()
                && o.getListSet() != null && !o.getListSet().isEmpty() && o.getListSet().toArray()[0] != null
                && o.getMySet() != null && !o.getMySet().isEmpty()
                && o.getMyHashSet() != null && !o.getMyHashSet().isEmpty());
    }

    /**
     * map测试
     */
    @Test
    public void mapTest() {
        creator.fieldValue(new DictDataCreator<>(new String[]{
                "小明", "小红"
        }));
        // 未指明泛型，无法添加数据
        check(creator.mock(Map.class), Objects::nonNull);
        check(creator.mock(MyHashMap.class), o -> o != null && !o.isEmpty());
        check(creator.mock(IMap.class), o -> o != null
                && o.getStringMapMap() != null && !o.getStringMapMap().isEmpty()
                && o.getEnumListMap() != null && !o.getEnumListMap().isEmpty() && !o.getEnumListMap().values().isEmpty()
                && o.getMapExtend() != null && !o.getMapExtend().isEmpty()
                && o.getDoubleMyMap() != null && !o.getDoubleMyMap().isEmpty());
        check(creator.mock(new IMap()), o -> o != null
                && o.getStringMapMap() != null && !o.getStringMapMap().isEmpty()
                && o.getEnumListMap() != null && !o.getEnumListMap().isEmpty() && !o.getEnumListMap().values().isEmpty()
                && o.getMapExtend() != null && !o.getMapExtend().isEmpty()
                && o.getDoubleMyMap() != null && !o.getDoubleMyMap().isEmpty());
        // 未指明泛型，无法添加数据
        check(creator.mock(MyMap.class), Objects::nonNull);
        // 以下不被允许
        creator.getConfig()
                .filter(new ClassKeyFilter()
                        .ignoredClass(MyMap.class));
        check(creator.mock(new MyMap<String, Double>()), Objects::nonNull);
        check(creator.mock(MyMapExtend.class), o -> o != null && !o.isEmpty());
        creator.getConfig().clearClassFilter();
    }

    /**
     * 嵌套循环测试
     */
    @Test
    public void circleTest() {
        check(creator.mock(AWithB.class), o -> o != null && o.getName() != null
                && o.getB() != null && o.getB().getA() != null);
        check(creator.mock(AWithB[].class), o -> o != null && o.length > 0 && o[0].getName() != null
                && o[0].getB() != null && o[0].getB().getA() != null);
        check(creator.mock(new AWithB()), o -> o != null && o.getName() != null
                && o.getB() != null && o.getB().getA() != null);
        check(creator.mock(BWithA.class), o -> o != null && o.getName() != null
                && o.getA() != null && o.getA().getB() != null);
        check(creator.mock(BWithA[].class), o -> o != null && o.length > 0 && o[0].getName() != null
                && o[0].getA() != null && o[0].getA().getB() != null);
        check(creator.mock(new BWithA()), o -> o != null && o.getName() != null
                && o.getA() != null && o.getA().getB() != null);
        check(creator.mock(SelfC.class), o -> o != null && o.getStr() != null && o.getI() != 0 && o.getD() != 0
                && o.getSelfC() != null);
        check(creator.mock(SelfC[].class), o -> o != null && o.length > 0 && o[0].getStr() != null && o[0].getI() != 0 && o[0].getD() != 0
                && o[0].getSelfC() != null);
        check(creator.mock(new SelfC()), o -> o != null && o.getStr() != null && o.getI() != 0 && o.getD() != 0
                && o.getSelfC() != null);
    }

    /**
     * 自定义测试
     */
    @Test
    public void customTest() {
        // 固定值测试
        creator.getConfig()
                .fieldObject(IEnum.class, IEnum.HELLO);
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
                .fieldValue(AWithB.class, (src, creator) -> {
                    AWithB a = new AWithB();
                    a.setName(name);
                    return a;
                });
        printlnFormatted("DataCreator testing result", name.equals(creator.mock(AWithB.class).getName()));

        // 特选属性固定值测试
        creator.getConfig()
                .fieldObject(AWithB::getName, name);
        printlnFormatted("DataCreator testing result", name.equals(creator.mock(AWithB.class).getName()));

        // 过滤器测试
        creator.getConfig()
                // 表示仅忽略属性中的Integer类型
                .filter(new FieldKeyFilter()
                        .ignoredField(Integer.class))
                .filter(new FieldModifierFilter()
                        .allowedModifiers(Modifier.PROTECTED, Modifier.PUBLIC)
                        .blockedModifiers(Modifier.PRIVATE));
        check(creator.mock(ModifierObject.class), o -> o != null && o.getPubInt() == null && o.getPriInt() == null && o.getProDou() != null);
        printlnFormatted("Integer not ignored testing result", creator.mock(Integer.class) != null);
        creator.getConfig()
                .filter(new ClassKeyFilter()
                        .ignoredClass(Double.class));
        printlnFormatted("Double ignored testing result", creator.mock(Double.class) == null);
        creator.clearClassFilter();
        creator.clearFieldFilter();
    }

    /**
     * 配置设定测试
     */
    @Test
    public void configTest() {
        // 属性与类过滤
        creator.filter(new FieldKeyFilter()
                        .ignoredField(SimpleObject::getsS))
                .filter(new ClassKeyFilter()
                        .ignoredClass(Boolean.class));
        check(creator.mock(SimpleObject.class), o -> o != null
                && o.getSelfC() != null && o.getSelfC().getSelfC() != null
                && o.getaWithB() != null && o.getaWithB().getB() != null
                && o.getbWithA() != null && o.getbWithA().getA() != null
                && o.getsS() == null && o.getsSa() != null && o.getsSa().length > 0 && o.getsSa()[0] != 0
                && o.getsBo() == null && o.getsBoa() == null);

        // 自定义属性值
        final String testStr = "test new DataCreator";
        creator.getConfig()
                .autoCascade(false)
                .cascadeCreateKey(SimpleObject.class)
                .cascadeCreateKey(SimpleObject::getSelfC)
                .fieldObject(int.class, 5)
                .fieldObject(SimpleObject::getSc, '=')
                .fieldValue(SimpleObject::getStrings, (DataCreator<List<String>>) (src, creator) -> {
                    List<String> list = new ArrayList<>();
                    list.add(testStr);
                    return list;
                });
        check(creator.mock(SimpleObject.class), o -> o != null
                && o.getSelfC() != null && o.getSelfC().getSelfC() != null
                && o.getaWithB() != null && o.getaWithB().getB() == null
                && o.getbWithA() != null && o.getbWithA().getA() == null
                && o.getsS() == null && o.getsSa() != null && o.getsSa().length > 0 && o.getsSa()[0] != 0
                && o.getsBo() == null && o.getsBoa() == null
                && o.getSi() == 5 && o.getSc() == '='
                && o.getStrings() != null && o.getStrings().get(0).equals(testStr));
        creator.getConfig()
                .autoCascade(true);
        creator.getConfig().clearFieldFilter();
        creator.getConfig().clearClassFilter();
    }

    /**
     * 属性数据池测试
     */
    @Test
    public void dataPoolTest() {
        FieldDataPool dataPool = new FieldDataPool()
                // 自动识别同类型属性，包括int类型的所有名称中包含age的属性，忽略大小写，例如age、nominalAge
                .like(Person::getAge, 23, 24, 25, 26, 27)
                .next()
                // 添加FRUIT类的数据池，则会对所有的FRUIT类进行数据池选取，忽略名称
                .type(Person.FRUIT.class)
                .values(Person.FRUIT.APPLE)
                .next()
                // 对Date类的所有名称中能匹配`.*day`和`.*time`的属性进行数据池选取
                .type(Date.class)
                .values(new Date[]{new Date()}, ".*day", ".*time").next();
        creator.dataPool(dataPool);
        check(creator.mock(Person.class), o -> o != null
                && o.getAge() > 22 && o.getAge() < 28 && o.getNominalAge() > 22 && o.getNominalAge() < 28
                && o.getFavouriteFruit() == Person.FRUIT.APPLE);
        Person.FRUIT fruit = null;
        for (int i = 0; i < 20; i++) {
            fruit = creator.mock(Person.FRUIT.class);
            if (fruit != Person.FRUIT.APPLE) {
                break;
            }
        }
        check(fruit, o -> o == Person.FRUIT.APPLE);
    }

    @Test
    public void instanceCreatorTest() {
        creator.getConfig().instanceCreator(new InstanceCreator<IpAddr>() {
            @Override
            public Class<?> matched() {
                return IpAddr.class;
            }

            @Override
            public IpAddr newInstance(MockDataCreator creator) {
                return new IpAddr(creator.mock(String.class));
            }
        });
        check(creator.mock(IpAddr.class), ipAddr -> ipAddr.getIp() != null);
    }

    /**
     * 泛型测试
     */
    @Test
    public void genericsTest() {
        check(creator.mock(GenericsBody.class), o -> o != null && o.getT() != null
                && o.getList() != null && !o.getList().isEmpty()
                && o.getMap() != null && !o.getMap().isEmpty());
        check(creator.mock(GenericsExt.class), o -> o != null && o.getT() != null
                && o.getList() != null && !o.getList().isEmpty() && o.getList().get(0) != null
                && o.getMap() != null && !o.getMap().isEmpty());
    }

    @Test
    public void DataTranspilerTest() {
        MockDataConfig config = creator.getConfig().copy();
        FieldDataPool dataPool = new FieldDataPool();
        dataPool.likeName(".*Fruit", Person.FRUIT.APPLE.name());
        config.dataPool(dataPool);
        check(creator.mock(Person.class, config), person -> person.getFavouriteFruit() == null
                && Person.FRUIT.APPLE.name().equals(person.getUnlikeFruit()));
        ObjectTranspiler<String> transpiler = new ObjectTranspiler<String>() {
            @Override
            public Object trans(String s) {
                return Person.FRUIT.valueOf(s.toUpperCase());
            }
            @Override
            public Class<?>[] targets() {
                return new Class[]{Person.FRUIT.class};
            }
        };
        config.addTranspiler(transpiler);
        check(creator.mock(Person.class, config), person -> person.getFavouriteFruit() == Person.FRUIT.APPLE
                && Person.FRUIT.APPLE.name().equals(person.getUnlikeFruit()));
    }

    private <T> void check(T t, Predicate<T> predicate) {
        total++;
        boolean test = predicate.test(t);
        if (test) {
            trueCount++;
        } else {
            falseCount++;
        }
        if (t == null) {
            printlnFormatted("null", null);
        } else {
            String claName = t.getClass().getName();
            printlnFormatted(claName, test + "\t---\t" + JSONObject.toJSONString(t));
        }
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
        if (o instanceof String) {
            System.out.println(builder + "\t--->\t" + o);
        } else {
            System.out.println(builder + "\t--->\t" + JSONObject.toJSONString(o));
        }
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
        System.out.println("---------------------- 总测试次数 " + total + " , 失败次数 " + falseCount + " , 成功率为 " + trueCount + " / " + total);
        System.out.println("---------------------- 累计耗时 >> " + stopwatch.getWholeInterval(TimeUnit.MILLISECONDS) + "  毫秒");
    }
}
