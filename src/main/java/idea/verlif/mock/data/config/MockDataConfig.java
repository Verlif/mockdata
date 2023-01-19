package idea.verlif.mock.data.config;

import idea.verlif.mock.data.creator.DataCreator;
import idea.verlif.mock.data.creator.InstanceCreator;
import idea.verlif.mock.data.domain.MockField;
import idea.verlif.mock.data.domain.SFunction;
import idea.verlif.mock.data.util.NamingUtil;
import idea.verlif.mock.data.util.ReflectUtil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Verlif
 */
public class MockDataConfig {

    /**
     * 属性填充的循环次数
     */
    private int circleCount = 1;

    /**
     * 忽略未知的属性
     */
    private boolean ignoredUnknownField = false;

    /**
     * 在没有数据构造器时尝试进行无参构造对象
     */
    private boolean tryNoParamsConstructor = true;

    /**
     * 构建数组时的填充长度
     */
    private int arraySize = 10;

    /**
     * 属性创造器表
     */
    private final Map<String, DataCreator<?>> fieldCreatorMap;

    /**
     * 实例构造器表
     */
    private final Map<String, InstanceCreator<?>> instanceCreatorMap;

    /**
     * 级联构造列表
     */
    private final Set<String> cascadeCreateSet;

    /**
     * 构造时忽略的属性
     */
    private final Set<String> ignoredFiledSet;

    public MockDataConfig() {
        fieldCreatorMap = new HashMap<>();
        instanceCreatorMap = new HashMap<>();
        cascadeCreateSet = new HashSet<>();
        ignoredFiledSet = new HashSet<>();
    }

    public int getCircleCount() {
        return circleCount;
    }

    public void setCircleCount(int circleCount) {
        this.circleCount = circleCount;
    }

    /**
     * 忽略未知的属性
     */
    public void ignoredUnknownField() {
        ignoredUnknownField = true;
    }

    public boolean isIgnoredUnknownField() {
        return ignoredUnknownField;
    }

    public void setTryNoParamsConstructor(boolean tryNoParamsConstructor) {
        this.tryNoParamsConstructor = tryNoParamsConstructor;
    }

    public boolean isTryNoParamsConstructor() {
        return tryNoParamsConstructor;
    }

    public int getArraySize() {
        return arraySize;
    }

    public void setArraySize(int arraySize) {
        this.arraySize = arraySize;
    }

    public <T> DataCreator<T> getDataCreator(MockField field) {
        return getDataCreator(field.getKey());
    }

    public <T> DataCreator<T> getDataCreator(String key) {
        return (DataCreator<T>) fieldCreatorMap.get(key);
    }

    /**
     * 添加或替换属性数据创造器
     *
     * @param function 属性获取表达式
     * @param creator  数据创造器
     */
    public <T> void addFieldCreator(SFunction<T, ?> function, DataCreator<?> creator) {
        Field field = ReflectUtil.getFieldFromLambda(function, true);
        MockField mf = new MockField(field);
        addFieldCreator(mf.getKey(), creator);
    }

    /**
     * 添加或替换属性数据创造器
     *
     * @param key     属性key值
     * @param creator 数据创造器
     */
    public <T> void addFieldCreator(String key, DataCreator<?> creator) {
        fieldCreatorMap.put(
                key,
                creator);
    }

    /**
     * 添加或替换属性数据创造器
     *
     * @param cla     目标类
     * @param creator 数据创造器
     */
    public <T> void addFieldCreator(Class<?> cla, DataCreator<?> creator) {
        fieldCreatorMap.put(
                NamingUtil.getKeyName(cla),
                creator);
    }

    /**
     * 是否拥有独立属性构造器
     *
     * @param key 属性key
     */
    public boolean hasFiledCreator(String key) {
        return fieldCreatorMap.containsKey(key);
    }

    /**
     * 添加实例构造器
     *
     * @param creator 实例构造器
     */
    public void addInstanceCreator(InstanceCreator<?> creator) {
        Class<?> cla = creator.matched();
        if (cla != null) {
            instanceCreatorMap.put(NamingUtil.getKeyName(cla), creator);
        }
    }

    /**
     * 获取实例构造器
     *
     * @param cla 实例类
     * @param <T> 实例类
     * @return 实例类对应实例构造器
     */
    public <T> InstanceCreator<T> getInstanceCreator(Class<T> cla) {
        return (InstanceCreator<T>) instanceCreatorMap.get(NamingUtil.getKeyName(cla));
    }

    /**
     * 添加级联构造的类
     *
     * @param cla 需要级联构造的类
     */
    public void addCascadeCreateKey(Class<?> cla) {
        addCascadeCreateKey(NamingUtil.getKeyName(cla));
    }

    /**
     * 添加级联构造的属性
     *
     * @param function 需要级联构造的属性
     */
    public void addCascadeCreateKey(SFunction<?, ?> function) {
        addCascadeCreateKey(NamingUtil.getKeyName(ReflectUtil.getFieldFromLambda(function, true)));
    }

    /**
     * 添加级联构造的key
     *
     * @param key 需要级联构造的key
     */
    public void addCascadeCreateKey(String key) {
        cascadeCreateSet.add(key);
    }

    /**
     * 查询属性是否级联构造
     *
     * @param key 目标key
     * @return 目标key是否级联构造
     */
    public boolean isCascadeCreate(String key) {
        return cascadeCreateSet.contains(key);
    }

    /**
     * 增加忽略的属性
     */
    public <T> void addIgnoredField(SFunction<T, ?> function) {
        ignoredFiledSet.add(NamingUtil.getKeyName(ReflectUtil.getFieldFromLambda(function)));
    }

    /**
     * 增加忽略的属性
     */
    public void addIgnoredField(Class<?> cla) {
        ignoredFiledSet.add(NamingUtil.getKeyName(cla));
    }

    /**
     * 判断该属性是否是被忽略的
     *
     * @param key 属性key
     */
    public boolean isIgnoredFiled(String key) {
        return ignoredFiledSet.contains(key);
    }
}
