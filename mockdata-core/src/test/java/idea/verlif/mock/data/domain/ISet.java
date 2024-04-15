package idea.verlif.mock.data.domain;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Verlif
 */
public class ISet {

    private Set<Set<Integer>> setSet;

    private Set<List<Map<IEnum, String>>> listSet;

    private MySet<String> mySet;

    private MyHashSet myHashSet;

    public Set<Set<Integer>> getSetSet() {
        return setSet;
    }

    public void setSetSet(Set<Set<Integer>> setSet) {
        this.setSet = setSet;
    }

    public Set<List<Map<IEnum, String>>> getListSet() {
        return listSet;
    }

    public void setListSet(Set<List<Map<IEnum, String>>> listSet) {
        this.listSet = listSet;
    }

    public MySet<String> getMySet() {
        return mySet;
    }

    public void setMySet(MySet<String> mySet) {
        this.mySet = mySet;
    }

    public MyHashSet getMyHashSet() {
        return myHashSet;
    }

    public void setMyHashSet(MyHashSet myHashSet) {
        this.myHashSet = myHashSet;
    }
}
