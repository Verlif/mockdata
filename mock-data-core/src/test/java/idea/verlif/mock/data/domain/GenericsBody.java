package idea.verlif.mock.data.domain;

import java.util.List;
import java.util.Map;

public class GenericsBody<T, V> {

    private T t;

    private V v;

    private List<T> list;

    private Map<T, List<V>> map;

    public T getT() {
        return t;
    }

    public V getV() {
        return v;
    }

    public void setV(V v) {
        this.v = v;
    }

    public void setT(T t) {
        this.t = t;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Map<T, List<V>> getMap() {
        return map;
    }

    public void setMap(Map<T, List<V>> map) {
        this.map = map;
    }
}
