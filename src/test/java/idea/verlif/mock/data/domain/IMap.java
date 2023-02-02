package idea.verlif.mock.data.domain;

import java.util.Map;

public class IMap {

    private MyMap<String, Double> doubleMyMap;

    private MyMapExtend mapExtend;

    private Map<String, Map<Integer, AWithB>> stringMapMap;

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

    public Map<String, Map<Integer, AWithB>> getStringMapMap() {
        return stringMapMap;
    }

    public void setStringMapMap(Map<String, Map<Integer, AWithB>> stringMapMap) {
        this.stringMapMap = stringMapMap;
    }
}
