package idea.verlif.mock.data.domain;

import java.util.List;

/**
 * @author Verlif
 */
public class IList<T> {

    private List<Person> list;

    public List<Person> getList() {
        return list;
    }

    public void setList(List<Person> list) {
        this.list = list;
    }
}
