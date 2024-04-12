package idea.verlif.mock.data.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * 计数器
 *
 * @author Verlif
 */
public abstract class Counter<T> {

    protected int defaultCount;

    protected final Map<T, Integer> countMap;

    public Counter() {
        this(0);
    }

    public Counter(int defaultCount) {
        this.defaultCount = defaultCount;
        this.countMap = new HashMap<>();
    }

    /**
     * 获取计数
     *
     * @param t 目标key
     * @return 目标key对应计数
     */
    public synchronized int getCount(T t) {
        return countMap.computeIfAbsent(t, k -> defaultCount);
    }

    /**
     * 获取计数
     *
     * @param t 目标key
     * @return 目标key对应计数
     */
    public Integer getCountWithNull(T t) {
        return countMap.get(t);
    }

    /**
     * 计数 + 1
     *
     * @param t 目标key
     */
    public int count(T t) {
        return count(t, 1);
    }

    /**
     * 计数 + N
     *
     * @param t     目标key
     * @param count 增加的计数
     */
    public synchronized int count(T t, int count) {
        int c = getCount(t) + count;
        countMap.put(t, c);
        return c;
    }

    /**
     * 设定计数
     *
     * @param t     目标key
     * @param count 设定的计数
     */
    public synchronized void setCount(T t, int count) {
        countMap.put(t, count);
    }

    /**
     * 获取初始计数
     *
     * @return 初始计数
     */
    public int getDefaultCount() {
        return defaultCount;
    }

    /**
     * 设定初始计数
     *
     * @param defaultCount 初始计数
     */
    public synchronized void setDefaultCount(int defaultCount) {
        this.defaultCount = defaultCount;
    }

    /**
     * 清除计数
     *
     * @param t 目标key
     */
    public synchronized void clear(T t) {
        countMap.put(t, defaultCount);
    }

    /**
     * 清空所有计数
     */
    public synchronized void clearAll() {
        countMap.clear();
    }
}
