package idea.verlif.mock.data.random;

import idea.verlif.mock.data.util.RandomUtil;

/**
 * 常见花名随机器
 */
public class FlowerRandom implements DataRandom<String> {

    private static final FlowerRandom RANDOM;
    static {
        RANDOM = new FlowerRandom();
    }

    public static FlowerRandom getInstance() {
        return RANDOM;
    }

    public static final String[] FLOWERS = new String[]{
            "玫瑰", "牡丹", "菊", "兰", "月季",
            "杜鹃", "荷", "水仙", "桂", "茶",
            "茉莉", "梅", "樱", "海棠", "康乃馨",
            "百合", "郁金香", "向日葵", "凌霄", "紫罗兰",
            "风信子", "紫藤", "三色堇", "栀子", "喇叭",
            "石斛兰", "文心兰", "剑兰", "仙客来", "非洲菊",
            "马蹄莲", "鹤望兰", "水仙百合", "朱顶红", "波斯菊",
            "虞美人", "鸡冠", "一串红", "千日红", "彼岸",
            "八仙", "金盏", "含笑", "木棉", "白兰",
            "凤仙", "桔梗", "牵牛", "铃兰", "菖蒲",
            "翠菊", "石蒜", "芍药", "水仙", "满天星"
    };

    @Override
    public String next() {
        return RandomUtil.next(FLOWERS);
    }
}
