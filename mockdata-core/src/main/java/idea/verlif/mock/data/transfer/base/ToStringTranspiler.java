package idea.verlif.mock.data.transfer.base;

import idea.verlif.mock.data.transfer.ObjectTranspiler;

public class ToStringTranspiler implements ObjectTranspiler<Object> {
    @Override
    public Object trans(Object o) {
        return o.toString();
    }

    @Override
    public Class<?>[] targets() {
        return new Class[]{String.class};
    }
}
