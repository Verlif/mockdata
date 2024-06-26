package idea.verlif.mock.data.virtualpool.template;

import idea.verlif.mock.data.random.FlowerRandom;
import idea.verlif.mock.data.random.FruitRandom;
import idea.verlif.mock.data.random.MineralRandom;
import idea.verlif.mock.data.random.VegetableRandom;
import idea.verlif.mock.data.util.RandomUtil;
import idea.verlif.mock.data.virtualpool.SimplePool;
import idea.verlif.reflection.domain.ClassGrc;

/**
 * 虚拟地址字符串池
 */
public class AddressStringPool implements SimplePool {

    private final FruitRandom fruitRandom;
    private final FlowerRandom flowerRandom;
    private final VegetableRandom vegetableRandom;
    private final MineralRandom mineralRandom;

    public AddressStringPool() {
        this.fruitRandom = FruitRandom.getInstance();
        this.flowerRandom = FlowerRandom.getInstance();
        this.vegetableRandom = VegetableRandom.getInstance();
        this.mineralRandom = MineralRandom.getInstance();
    }

    public String mock() {
        return (fruitRandom.next() + "省"
                + flowerRandom.next() + "市"
                + vegetableRandom.next() + "区"
                + mineralRandom.next() + (RandomUtil.nextBoolean() ? mineralRandom.next() : "") + "街道");
    }

    @Override
    public Object fetch(ClassGrc classGrc, String key) {
        return mock();
    }

    @Override
    public Class<?> type() {
        return String.class;
    }
}
