package idea.verlif.mock.data.domain;

public class ComplexObject {

    private MyMap<IEnum,  MyList<AWithB>> myListMyMap;

    private MyArrayList myArrayList;

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

    public MyMapExtend getMapExtend() {
        return mapExtend;
    }

    public void setMapExtend(MyMapExtend mapExtend) {
        this.mapExtend = mapExtend;
    }

}
