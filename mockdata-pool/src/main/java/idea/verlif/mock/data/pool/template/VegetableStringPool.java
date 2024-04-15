package idea.verlif.mock.data.pool.template;

import idea.verlif.mock.data.pool.SimplePool;
import idea.verlif.mock.data.pool.random.VegetableRandom;
import idea.verlif.reflection.domain.ClassGrc;

/**
 * 蔬菜名称字符串池
 */
public class VegetableStringPool implements SimplePool {

    private final VegetableRandom vegetableRandom;

    public VegetableStringPool() {
        this.vegetableRandom = VegetableRandom.getInstance();
    }

    @Override
    public Object fetch(ClassGrc classGrc, String key) {
        return vegetableRandom.next();
    }
}
