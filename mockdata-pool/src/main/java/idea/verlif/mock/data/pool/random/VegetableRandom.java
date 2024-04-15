package idea.verlif.mock.data.pool.random;

import idea.verlif.mock.data.pool.DataRandom;
import idea.verlif.mock.data.pool.util.RandomUtil;

/**
 * 蔬菜名称随机器
 */
public class VegetableRandom implements DataRandom<String> {

    private static final VegetableRandom RANDOM;
    static {
        RANDOM = new VegetableRandom();
    }

    public static VegetableRandom getInstance() {
        return RANDOM;
    }

    private static final String[] VEGETABLES = new String[]{
            "白菜", "菠菜", "芹菜", "青菜", "西兰花", "花菜", "卷心菜", "生菜", "空心菜", "油麦菜",
            "苋菜", "芥菜", "豆芽", "豆角", "茄子", "黄瓜", "西红柿", "南瓜", "冬瓜", "丝瓜", "苦瓜",
            "青椒", "红椒", "土豆", "红薯", "山药", "芋头", "莲藕", "胡萝卜", "白萝卜", "洋葱",
            "大葱", "蒜头", "蒜苗", "韭菜", "芦笋", "莴苣", "西兰花芽", "竹笋", "香菇", "金针菇"
    };

    @Override
    public String next() {
        return RandomUtil.next(VEGETABLES);
    }
}
