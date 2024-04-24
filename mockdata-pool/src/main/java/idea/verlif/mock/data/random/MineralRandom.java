package idea.verlif.mock.data.random;

import idea.verlif.mock.data.util.RandomUtil;

/**
 * 常见非金属矿石随机器
 */
public class MineralRandom implements DataRandom<String> {

    private static final MineralRandom RANDOM;
    static {
        RANDOM = new MineralRandom();
    }

    public static MineralRandom getInstance() {
        return RANDOM;
    }

    public static final String[] MINERALS = new String[]{
            "金刚石", "石墨", "水晶", "刚玉", "石棉",
            "云母", "石膏", "萤石", "玛瑙", "石灰岩",
            "白云岩", "石英岩", "陶瓷土", "耐火粘土", "大理岩",
            "花岗岩", "盐", "磷", "蓝晶石", "夕线石",
            "红柱石", "硅灰石", "滑石", "蓝石棉", "长石",
            "石榴子石", "叶蜡石", "透辉石", "透闪石", "蛭石",
            "沸石", "明矾石", "芒硝", "重晶石", "毒重石",
            "天然碱", "方解石", "冰洲石", "菱镁", "宝石",
            "玉石", "白垩", "砂岩", "天然石英砂", "脉石英",
            "硅藻土", "页岩", "高岭土", "凹凸棒石", "海泡石",
            "膨润土", "硼", "碘", "溴", "砷"
    };

    @Override
    public String next() {
        return RandomUtil.next(MINERALS);
    }
}
