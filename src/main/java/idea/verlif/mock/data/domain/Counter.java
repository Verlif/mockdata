package idea.verlif.mock.data.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * 计数器
 *
 * @author Verlif
 */
public abstract class Counter<T> {

    protected final Map<T, Integer> countMap;

    public Counter() {
        countMap = new HashMap<>();
    }

    /**
     * 获取计数
     *
     * @param t 目标key
     * @return 目标key对应计数
     */
    public synchronized int getCount(T t) {
        return countMap.computeIfAbsent(t, k -> 0);
    }

    /**
     * 计数 + 1
     *
     * @param t 目标key
     */
    public void count(T t) {
        count(t, 1);
    }

    /**
     * 计数 + N
     *
     * @param t     目标key
     * @param count 增加的计数
     */
    public synchronized void count(T t, int count) {
        countMap.put(t, getCount(t) + count);
    }

    /**
     * 清除计数
     *
     * @param t 目标key
     */
    public synchronized void clear(T t) {
        countMap.put(t, 0);
    }

    /**
     * 清空所有计数
     */
    public synchronized void clearAll() {
        countMap.clear();
    }
}
