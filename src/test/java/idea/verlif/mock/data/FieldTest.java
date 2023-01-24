package idea.verlif.mock.data;

import idea.verlif.mock.data.config.MockDataConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import stopwatch.Stopwatch;

import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;

/**
 * @author Verlif
 */
public class FieldTest {

    @Test
    public void annotation() {
        MockDataConfig config = new MockDataConfig();
        System.out.println("预期 - true - " + config.isAllowedModifier(Modifier.PRIVATE));
        System.out.println("预期 - false - " + config.isAllowedModifier(Modifier.PUBLIC));
        config.setAllowPublic(true);
        System.out.println("预期 - true - " + config.isAllowedModifier(Modifier.PRIVATE));
        System.out.println("预期 - true - " + config.isAllowedModifier(Modifier.PUBLIC));
        config.setAllowPrivate(false);
        System.out.println("预期 - false - " + config.isAllowedModifier(Modifier.PRIVATE));
        System.out.println("预期 - true - " + config.isAllowedModifier(Modifier.PUBLIC));
        config.setAllowPublic(false);
        System.out.println("预期 - false - " + config.isAllowedModifier(Modifier.PRIVATE));
        System.out.println("预期 - false - " + config.isAllowedModifier(Modifier.PUBLIC));
    }

    @Test
    public void classNameTest() {
        System.out.println(int[].class.getName());
        System.out.println(short[].class.getName());
        System.out.println(long[].class.getName());
        System.out.println(byte[].class.getName());
        System.out.println(boolean[].class.getName());
        System.out.println(char[].class.getName());
        System.out.println(float[].class.getName());
        System.out.println(long[].class.getName());
        System.out.println(Long[].class.getName());
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
