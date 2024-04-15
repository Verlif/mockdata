package idea.verlif.mock.data.pool.template;

import idea.verlif.mock.data.exception.MockDataException;
import idea.verlif.mock.data.pool.SimplePool;
import idea.verlif.mock.data.pool.util.RandomUtil;
import idea.verlif.reflection.domain.ClassGrc;

/**
 * 带期望的数字范围池，一般可做年龄、年份等存在正太分布的整数中。
 */
public class LimitIntPool implements SimplePool {

    /**
     * 最小值
     */
    private final int min;
    /**
     * 最大值
     */
    private final int max;
    /**
     * 期望最小值
     */
    private final int preferredMin;
    /**
     * 期望最大值
     */
    private final int preferredMax;

    /**
     * 回归偏差值，偏向值越高，在倾向范围的概率越大。
     */
    private double offset;

    public LimitIntPool(int preferredMin, int preferredMax) {
        this(preferredMin, preferredMax, preferredMin, preferredMax);
    }

    public LimitIntPool(int min, int max, int preferredMin, int preferredMax) {
        this.min = min;
        this.max = max;
        this.preferredMin = preferredMin;
        this.preferredMax = preferredMax;
        if (max < min) {
            throw new MockDataException("Max must be larger than Min!");
        }
        if (preferredMin > preferredMax) {
            throw new MockDataException("PreferredMin must be letter than PreferredMax!");
        }
        if (preferredMin < min || preferredMax > max) {
            throw new MockDataException("Wrong limit!");
        }
        setLevel(1);
    }

    public void setLevel(int level) {
        if (level < 1) {
            level = 1;
        }
        this.offset = level / (level + 9d);
    }

    public int mock() {
        int pMin = min;
        int pMax = max;
        int num;
        for (int i = 0; i < 3; i++) {
            num = RandomUtil.range(pMin, pMax);
            if ((num >= preferredMin && num <= preferredMax)) {
                return num;
            }
            pMin += (int) ((preferredMin - pMin) * offset);
            pMax += (int) ((preferredMax - pMax) * offset);
        }
        return RandomUtil.range(min, max);
    }

    @Override
    public Object fetch(ClassGrc classGrc, String key) {
        return mock();
    }

}
