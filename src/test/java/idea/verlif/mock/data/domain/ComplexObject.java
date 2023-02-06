package idea.verlif.mock.data.domain;

import java.util.*;

public class ComplexObject {

    private MyMap<IEnum,  MyList<AWithB>> myListMyMap;

    private MyArrayList myArrayList;

    private Map<List<int[]>, Set<SelfC>> listSetMap;

    private HashMap<IEnum, ArrayList<String>> arrayListHashMap;

    private MyMapExtend mapExtend;

    public MyMap<IEnum, MyList<AWithB>> getMyListMyMap() {
        return myListMyMap;
    }

    public void setMyListMyMap(MyMap<IEnum, MyList<AWithB>> myListMyMap) {
        this.myListMyMap = myListMyMap;
    }

    public MyArrayList getMyArrayList() {
        return myArrayList;
    }

    public void setMyArrayList(MyArrayList myArrayList) {
        this.myArrayList = myArrayList;
    }

    public Map<List<int[]>, Set<SelfC>> getListSetMap() {
        return listSetMap;
    }

    public void setListSetMap(Map<List<int[]>, Set<SelfC>> listSetMap) {
        this.listSetMap = listSetMap;
    }

    public HashMap<IEnum, ArrayList<String>> getArrayListHashMap() {
        return arrayListHashMap;
    }

    public void setArrayListHashMap(HashMap<IEnum, ArrayList<String>> arrayListHashMap) {
        this.arrayListHashMap = arrayListHashMap;
    }

    public MyMapExtend getMapExtend() {
        return mapExtend;
    }

    public void setMapExtend(MyMapExtend mapExtend) {
        this.mapExtend = mapExtend;
    }

}
