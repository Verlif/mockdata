package idea.verlif.mock.data.config;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.config.filter.ClassFilter;
import idea.verlif.mock.data.config.filter.FieldFilter;
import idea.verlif.mock.data.creator.DataCreator;
import idea.verlif.mock.data.creator.InstanceCreator;
import idea.verlif.mock.data.domain.SFunction;
import idea.verlif.mock.data.domain.counter.StringCounter;
import idea.verlif.mock.data.exception.MockDataException;
import idea.verlif.mock.data.util.ContainsUtil;
import idea.verlif.mock.data.util.NamingUtil;
import idea.verlif.mock.data.util.ReflectUtil;

import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Verlif
 */
public class MockDataConfig {

    private static final int ARRAY_SIZE = 5;
    private static final int DEFAULT_DEPTH = 2;

    /**
     * 属性填充的循环次数
     */
    private StringCounter depthCounter;

    /**
     * 构建数组时的填充长度
     */
    private SizeCreator arraySizeCreator;

    /**
     * 属性创造器表
     */
    private final Map<String, DataCreator<?>> fieldCreatorMap;

    /**
     * 实例构造器表
     */
    private final Map<String, InstanceCreator<?>> instanceCreatorMap;

    /**
     * 自动级联构建
     */
    private boolean autoCascade;

    /**
     * 级联构造列表
     */
    private final Set<String> cascadeCreateSet;

    /**
     * 级联构造正则列表
     */
    private final List<Pattern> cascadeCreatePattern;

    /**
     * 属性过滤器列表
     */
    private final ArrayList<FieldFilter> fieldFilters;

    /**
     * 类过滤器列表
     */
    private final ArrayList<ClassFilter> classFilters;

    /**
     * 强制生成新对象
     */
    private boolean forceNew = false;

    public MockDataConfig() {
        fieldCreatorMap = new HashMap<>();
        instanceCreatorMap = new HashMap<>();
        cascadeCreateSet = new HashSet<>();
        cascadeCreatePattern = new ArrayList<>();

        fieldFilters = new ArrayList<>();
        classFilters = new ArrayList<>();
    }

    public MockDataConfig copy() {
        MockDataConfig config = new MockDataConfig();
        config.depthCounter = this.depthCounter;
        config.arraySizeCreator = this.arraySizeCreator;
        config.fieldCreatorMap.putAll(this.fieldCreatorMap);
        config.instanceCreatorMap.putAll(this.instanceCreatorMap);
        config.autoCascade = this.autoCascade;
        config.cascadeCreateSet.addAll(this.cascadeCreateSet);
        config.cascadeCreatePattern.addAll(this.cascadeCreatePattern);
        config.fieldFilters.addAll(this.fieldFilters);
        config.classFilters.addAll(this.classFilters);
        config.forceNew = this.forceNew;

        return config;
    }

    public int getCreatingDepth(String key) {
        if (depthCounter == null) {
            return DEFAULT_DEPTH;
        }
        return depthCounter.getCount(key);
    }

    public MockDataConfig creatingDepth(int defaultDepth) {
        if (depthCounter == null) {
            depthCounter = new StringCounter(defaultDepth);
        } else {
            depthCounter.setDefaultCount(defaultDepth);
        }
        return this;
    }

    /**
     * 设定构建深度
     *
     * @param function 属性表达式
     * @param depth    属性的构建深度
     */
    public <T> MockDataConfig creatingDepth(SFunction<T, ?> function, int depth) {
        setKeyDepth(NamingUtil.getKeyName(ReflectUtil.getFieldFromLambda(function)), depth);
        return this;
    }

    /**
     * 设定构建深度
     *
     * @param cla   目标类
     * @param depth 目标类的构建深度
     */
    public MockDataConfig creatingDepth(Class<?> cla, int depth) {
        setKeyDepth(NamingUtil.getKeyName(cla), depth);
        return this;
    }

    private void setKeyDepth(String key, int depth) {
        if (depthCounter == null) {
            depthCounter = new StringCounter(DEFAULT_DEPTH);
        }
        depthCounter.setCount(key, depth);
    }

    public int getArraySize(Class<?> cla) {
        if (arraySizeCreator == null) {
            return ARRAY_SIZE;
        }
        return arraySizeCreator.getSize(cla);
    }

    public MockDataConfig arraySize(int arraySize) {
        this.arraySizeCreator = new StaticSizeCreator(arraySize);
        return this;
    }

    public MockDataConfig arraySize(SizeCreator sizeCreator) {
        this.arraySizeCreator = sizeCreator;
        return this;
    }

    public boolean isForceNew() {
        return forceNew;
    }

    public MockDataConfig forceNew(boolean forceNew) {
        this.forceNew = forceNew;
        return this;
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
    private <T> void addFieldValue(SFunction<T, ?> function, DataCreator<?> creator) {
        Field field = ReflectUtil.getFieldFromLambda(function, true);
        addFieldValue(NamingUtil.getKeyName(field), creator);
    }

    /**
     * 添加或替换属性数据创造器
     *
     * @param function 属性获取表达式
     * @param creator  数据创造器
     */
    public <T> MockDataConfig fieldValue(SFunction<T, ?> function, DataCreator<?> creator) {
        addFieldValue(function, creator);
        return this;
    }

    /**
     * 添加或替换属性数据
     *
     * @param function 属性获取表达式
     * @param o        属性对应数据
     */
    public <T> MockDataConfig fieldValue(SFunction<T, ?> function, Object o) {
        addFieldValue(function, new StaticValueCreator(o));
        return this;
    }

    /**
     * 添加或替换属性数据创造器
     *
     * @param key     属性key值
     * @param creator 数据创造器
     */
    private void addFieldValue(String key, DataCreator<?> creator) {
        if (creator.getClass().getName().contains("$Lambda")) {
            throw new MockDataException("Lambda expressions are not recognized!");
        }
        fieldCreatorMap.put(
                key,
                creator);
    }

    /**
     * 添加或替换属性数据创造器
     *
     * @param key     属性key值
     * @param creator 数据创造器
     */
    public MockDataConfig fieldValue(String key, DataCreator<?> creator) {
        addFieldValue(key, creator);
        return this;
    }

    /**
     * 添加或替换属性数据创造器
     *
     * @param cla     目标类
     * @param creator 数据创造器
     */
    private void addFieldValue(Class<?> cla, DataCreator<?> creator) {
        String key = NamingUtil.getKeyName(cla);
        addFieldValue(key, creator);
    }

    /**
     * 添加或替换属性数据创造器
     *
     * @param creator 数据创造器
     */
    private void addFieldValue(DataCreator<?> creator) {
        for (Class<?> cla : creator.types()) {
            addFieldValue(cla, creator);
        }
    }

    /**
     * 添加或替换属性数据创造器
     *
     * @param cla     目标类
     * @param creator 数据创造器
     */
    public <T> MockDataConfig fieldValue(Class<T> cla, DataCreator<T> creator) {
        if (creator.getClass().getName().contains("$Lambda")) {
            throw new MockDataException("Lambda expressions are not recognized!");
        }
        addFieldValue(cla, creator);
        return this;
    }

    /**
     * 添加或替换属性数据
     *
     * @param cla 目标类
     * @param o   属性对应数据
     */
    public <T> MockDataConfig fieldValue(Class<T> cla, Object o) {
        addFieldValue(cla, new StaticValueCreator(o));
        return this;
    }

    /**
     * 添加或替换属性数据创造器
     *
     * @param creator 数据创造器
     */
    public MockDataConfig fieldValue(DataCreator<?> creator) {
        addFieldValue(creator);
        return this;
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
    private void addInstanceCreator(InstanceCreator<?> creator) {
        Class<?> cla = creator.matched();
        if (cla != null) {
            instanceCreatorMap.put(NamingUtil.getKeyName(cla), creator);
        }
    }

    /**
     * 添加实例构造器
     *
     * @param creator 实例构造器
     */
    public MockDataConfig instanceCreator(InstanceCreator<?> creator) {
        addInstanceCreator(creator);
        return this;
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
     * @param clas 需要级联构造的类
     */
    public MockDataConfig cascadeCreateKey(Class<?>... clas) {
        for (Class<?> cla : clas) {
            addCascadeCreateKey(NamingUtil.getKeyName(cla));
        }
        return this;
    }

    /**
     * 添加级联构造的属性
     *
     * @param function 需要级联构造的属性
     */
    private <T> void addCascadeCreateKey(SFunction<T, ?> function) {
        addCascadeCreateKey(NamingUtil.getKeyName(ReflectUtil.getFieldFromLambda(function, true)));
    }

    /**
     * 添加级联构造的属性
     *
     * @param function 需要级联构造的属性
     */
    public <T> MockDataConfig cascadeCreateKey(SFunction<T, ?> function) {
        addCascadeCreateKey(function);
        return this;
    }

    /**
     * 添加级联构造的key
     *
     * @param key 需要级联构造的key
     */
    private void addCascadeCreateKey(String key) {
        cascadeCreateSet.add(key);
    }

    /**
     * 添加级联构造的属性key的正则表达
     *
     * @param regex 需要级联构造的属性key的正则表达
     */
    public MockDataConfig cascadeCreatePattern(String... regex) {
        for (String s : regex) {
            addCascadeCreatePattern(s);
        }
        return this;
    }

    /**
     * 添加级联构造的属性key的正则表达
     *
     * @param regex 需要级联构造的属性key的正则表达
     */
    private void addCascadeCreatePattern(String regex) {
        cascadeCreatePattern.add(Pattern.compile(regex));
    }

    /**
     * 添加级联构造的属性key的包名
     *
     * @param packName 需要级联构造的包名
     */
    public MockDataConfig cascadeCreatePackage(String... packName) {
        for (String s : packName) {
            addCascadeCreatePackage(s);
        }
        return this;
    }

    /**
     * 添加级联构造的属性key的包名
     *
     * @param packName 需要级联构造的包名
     */
    private void addCascadeCreatePackage(String packName) {
        addCascadeCreatePattern(packName + ".*" + NamingUtil.KEY_SUFFIX_CLASS);
    }

    /**
     * 添加级联构造的key
     *
     * @param key 需要级联构造的key
     */
    public MockDataConfig cascadeCreateKey(String key) {
        addCascadeCreateKey(key);
        return this;
    }

    /**
     * 移除级联构造的类
     *
     * @param cla 需要级联构造的类
     */
    public void removeCascadeCreateKey(Class<?> cla) {
        removeCascadeCreateKey(NamingUtil.getKeyName(cla));
    }

    /**
     * 移除级联构造的属性
     *
     * @param function 需要级联构造的属性
     */
    public <T> void removeCascadeCreateKey(SFunction<T, ?> function) {
        removeCascadeCreateKey(NamingUtil.getKeyName(ReflectUtil.getFieldFromLambda(function, true)));
    }

    /**
     * 移除级联构造的key
     *
     * @param key 需要级联构造的key
     */
    private void removeCascadeCreateKey(String key) {
        cascadeCreateSet.remove(key);
    }

    /**
     * 设置自动级联构造标识
     *
     * @param autoCascade 是否自动级联构造
     */
    public MockDataConfig autoCascade(boolean autoCascade) {
        this.autoCascade = autoCascade;
        return this;
    }

    /**
     * 查询属性是否级联构造
     *
     * @param key 目标key
     * @return 目标key是否级联构造
     */
    public boolean isCascadeCreate(String key) {
        return autoCascade || ContainsUtil.checkContains(key, cascadeCreateSet, cascadeCreatePattern);
    }

    /**
     * 添加类过滤器
     *
     * @param filter 类过滤器
     */
    public MockDataConfig filter(ClassFilter filter) {
        classFilters.add(filter);
        return this;
    }

    /**
     * 添加属性过滤器
     *
     * @param filter 属性过滤器
     */
    public MockDataConfig filter(FieldFilter filter) {
        fieldFilters.add(filter);
        return this;
    }

    /**
     * 判断该属性是否是被忽略的
     *
     * @param field 目标属性
     */
    public boolean isAllowedField(Field field) {
        for (FieldFilter filter : fieldFilters) {
            if (!filter.accept(field)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断该类是否是被忽略的
     *
     * @param cla 目标类
     */
    public boolean isAllowedClass(Class<?> cla) {
        for (ClassFilter filter : classFilters) {
            if (!filter.accept(cla)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 固定大小创建器
     */
    private static final class StaticSizeCreator implements SizeCreator {

        private final int size;

        public StaticSizeCreator(int size) {
            this.size = size;
        }

        @Override
        public int getSize(Class<?> cla) {
            return size;
        }
    }

    /**
     * 静态值返回器
     */
    private static final class StaticValueCreator implements DataCreator<Object> {

        private final Object o;

        public StaticValueCreator(Object o) {
            this.o = o;
        }

        @Override
        public Object mock(Class<?> cla, Field field, MockDataCreator.Creator creator) {
            return o;
        }
    }
}
