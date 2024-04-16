package idea.verlif.test;

import idea.verlif.mock.data.pool.VirtualDataPool;
import idea.verlif.mock.data.pool.random.TypeStringRandom;
import idea.verlif.mock.data.pool.template.*;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PoolTest {

    @Test
    public void simpleTest() {
    }

    @Test
    public void ageIntPoolTest() {
        int pMin = 15;
        int pMax = 25;
        int min = 0;
        int max = 100;
        int total = 100;
        LimitIntPool limitIntPool = new LimitIntPool(min, max, pMin, pMax);
        int count = 0;
        VirtualDataPool dataPool = new VirtualDataPool();
        for (int level = 2; level < 100; level++) {
            limitIntPool.setLevel(level);
            System.out.println("level -> " + level);
            for (int i = 0; i < total; i++) {
                int mock = limitIntPool.mock();
                if (mock >= pMin && mock <= pMax) {
                    count++;
                }
            }
            StringBuilder stb = new StringBuilder("\t" + divide(pMax - pMin, max - min) + " -> ");
            double realRate = divide(count, total);
            for (int i = (int) (realRate * 50); i > 0; i--) {
                stb.append(" ");
            }
            stb.append(realRate);
            System.out.println(stb);
            count = 0;
        }
    }

    private double divide(int sub, int sup) {
        return BigDecimal.valueOf(sub).divide(BigDecimal.valueOf(sup), 2, RoundingMode.HALF_UP).doubleValue();
    }

    @Test
    public void nameStringTest() {
        ChineseNameStringPool chineseNameStringPool = new ChineseNameStringPool();
        for (int i = 0; i < 10; i++) {
            System.out.println(chineseNameStringPool.mock());
        }
    }

    @Test
    public void ipStringTest() {
        IpStringPool ipStringPool = new IpStringPool();
        System.out.println("IPv4");
        for (int i = 0; i < 10; i++) {
            System.out.println(ipStringPool.mock());
        }
        System.out.println("IPv6");
        ipStringPool.setType(IpStringPool.TYPE.IPV6);
        for (int i = 0; i < 10; i++) {
            System.out.println(ipStringPool.mock());
        }
    }

    @Test
    public void addressStringPoolTest() {
        AddressStringPool addressStringPool = new AddressStringPool();
        for (int i = 0; i < 100; i++) {
            System.out.println(addressStringPool.mock());
        }
    }

    @Test
    public void emailStringPoolTest() {
        EmailStringPool emailStringPool = new EmailStringPool();
        for (int i = 0; i < 100; i++) {
            System.out.println(emailStringPool.mock());
        }
    }

    @Test
    public void IdStringPoolTest() {
        IdNumberStringPool idNumberStringPool = new IdNumberStringPool();
        for (int i = 0; i < 10; i++) {
            System.out.println(idNumberStringPool.mock());
        }
    }

    @Test
    public void phoneNumStringPoolTest() {
        PhoneNumStringPool phoneNumStringPool = new PhoneNumStringPool();
        for (int i = 0; i < 10; i++) {
            System.out.println(phoneNumStringPool.mock());
        }
    }

    @Test
    public void continuousIntPoolTest() {
        ContinuousIntPool continuousIntPool = new ContinuousIntPool(2, 2);
        for (int i = 0; i < 10; i++) {
            System.out.println(continuousIntPool.mock());
        }
    }

    @Test
    public void limitedStringPoolTest() {
        LimitedStringPool limitedStringPool = new LimitedStringPool(8, 9, TypeStringRandom.CharType.LETTER, TypeStringRandom.CharType.NUMBER);
        for (int i = 0; i < 10; i++) {
            System.out.println(limitedStringPool.next());
        }
    }
}
