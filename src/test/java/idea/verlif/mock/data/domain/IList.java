package idea.verlif.mock.data.domain;

import java.util.List;

/**
 * @author Verlif
 */
public class IList {

    private List<Double> doubles;

    private List<List<String>> listList;

    private MyList<SelfC> myList;

    private MyArrayList myArrayList;

    public List<Double> getDoubles() {
        return doubles;
    }

    public void setDoubles(List<Double> doubles) {
        this.doubles = doubles;
    }

    public List<List<String>> getListList() {
        return listList;
    }

    public void setListList(List<List<String>> listList) {
        this.listList = listList;
    }

    public MyList<SelfC> getMyList() {
        return myList;
    }

    public void setMyList(MyList<SelfC> myList) {
        this.myList = myList;
    }

    public MyArrayList getMyArrayList() {
        return myArrayList;
    }

    public void setMyArrayList(MyArrayList myArrayList) {
        this.myArrayList = myArrayList;
    }
}
