package idea.verlif.mock.data.config;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.config.filter.ClassFilter;
import idea.verlif.mock.data.config.filter.FieldFilter;
import idea.verlif.mock.data.creator.DataCreator;
import idea.verlif.mock.data.creator.InstanceCreator;
import idea.verlif.mock.data.domain.MockSrc;
import idea.verlif.mock.data.exception.ClassNotMatchException;
import idea.verlif.mock.data.exception.MockDataException;
import idea.verlif.mock.data.transfer.DataTranspiler;
import idea.verlif.mock.data.util.ContainsUtil;
import idea.verlif.mock.data.util.NamingUtil;
import idea.verlif.reflection.domain.ClassGrc;
import idea.verlif.reflection.domain.SFunction;
import idea.verlif.reflection.util.FieldUtil;

import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Verlif
 */
public class CommonConfig extends DataTranspiler {

    protected final Random random;

    /**
     * 属性创造器表
     */
    protected final Map<String, DataCreator<?>> fieldCreatorMap;

    /**
     * 接口类型构造器表
     */
    protected final Map<Class<?>, DataCreator<?>> interfaceCreatorMap;

    /**
     * 实例构造器表
     */
    protected final Map<String, InstanceCreator<?>> instanceCreatorMap;

    /**
     * 级联构造列表
     */
    protected final Set<String> cascadeCreateSet;

    /**
     * 级联构造正则列表
     */
    protected final List<Pattern> cascadeCreatePattern;

    /**
     * 属性过滤器列表
     */
    protected final ArrayList<FieldFilter> fieldFilters;

    /**
     * 类过滤器列表
     */
    protected final ArrayList<ClassFilter> classFilters;

    /**
     * 属性数据池
     */
    protected DataPool dataPool;

    public CommonConfig() {
        random = new Random();
        fieldCreatorMap = new HashMap<>();
        interfaceCreatorMap = new HashMap<>();
        instanceCreatorMap = new HashMap<>();
        cascadeCreateSet = new HashSet<>();
        cascadeCreatePattern = new ArrayList<>();

        fieldFilters = new ArrayList<>();
        classFilters = new ArrayList<>();
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
    protected <T> void addFieldValue(SFunction<T, ?> function, DataCreator<?> creator) {
        Field field = FieldUtil.getFieldFromLambda(function);
        addFieldValue(NamingUtil.getKeyName(field), creator);
    }

    /**
     * 添加或替换属性数据创造器
     *
     * @param function 属性获取表达式
     * @param creator  数据创造器
     */
    public <T> CommonConfig fieldValue(SFunction<T, ?> function, DataCreator<?> creator) {
        addFieldValue(function, creator);
        return this;
    }

    /**
     * 添加或替换属性数据
     *
     * @param function 属性获取表达式
     * @param o        属性对应数据
     */
    public <T> CommonConfig fieldObject(SFunction<T, ?> function, Object o) {
        addFieldValue(function, new StaticValueCreator(o));
        return this;
    }

    /**
     * 添加或替换属性数据创造器
     *
     * @param key     属性key值
     * @param creator 数据创造器
     */
    protected void addFieldValue(String key, DataCreator<?> creator) {
        fieldCreatorMap.put(key, creator);
    }

    /**
     * 添加或替换属性数据创造器
     *
     * @param key     属性key值。示例：int.class、demo.Person.name
     * @param creator 数据创造器
     */
    public CommonConfig fieldValue(String key, DataCreator<?> creator) {
        addFieldValue(key, creator);
        return this;
    }

    /**
     * 添加或替换属性数据创造器
     *
     * @param cla     目标类
     * @param creator 数据创造器
     */
    protected void addFieldValue(Class<?> cla, DataCreator<?> creator) {
        String key = NamingUtil.getKeyName(cla);
        addFieldValue(key, creator);
    }

    /**
     * 添加或替换属性数据创造器
     *
     * @param creator 数据创造器
     */
    protected void addFieldValue(DataCreator<?> creator) {
        if (creator.getClass().getName().contains("$Lambda")) {
            throw new MockDataException("Lambda expressions are not recognized!");
        }
        for (Class<?> cla : creator.types()) {
            addFieldValue(cla, creator);
        }
    }

    /**
     * 添加或替换属性数据创造器
     *
     * @param cla     目标类
     * @param creator 数据填充器
     */
    public CommonConfig fieldValue(Class<?> cla, DataCreator<?> creator) {
        addFieldValue(cla, creator);
        return this;
    }

    /**
     * 获取接口构造器
     *
     * @param cla 目标类
     */
    public DataCreator<?> getInterfaceValue(Class<?> cla) {
        return this.interfaceCreatorMap.get(cla);
    }

    /**
     * 添加或替换属性接口创造器
     *
     * @param cla     目标类
     * @param creator 数据填充器
     */
    public CommonConfig interfaceValue(Class<?> cla, DataCreator<?> creator) {
        addInterfaceValue(cla, creator);
        return this;
    }

    /**
     * 添加或替换属性接口创造器
     *
     * @param cla     目标类
     * @param creator 数据填充器
     */
    protected void addInterfaceValue(Class<?> cla, DataCreator<?> creator) {
        if (cla.isInterface()) {
            this.interfaceCreatorMap.put(cla, creator);
        } else {
            throw new ClassNotMatchException(cla + " is not interface!");
        }
    }

    /**
     * 添加或替换属性数据
     *
     * @param cla 目标类
     * @param o   属性对应数据
     */
    public <T> CommonConfig fieldObject(Class<T> cla, Object o) {
        addFieldValue(cla, new StaticValueCreator(o));
        return this;
    }

    /**
     * 添加或替换属性数据创造器
     *
     * @param creator 数据创造器
     */
    public CommonConfig fieldValue(DataCreator<?> creator) {
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
    protected void addInstanceCreator(InstanceCreator<?> creator) {
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
    public CommonConfig instanceCreator(InstanceCreator<?> creator) {
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
    public CommonConfig cascadeCreateKey(Class<?>... clas) {
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
    protected <T> void addCascadeCreateKey(SFunction<T, ?> function) {
        addCascadeCreateKey(NamingUtil.getKeyName(FieldUtil.getFieldFromLambda(function)));
    }

    /**
     * 添加级联构造的属性
     *
     * @param function 需要级联构造的属性
     */
    public <T> CommonConfig cascadeCreateKey(SFunction<T, ?> function) {
        addCascadeCreateKey(function);
        return this;
    }

    /**
     * 添加级联构造的key
     *
     * @param key 需要级联构造的key
     */
    protected void addCascadeCreateKey(String key) {
        cascadeCreateSet.add(key);
    }

    /**
     * 添加级联构造的属性key的正则表达
     *
     * @param regex 需要级联构造的属性key的正则表达
     */
    public CommonConfig cascadeCreatePattern(String... regex) {
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
    protected void addCascadeCreatePattern(String regex) {
        cascadeCreatePattern.add(Pattern.compile(regex));
    }

    /**
     * 添加级联构造的属性key的包名
     *
     * @param packName 需要级联构造的包名
     */
    public CommonConfig cascadeCreatePackage(String... packName) {
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
    protected void addCascadeCreatePackage(String packName) {
        addCascadeCreatePattern(packName + ".*" + NamingUtil.KEY_SUFFIX_CLASS);
    }

    /**
     * 添加级联构造的key
     *
     * @param key 需要级联构造的key
     */
    public CommonConfig cascadeCreateKey(String key) {
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
        removeCascadeCreateKey(NamingUtil.getKeyName(FieldUtil.getFieldFromLambda(function)));
    }

    /**
     * 移除级联构造的key
     *
     * @param key 需要级联构造的key
     */
    protected void removeCascadeCreateKey(String key) {
        cascadeCreateSet.remove(key);
    }

    /**
     * 查询属性是否级联构造
     *
     * @param key 目标key
     * @return 目标key是否级联构造
     */
    public boolean isCascadeCreate(String key) {
        return ContainsUtil.checkContains(key, cascadeCreateSet, cascadeCreatePattern);
    }

    /**
     * 添加类过滤器
     *
     * @param filter 类过滤器
     */
    public CommonConfig filter(ClassFilter filter) {
        classFilters.add(filter);
        return this;
    }

    /**
     * 添加属性过滤器
     *
     * @param filter 属性过滤器
     */
    public CommonConfig filter(FieldFilter filter) {
        fieldFilters.add(filter);
        return this;
    }

    public void clearFieldFilter() {
        fieldFilters.clear();
    }

    public void clearClassFilter() {
        classFilters.clear();
    }

    /**
     * 判断该属性是否是被忽略的
     *
     * @param field 目标属性
     */
    public boolean isAllowedField(Object target, Field field) {
        for (FieldFilter filter : fieldFilters) {
            if (!filter.accept(target, field)) {
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
     * 设置配置属性数据池
     *
     * @param pool 属性数据池
     */
    public CommonConfig dataPool(DataPool pool) {
        this.dataPool = pool;
        return this;
    }

    /**
     * 获取当前配置的属性数据池
     */
    public DataPool getDataPool() {
        return dataPool;
    }

    public Object randomDataFromDataPool(ClassGrc classGrc, String key) {
        if (dataPool == null) {
            return null;
        } else if (key == null) {
            key = "";
        }
        Object[] values = dataPool.getValues(classGrc, key);
        if (values == null || values.length == 0) {
            return null;
        } else if (values.length == 1) {
            return values[0];
        }
        return values[random.nextInt(values.length)];
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
        public Object mock(MockSrc src, MockDataCreator.Creator creator) {
            return o;
        }
    }
}
