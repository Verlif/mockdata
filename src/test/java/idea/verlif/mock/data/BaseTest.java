package idea.verlif.mock.data;

import com.alibaba.fastjson2.JSONObject;
import idea.verlif.mock.data.config.MockDataConfig;
import idea.verlif.mock.data.config.filter.impl.ClassKeyFilter;
import idea.verlif.mock.data.config.filter.impl.FieldKeyFilter;
import idea.verlif.mock.data.creator.DataCreator;
import idea.verlif.mock.data.creator.data.DictDataCreator;
import idea.verlif.mock.data.creator.data.DoubleRandomCreator;
import idea.verlif.mock.data.creator.data.IntegerRandomCreator;
import idea.verlif.mock.data.creator.data.LongRandomCreator;
import idea.verlif.mock.data.domain.*;
import idea.verlif.mock.data.domain.test.A;
import idea.verlif.mock.data.domain.test.Dog;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import stopwatch.Stopwatch;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Random;
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
        // 获取构造器的当前配置
        MockDataConfig config = creator.getConfig()
                // 添加需要级联构造的类
//                .cascadeCreateKey(Student.class)
//                .cascadeCreateKey(Person.class)
//                .cascadeCreateKey(Person.PersonInner.class)
//                .cascadeCreateKey(A.class)
//                .cascadeCreateKey(B.class)
//                .cascadeCreateKey(Pet.class)
//                .cascadeCreateKey(Dog.class)
                .cascadeCreatePattern(".*Student.*")
                .cascadeCreatePackage(Student.class.getPackage().getName())
//                .cascadeCreatePattern(A.class.getPackage().getName())
                // 将构造深度设置为1
                .creatingDepth(1);
        // 构造测试
        for (int i = 0; i < 10; i++) {
            System.out.println(creator.mock(Student.class));
        }

        System.out.println("------>>> 使用字典生成name属性");
        System.out.println("------>>> 限制id属性生成范围");
        System.out.println("------>>> 限制age属性生成范围");
        System.out.println("------>>> 限制score属性生成范围");
        System.out.println("------>>> 自定义secondChild属性");
        config
                // 使用字典生成name属性
                .fieldValue(Student::getName, new DictDataCreator<String>(new String[]{
                        "小明", "小红", "小王", "小赵", "小李", "小周", "小强"
                }))
                // 限制id属性生成范围
                .fieldValue(Student::getId, new LongRandomCreator(0L, 9999L))
                // 限制age属性生成范围
                .fieldValue(Student::getAge, new IntegerRandomCreator(0, 200))
                // 限制score属性生成范围
                .fieldValue(Student::getScore, new DoubleRandomCreator(0, 100D))
                // 自定义secondChild属性
                .fieldValue(Student::getSecondChild, new DataCreator<Student>() {

                    private final Random random = new Random();

                    @Override
                    public Student mock(Class<?> cla, Field field, MockDataCreator.Creator creator) {
                        if (random.nextBoolean()) {
                            return new Student("这是自定义的构造");
                        } else {
                            return null;
                        }
                    }
                })
                .fieldValue(new DataCreator<Person>() {
                    @Override
                    public Person mock(Class<?> cla, Field field, MockDataCreator.Creator creator) {
                        return new Person("Person");
                    }
                })
                .fieldValue(Dog.class, new DataCreator<Dog>() {
                    @Override
                    public Dog mock(Class<?> cla, Field field, MockDataCreator.Creator creator) {
                        return new Dog("Dog");
                    }
                })
                .fieldValue(Pet.class, new DataCreator<Pet>() {
                    @Override
                    public Pet mock(Class<?> cla, Field field, MockDataCreator.Creator creator) {
                        return new Pet("Pet");
                    }
                });
        System.out.println("------>>> 强制新建对象");
        config.forceNew(true);
//        System.out.println("------>>> 关闭private属性构建");
//        config.allowedModifiers(Modifier.PRIVATE);
//        System.out.println("------>>> 开启public属性构建");
//        config.blockedModifiers(Modifier.PUBLIC);
        Student[][] personArray = creator.mock(new Student[2][5]);
        for (int i = 0; i < 10; i++) {
            System.out.println(creator.mock(Student.class));
        }

    }

    @Test
    public void configTest() throws IllegalAccessException {
        // 创建数据构造器
        MockDataCreator creator = new MockDataCreator();
        // 获取构造器的当前配置
        MockDataConfig config = creator.getConfig()
                .fieldValue(Student::getSecondChild, new DataCreator<Student>() {
                    @Override
                    public Student mock(Class<?> cla, Field field, MockDataCreator.Creator creator) {
                        return new Student("这是自定义的构造");
                    }
                })
                // 添加需要级联构造的类
//                .cascadeCreateKey(Student.class)
//                .cascadeCreateKey(Person.class)
//                .cascadeCreateKey(Person.PersonInner.class)
//                .cascadeCreateKey(A.class)
//                .cascadeCreateKey(B.class)
//                .cascadeCreateKey(Pet.class)
//                .cascadeCreateKey(Dog.class)
                .cascadeCreatePackage(B.class.getPackage().getName())
                .filter(new ClassKeyFilter()
                        .ignoredClassRegex(".*secondChild.*")
                        .ignoredClassPackage(Person.class.getPackage().getName()))
                // 将构造深度设置为1
                .creatingDepth(1);
        System.out.println(creator.mock(Student.class));
        System.out.println(creator.mock(Student.class));
    }

    @Test
    public void mockTest() throws IllegalAccessException {
        MockDataCreator creator = new MockDataCreator();
        creator.addDefaultCreator(new LongRandomCreator(-100, 200));
        MockDataConfig config = new MockDataConfig();
        config.cascadeCreateKey(A.class, B.class);
        creator.setConfig(config);
        config.cascadeCreateKey(Person.class)
                .filter(new FieldKeyFilter()
                        .ignoredField(Person::getAList))
                .creatingDepth(2)
                .fieldValue(Person::getId, new LongRandomCreator(100, 300));
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
        creator.getConfig()
                .autoCascade(true)
                .fieldValue(Person::getId, new DataCreator<Long>() {
                    @Override
                    public Long mock(Class<?> cla, Field field, MockDataCreator.Creator creator) {
                        return 312L;
                    }
                });
        creator.addDefaultCreator(new DictDataCreator<>(new Integer[]{
                1, 2, 3, 4
        }));
//        creator.addDefaultCreator((cla, field, creator1) -> "String.String");
        Person person = creator.mock(Person.class);
        System.out.println(person);
        for (int i = 0; i < 10; i++) {
            System.out.println(creator.mock(Integer.class));
        }
    }

    @Test
    public void simpleTest() {
        Stopwatch stopwatch = Stopwatch.get("this");
        stopwatch.pin();
        MockDataCreator creator = new MockDataCreator();
        creator.getConfig()
                .arraySize(2)
                .autoCascade(true)
                .filter(new ClassKeyFilter()
                        .ignoredClass(int.class)
                        .ignoredClass(Integer.class));
        System.out.println(creator.mock(ObjectForTest.class));
        stopwatch.pin();
        System.out.println("---------------- >>> MockDataCreatorTest: " + stopwatch.getLastInterval(TimeUnit.MILLISECONDS) + "ms");
    }

    public static class ObjectForTest {

        private int[] ints;

        private int[][] intss;

        private int[][][] intsss;

        private Integer[][][] integersss;

        public int[] getInts() {
            return ints;
        }

        public void setInts(int[] ints) {
            this.ints = ints;
        }

        public int[][] getIntss() {
            return intss;
        }

        public void setIntss(int[][] intss) {
            this.intss = intss;
        }

        public int[][][] getIntsss() {
            return intsss;
        }

        public void setIntsss(int[][][] intsss) {
            this.intsss = intsss;
        }

        public Integer[][][] getIntegersss() {
            return integersss;
        }

        public void setIntegersss(Integer[][][] integersss) {
            this.integersss = integersss;
        }

        @Override
        public String toString() {
            return JSONObject.toJSONString(this);
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
