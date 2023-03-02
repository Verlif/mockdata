package idea.verlif.mock.data.domain;

import idea.verlif.reflection.domain.ClassGrc;

public class MockSrc {

    /**
     * 目标类型信息
     */
    private final ClassGrc classGrc;

    /**
     * 构建的源对象，可能为空
     */
    private final Object oldObj;

    public MockSrc(ClassGrc classGrc, Object oldObj) {
        this.classGrc = classGrc;
        this.oldObj = oldObj;
    }

    public ClassGrc getClassGrc() {
        return classGrc;
    }

    public Object getOldObj() {
        return oldObj;
    }
}
