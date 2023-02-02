package idea.verlif.mock.data.domain;

import java.util.List;

/**
 * @author Verlif
 */
public class IList {

    private List<Double> doubles;

    private List<List<String>> listList;

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

    public MyArrayList getMyArrayList() {
        return myArrayList;
    }

    public void setMyArrayList(MyArrayList myArrayList) {
        this.myArrayList = myArrayList;
    }
}
