package idea.verlif.mock.data.domain;

import java.util.List;

/**
 * @author Verlif
 */
public class IList<T> {

    private List<IList<T>> list;

    public List<IList<T>> getList() {
        return list;
    }

    public void setList(List<IList<T>> list) {
        this.list = list;
    }
}
