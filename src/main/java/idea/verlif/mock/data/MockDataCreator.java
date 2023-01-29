package idea.verlif.mock.data;

import idea.verlif.mock.data.config.MockDataConfig;
import idea.verlif.mock.data.creator.DataCreator;
import idea.verlif.mock.data.creator.InstanceCreator;
import idea.verlif.mock.data.creator.data.*;
import idea.verlif.mock.data.domain.SFunction;
import idea.verlif.mock.data.domain.counter.StringCounter;
import idea.verlif.mock.data.util.NamingUtil;
import idea.verlif.mock.data.util.ReflectUtil;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Verlif
 */
public class MockDataCreator {

    private final Map<String, DataCreator<?>> defaultCreatorMap;

    private MockDataConfig config;

    public MockDataCreator() {
        this.defaultCreatorMap = new HashMap<>();

        this.config = new MockDataConfig();
        useBaseData();
        useExtendData();
    }

    public void setConfig(MockDataConfig config) {
        this.config = config;
    }

    public MockDataConfig getConfig() {
        return config;
    }

    /**
     * 使用基础数据
     */
    public void useBaseData() {
        addDefaultCreator(new ByteRandomCreator());
        addDefaultCreator(new BooleanRandomCreator());
        addDefaultCreator(new ShortRandomCreator());
        addDefaultCreator(new IntegerRandomCreator());
        addDefaultCreator(new LongRandomCreator());
        addDefaultCreator(new FloatRandomCreator());
        addDefaultCreator(new DoubleRandomCreator());
        addDefaultCreator(new CharacterRandomCreator());
        addDefaultCreator(new StringRandomCreator());
        addDefaultCreator(new ListCreator());
        addDefaultCreator(new DateRandomCreator());
        addDefaultCreator(new EnumRandomCreator());
    }

    /**
     * 使用拓展数据
     */
    public void useExtendData() {
        addDefaultCreator(new BigIntegerCreator());
        addDefaultCreator(new BigDecimalCreator());
        addDefaultCreator(new LocalDateCreator());
        addDefaultCreator(new LocalTimeCreator());
        addDefaultCreator(new LocalDateTimeCreator());
        addDefaultCreator(new MapRandomCreator());
    }

    /**
     * 添加或是替换数据创造器
     *
     * @param creator 数据创造器
     */
    public void addDefaultCreator(DataCreator<?> creator) {
        for (Class<?> cla : creator.types()) {
            defaultCreatorMap.put(NamingUtil.getKeyName(cla), creator);
        }
    }

    /**
     * 添加或是替换数据创造器
     *
     * @param creator 数据创造器
     */
    public MockDataCreator defaultCreator(DataCreator<?> creator) {
        addDefaultCreator(creator);
        return this;
    }


    /**
     * mock数据
     *
     * @param function Lambda表达式
     * @param <T>      目标泛型
     * @return 返回对象本身
     */
    public <T, V> V mock(SFunction<T, V> function) throws IllegalAccessException {
        return (V) mock(ReflectUtil.getFieldFromLambda(function).getType());
    }

    /**
     * mock数据
     *
     * @param t   对象实例
     * @param <T> 目标泛型
     * @return 返回对象本身
     */
    public <T> T mock(T t) throws IllegalAccessException {
        return mock(t, config);
    }

    /**
     * mock数据
     *
     * @param t      对象实例
     * @param config 使用的配置
     * @param <T>    目标泛型
     * @return 返回对象本身
     */
    public <T> T mock(T t, MockDataConfig config) throws IllegalAccessException {
        Creator creator = new Creator(config);
        creator.mock(t, (Class<T>) t.getClass());
        creator.counterClear();
        return t;
    }

    /**
     * mock数据
     *
     * @param cla 实例类
     * @param <T> 目标泛型
     * @return 返回新实例
     */
    public <T> T mock(Class<T> cla) throws IllegalAccessException {
        return mock(cla, config);
    }

    /**
     * mock数据
     *
     * @param cla    实例类
     * @param config 使用的配置
     * @param <T>    目标泛型
     * @return 返回新实例
     */
    public <T> T mock(Class<T> cla, MockDataConfig config) throws IllegalAccessException {
        Creator creator = new Creator(config);
        T t = creator.mockClass(cla);
        creator.counterClear();
        return t;
    }

    public final class Creator {

        /**
         * 引用构建次数统计
         */
        private final StringCounter counter;

        /**
         * 创建器配置
         */
        private final MockDataConfig mockConfig;

        public Creator(MockDataConfig config) {
            this.counter = new StringCounter();
            this.mockConfig = config;
        }

        public void counterClear() {
            counter.clearAll();
        }

        /**
         * mock数据
         *
         * @param cla 目标类
         * @param <T> 目标类泛型
         * @return 目标类
         */
        public <T> T mockClass(Class<T> cla) throws IllegalAccessException {
            String claKey = NamingUtil.getKeyName(cla);
            // 是否是忽略类
            if (mockConfig.isIgnoredFiled(claKey)) {
                return null;
            }
            T t;
            // 检测是否存在自定义构建器
            DataCreator<?> dataCreator = getDataCreator(claKey);
            // 不存在则尝试新建对象
            if (dataCreator == null) {
                t = newInstance(cla);
                // 如果是数组则取其真实类
                Class<?> tmpCla = cla;
                while (tmpCla.isArray()) {
                    tmpCla = tmpCla.getComponentType();
                }
                claKey = NamingUtil.getKeyName(tmpCla);
                // 如果此类允许级联构造则进行mock
                if (mockConfig.isCascadeCreate(claKey)) {
                    mock(t, cla);
                }
            } else {
                t = (T) dataCreator.mock(null, this);
            }
            return t;
        }

        /**
         * mock数据
         *
         * @param t   目标类实例
         * @param cla 目标类
         * @param <T> 目标类泛型
         * @return 目标类
         */
        public <T> T mock(T t, Class<T> cla) throws IllegalAccessException {
            // 判断是否是忽略类
            if (mockConfig.isIgnoredFiled(NamingUtil.getKeyName(cla))) {
                return t;
            }
            // 判断是否是枚举类
            if (cla.isEnum()) {
                // 枚举对象直接返回
                return t;
            } else {
                fillField(t, cla);
            }
            return t;
        }

        /**
         * 填充数组
         *
         * @param o   数组引用对象
         * @param cla 数组类型
         * @throws IllegalAccessException
         */
        private void fillArray(Object o, Class<?> cla) throws IllegalAccessException {
            // 当前的实际类
            Class<?> componentType = cla.getComponentType();
            // 当前的多维数组
            int size = Array.getLength(o);
            // 遍历多维数组的最外层
            for (int i = 0; i < size; i++) {
                // 如果多维数组的降维后还是数组
                if (componentType.isArray()) {
                    // 进行递归
                    Class<?> realClaDepp = componentType.getComponentType();
                    Object arr = Array.get(o, i);
                    if (arr == null) {
                        arr = Array.newInstance(realClaDepp, mockConfig.getArraySize());
                    }
                    arr = Array.newInstance(realClaDepp, Array.getLength(arr));
                    fillArray(arr, componentType);
                    Array.set(o, i, arr);
                } else {
                    Array.set(o, i, MockDataCreator.this.mock(componentType));
                }
            }
        }

        /**
         * 为对象填充属性
         *
         * @param t   目标对象
         * @param cla 目标对象类
         */
        private void fillField(Object t, Class<?> cla) throws IllegalAccessException {
            if (t == null) {
                return;
            }
            // 判断填充对象是否是数组
            if (cla.isArray()) {
                // 填充数组
                fillArray(t, cla);
            } else {
                // 遍历属性进行填充
                List<Field> allFields = ReflectUtil.getAllFields(cla);
                for (Field field : allFields) {
                    // 判断此属性是否支持构建
                    String key = NamingUtil.getKeyName(field);
                    Class<?> fieldCla = field.getType();
                    String claKey = NamingUtil.getKeyName(fieldCla);
                    if (mockConfig.isIgnoredFiled(key) || mockConfig.isIgnoredFiled(claKey) || !mockConfig.isAllowField(field)) {
                        continue;
                    }
                    int max = mockConfig.getCreatingDepth();
                    // 判定属性引用次数是否已到达最大值
                    if (counter.getCount(key) < max) {
                        // 设定可访问
                        boolean oldAcc = field.isAccessible();
                        if (!oldAcc) {
                            field.setAccessible(true);
                        }
                        // 获取属性可能存在的对象
                        Object o = field.get(t);
                        // 如果对象已存在则判断是否重新创建
                        if (o == null || mockConfig.isForceNew()) {
                            int count = counter.count(key);
                            // 判定类是否存在构造器
                            DataCreator<?> configCreator = getDataCreator(field);
                            // 构造器存在则使用构造器进行构造
                            if (configCreator != null) {
                                o = configCreator.mock(field, this);
                            } else {
                                // 判断属性是否允许级联构造
                                o = newInstance(fieldCla);
                                if (mockConfig.isCascadeCreate(claKey) || mockConfig.isCascadeCreate(key) || fieldCla.isArray()) {
                                    // 进行级联构造
                                    fillField(o, field.getType());
                                }
                            }
                            if (o != null) {
                                field.set(t, o);
                            }
                            counter.setCount(key, count - 1);
                        }
                        // 还原权限
                        if (!oldAcc) {
                            field.setAccessible(false);
                        }
                    }
                }
            }
        }

        /**
         * 创建新实例
         *
         * @param cla 目标类
         * @param <T> 目标类
         * @return 实例对象
         */
        public <T> T newInstance(Class<T> cla, Object... params) {
            InstanceCreator<T> instanceCreator = mockConfig.getInstanceCreator(cla);
            // 实例构造器不存在时，尝试进行参数构造
            if (instanceCreator == null) {
                if (cla.isArray()) {
                    return (T) Array.newInstance(cla.getComponentType(), mockConfig.getArraySize());
                } else {
                    try {
                        return ReflectUtil.newInstance(cla, params);
                    } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                return instanceCreator.newInstance();
            }
        }

        /**
         * 获取数据构造器
         *
         * @param key 构造器key
         * @return key对应的数据构造器
         */
        public DataCreator<?> getDataCreator(String key) {
            DataCreator<?> creator = mockConfig.getDataCreator(key);
            if (creator == null) {
                creator = defaultCreatorMap.get(key);
            }
            return creator;
        }

        /**
         * 获取数据构造器
         *
         * @param field 目标类型
         * @return 目标类型对应的数据构造器
         */
        public DataCreator<?> getDataCreator(Field field) {
            // 优先从配置中获取属性构造器
            DataCreator<?> creator = getDataCreator(NamingUtil.getKeyName(field));
            if (creator != null) {
                return creator;
            }
            // 获取属性类构造器
            Class<?> cla = field.getType();
            String key;
            // 向类上级求取构造器
            do {
                key = NamingUtil.getKeyName(cla);
                creator = mockConfig.getDataCreator(key);
                if (creator == null) {
                    creator = defaultCreatorMap.get(key);
                }
                if (creator != null) {
                    return creator;
                }
                cla = cla.getSuperclass();
            } while (cla != null);
            return null;
        }
    }
}
