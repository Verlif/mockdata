package idea.verlif.mock.data.virtualpool.template;


import idea.verlif.mock.data.random.EnglishFirstNameRandom;
import idea.verlif.mock.data.random.EnglishSecondNameRandom;
import idea.verlif.mock.data.virtualpool.SimplePool;
import idea.verlif.reflection.domain.ClassGrc;

/**
 * 英文姓氏字符串池
 */
public class EnglishNameStringPool implements SimplePool {

    private final EnglishFirstNameRandom firstNameRandom;
    private final EnglishSecondNameRandom secondNameRandom;

    public EnglishNameStringPool() {
        this.firstNameRandom = EnglishFirstNameRandom.getInstance();
        this.secondNameRandom = EnglishSecondNameRandom.getInstance();
    }

    public String mock() {
        return secondNameRandom.next() + " " + firstNameRandom.next();
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
