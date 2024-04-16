package idea.verlif.mock.data.pool.template;

import idea.verlif.mock.data.pool.SimplePool;
import idea.verlif.reflection.domain.ClassGrc;

import java.util.UUID;

/**
 * UUID字符串池
 */
public class UUIDStringPool implements SimplePool {

    private final boolean withEnDash;

    public UUIDStringPool() {
        this(true);
    }

    public UUIDStringPool(boolean withEnDash) {
        this.withEnDash = withEnDash;
    }

    @Override
    public Object fetch(ClassGrc classGrc, String key) {
        String uuid = UUID.randomUUID().toString();
        if (withEnDash) {
            return uuid;
        } else {
            return uuid.replace("-", "");
        }
    }
}
