package idea.verlif.mock.data.domain;

public class IMap {

    private MyMap<String, Double> doubleMyMap;

    private MyMapExtend mapExtend;

    public MyMap<String, Double> getDoubleMyMap() {
        return doubleMyMap;
    }

    public void setDoubleMyMap(MyMap<String, Double> doubleMyMap) {
        this.doubleMyMap = doubleMyMap;
    }

    public MyMapExtend getMapExtend() {
        return mapExtend;
    }

    public void setMapExtend(MyMapExtend mapExtend) {
        this.mapExtend = mapExtend;
    }
}
