package idea.verlif.mock.data.virtualpool.template;

import idea.verlif.mock.data.random.ChineseFirstNameRandom;
import idea.verlif.mock.data.random.ChineseSecondNameRandom;
import idea.verlif.mock.data.virtualpool.SimplePool;
import idea.verlif.reflection.domain.ClassGrc;

/**
 * 中文姓氏字符串池
 */
public class ChineseNameStringPool implements SimplePool {

    private final ChineseFirstNameRandom firstNameRandom;
    private final ChineseSecondNameRandom secondNameRandom;

    public ChineseNameStringPool() {
        this.firstNameRandom = ChineseFirstNameRandom.getInstance();
        this.secondNameRandom = ChineseSecondNameRandom.getInstance();
    }

    public String mock() {
        return firstNameRandom.next() + secondNameRandom.next();
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
