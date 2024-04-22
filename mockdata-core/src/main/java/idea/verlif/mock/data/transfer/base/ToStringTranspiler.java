package idea.verlif.mock.data.transfer.base;

import idea.verlif.mock.data.transfer.TypeTranspiler;

public class ToStringTranspiler implements TypeTranspiler<String> {
    @Override
    public String trans(Object o) {
        return o.toString();
    }

    @Override
    public boolean support(Class<?> cla) {
        return true;
    }

}
