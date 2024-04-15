package idea.verlif.mock.data.pool;

import idea.verlif.reflection.domain.ClassGrc;

public interface SimplePool {

    Object fetch(ClassGrc classGrc, String key);
}
