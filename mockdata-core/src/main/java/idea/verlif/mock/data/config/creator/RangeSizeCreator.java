package idea.verlif.mock.data.config.creator;

import idea.verlif.mock.data.config.SizeCreator;
import idea.verlif.mock.data.util.RandomUtil;

public class RangeSizeCreator implements SizeCreator {

    private final int min;
    private final int max;

    public RangeSizeCreator(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public int getSize(Class<?> cla) {
        return RandomUtil.range(min, max);
    }
}
