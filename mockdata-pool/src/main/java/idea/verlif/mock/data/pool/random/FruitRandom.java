package idea.verlif.mock.data.pool.random;

import idea.verlif.mock.data.pool.DataRandom;
import idea.verlif.mock.data.pool.util.RandomUtil;

/**
 * 水果名称随机器
 */
public class FruitRandom implements DataRandom<String> {

    private static final FruitRandom RANDOM;
    static {
        RANDOM = new FruitRandom();
    }

    public static FruitRandom getInstance() {
        return RANDOM;
    }

    public static final String[] FRUITS = new String[]{
            "苹果", "香蕉", "橙子", "葡萄", "草莓", "樱桃", "桃子", "菠萝", "柠檬", "西瓜",
            "芒果", "火龙果", "李子", "石榴", "哈密瓜", "木瓜", "柚子", "椰子", "橄榄", "蓝莓",
            "黑莓", "覆盆子", "杨桃", "杏子", "柿子", "桑葚", "金桔", "青梅", "山楂", "释迦果"
    };

    @Override
    public String next() {
        return RandomUtil.next(FRUITS);
    }
}
