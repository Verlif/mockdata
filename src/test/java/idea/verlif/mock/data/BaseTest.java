package idea.verlif.mock.data;

import idea.verlif.mock.data.config.MockDataConfig;
import idea.verlif.mock.data.creator.data.ByteRandomCreator;
import idea.verlif.mock.data.creator.data.IntegerRandomCreator;
import idea.verlif.mock.data.creator.data.ListCreator;
import idea.verlif.mock.data.creator.data.LongRandomCreator;
import idea.verlif.mock.data.domain.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import stopwatch.Stopwatch;

import java.util.Arrays;
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
    public void mockTest() throws IllegalAccessException, InstantiationException {
        MockDataCreator creator = new MockDataCreator();
        creator.addDefaultCreator(new IntegerRandomCreator());
        creator.addDefaultCreator(new LongRandomCreator());
        creator.addDefaultCreator(new ListCreator());
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
        creator.addDefaultCreator(new ByteRandomCreator());
        MockDataConfig config = creator.getConfig();
        int min = 0, max = 0;
        for (int i = 0; i < 100; i++) {
            byte b = creator.mock(byte.class);
            System.out.println(b);
            if (b < min) {
                min = b;
            }
            if (b > max) {
                max = b;
            }
        }
        System.out.println("min - " + min);
        System.out.println("max - " + max);
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
