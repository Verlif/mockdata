package idea.verlif.mock.data;

import idea.verlif.mock.data.annotation.MockData;
import idea.verlif.mock.data.domain.Person;
import idea.verlif.mock.data.util.ReflectUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import stopwatch.Stopwatch;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

/**
 * @author Verlif
 */
public class FieldTest {

    @Test
    public void annotation() {
        Stopwatch stopwatch = Stopwatch.get("this");
        stopwatch.pin();
        Field field1 = ReflectUtil.getFieldFromLambda(Person::getName);
        Field field2 = ReflectUtil.getFieldFromLambda(Person::getName);
        stopwatch.pin();
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
