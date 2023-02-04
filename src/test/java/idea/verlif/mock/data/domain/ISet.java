package idea.verlif.mock.data.domain;

import java.util.Set;

/**
 * @author Verlif
 */
public class ISet {

    private Set<Set<Integer>> setSet;

    private MySet<String> mySet;

    private MyHashSet myHashSet;

    public Set<Set<Integer>> getSetSet() {
        return setSet;
    }

    public void setSetSet(Set<Set<Integer>> setSet) {
        this.setSet = setSet;
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
