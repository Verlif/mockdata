package idea.verlif.mock.data.pool.random;

import idea.verlif.mock.data.pool.DataRandom;
import idea.verlif.mock.data.pool.util.RandomUtil;

/**
 * 中文常见名随机器
 */
public class ChineseSecondNameRandom implements DataRandom<String> {

    private static final ChineseSecondNameRandom RANDOM;
    static {
        RANDOM = new ChineseSecondNameRandom();
    }

    public static ChineseSecondNameRandom getInstance() {
        return RANDOM;
    }

    private static final String[] LAST = new String[]{
            "明轩", "睿诚", "昊天", "文博", "俊杰",
            "欣怡", "雅婷", "思涵", "雨婷", "晓萱",
            "宇航", "浩然", "天宇", "泽洋", "煜城",
            "诗琪", "雨薇", "静琪", "语嫣", "芷若",
            "博文", "致远", "智渊", "立轩", "弘文",
            "欣妍", "婉婷", "雪芳", "可馨", "婧琪",
            "伟宸", "烨伟", "苑博", "伟泽", "熠彤",
            "梦洁", "凌薇", "美萱", "曼玉", "美琳",
            "鑫磊", "晟睿", "天佑", "文昊", "修洁",
            "惠茜", "漫妮", "香茹", "月婵", "嫦曦",
            "乐天", "鹏涛", "鸿煊", "煜祺", "峻熙",
            "靖瑶", "瑾萱", "佑怡", "婳祎", "檀雅",
            "子轩", "弘图", "睿思", "鸿博", "嘉懿",
            "晟楠", "泓潇", "子骞", "博超", "君昊",
            "若瑄", "熙雯", "诗茵", "静璇", "婕珍",
            "欣彤", "鑫蕾", "绍辉", "泽楷", "昊强"
    };

    @Override
    public String next() {
        String next = RandomUtil.next(LAST);
        if (RandomUtil.bool()) {
            return next.substring(1);
        } else {
            return next;
        }
    }

}
