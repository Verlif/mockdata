package idea.verlif.mock.data.pool.template;

import idea.verlif.mock.data.pool.SimplePool;
import idea.verlif.mock.data.pool.random.FruitRandom;
import idea.verlif.reflection.domain.ClassGrc;

/**
 * 水果名称字符串池
 */
public class FruitStringPool implements SimplePool {

    private final FruitRandom fruitRandom;

    public FruitStringPool() {
        this.fruitRandom = FruitRandom.getInstance();
    }

    @Override
    public Object fetch(ClassGrc classGrc, String key) {
        return fruitRandom.next();
    }
}
