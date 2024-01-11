package idea.verlif.mock.data;

import idea.verlif.mock.data.config.CommonConfig;
import idea.verlif.mock.data.config.FieldOption;
import idea.verlif.mock.data.config.MockDataConfig;
import idea.verlif.mock.data.creator.DataCreator;
import idea.verlif.mock.data.creator.InstanceCreator;
import idea.verlif.mock.data.creator.data.*;
import idea.verlif.mock.data.domain.MockSrc;
import idea.verlif.mock.data.domain.counter.StringCounter;
import idea.verlif.mock.data.util.NamingUtil;
import idea.verlif.reflection.domain.ClassGrc;
import idea.verlif.reflection.domain.FieldGrc;
import idea.verlif.reflection.domain.SFunction;
import idea.verlif.reflection.util.FieldUtil;
import idea.verlif.reflection.util.MethodUtil;
import idea.verlif.reflection.util.ReflectUtil;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Mock数据创建器
 *
 * @author Verlif
 */
public class MockDataCreator extends CommonConfig {

    private MockDataConfig config;

    public MockDataCreator() {
        this.config = new MockDataConfig();
        useBaseData();
        useExtendData();
        useBaseFiller();
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
        addFieldValue(new ByteRandomCreator());
        addFieldValue(new BooleanRandomCreator());
        addFieldValue(new ShortRandomCreator());
        addFieldValue(new IntegerRandomCreator());
        addFieldValue(new LongRandomCreator());
        addFieldValue(new FloatRandomCreator());
        addFieldValue(new DoubleRandomCreator());
        addFieldValue(new CharacterRandomCreator());
        addFieldValue(new StringRandomCreator());
        addFieldValue(new DateRandomCreator());
        addFieldValue(new EnumRandomCreator());
    }

    /**
     * 使用拓展数据
     */
    public void useExtendData() {
        addFieldValue(new BigIntegerCreator());
        addFieldValue(new BigDecimalCreator());
        addFieldValue(new LocalDateCreator());
        addFieldValue(new LocalTimeCreator());
        addFieldValue(new LocalDateTimeCreator());
    }

    public void useBaseFiller() {
        addInterfaceValue(List.class, new ListCreator());
        addInterfaceValue(Map.class, new MapCreator());
        addInterfaceValue(Set.class, new SetCreator());
    }

    /**
     * mock数据
     *
     * @param function Lambda表达式
     * @param <T>      目标泛型
     * @return 返回构建数据
     */
    public <T, V> V mock(SFunction<T, V> function) {
        Method method = MethodUtil.getMethodFromLambda(function);
        return (V) mock(method.getReturnType());
    }

    /**
     * mock数据
     *
     * @param t   对象实例
     * @param <T> 目标泛型
     * @return 返回对象本身
     */
    public <T> T mock(T t) {
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
    public <T> T mock(T t, MockDataConfig config) {
        Creator creator = new Creator(config);
        creator.mock(t, ReflectUtil.getClassGrc(t.getClass()));
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
    public <T> T mock(Class<T> cla) {
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
    public <T> T mock(Class<T> cla, MockDataConfig config) {
        return mock(ReflectUtil.getClassGrc(cla), config);
    }


    /**
     * mock数据
     *
     * @param classGrc 实例类信息
     * @param <T>      目标泛型
     * @return 返回新实例
     */
    public <T> T mock(ClassGrc classGrc) {
        return mock(classGrc, config);
    }

    /**
     * mock数据
     *
     * @param classGrc 实例类信息
     * @param config   使用的配置
     * @param <T>      目标泛型
     * @return 返回新实例
     */
    public <T> T mock(ClassGrc classGrc, MockDataConfig config) {
        Creator creator = new Creator(config);
        T t = creator.mockClass(classGrc);
        creator.counterClear();
        return t;
    }

    @Override
    public boolean isCascadeCreate(String key) {
        // 默认级联构建
        if (cascadeCreateSet.size() == 0 && cascadeCreatePattern.size() == 0) {
            return true;
        } else {
            return super.isCascadeCreate(key);
        }
    }

    /**
     * 构建器
     */
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
            this.counter = new StringCounter(0);
            this.mockConfig = config;
        }

        public void counterClear() {
            counter.clearAll();
        }

        public MockDataConfig getMockConfig() {
            return mockConfig;
        }

        /**
         * mock数据
         *
         * @param classGrc 目标类信息
         * @param <T>      目标类泛型
         * @return 目标类
         */
        public <T> T mockClass(ClassGrc classGrc) {
            return mockSrc(new MockSrc(classGrc, null));
        }

        /**
         * mock源数据，最终调用方法
         *
         * @param src 源数据
         * @return 源数据对应数据
         */
        public <T> T mockSrc(MockSrc src) {
            Class<T> cla = (Class<T>) src.getClassGrc().getTarget();
            // 从数据池中判断是否有值
            Object o = getObjectFromDataPool(cla, null);
            if (o == null) {
                Class<?> componentClass = getComponentClass(cla);
                // 是否是忽略类
                if (isAllowedClass(componentClass)) {
                    T t;
                    // 检测是否存在自定义构建器
                    DataCreator<?> dataCreator = getDataCreator(cla);
                    // 不存在则尝试新建对象
                    if (dataCreator == null) {
                        t = newInstance(cla);
                        String claKey = NamingUtil.getKeyName(componentClass);
                        // 如果此类允许级联构造则进行mock
                        if (isCascadeCreate(claKey)) {
                            mock(t, src.getClassGrc());
                        }
                    } else {
                        t = (T) dataCreator.mock(src, this);
                    }
                    return t;
                }
                return null;
            } else {
                return (T) o;
            }
        }

        /**
         * mock数据，最终mock类
         *
         * @param t        目标类实例
         * @param classGrc 目标类信息
         * @param <T>      目标类泛型
         * @return 目标类
         */
        private <T> T mock(T t, ClassGrc classGrc) {
            Class<T> cla = (Class<T>) classGrc.getTarget();
            // 判断是否是忽略类
            if (isAllowedClass(getComponentClass(cla))) {
                // 按照类型进行填装
                if (cla.isEnum()) {
                    return t;
                } else if (cla.isArray()) {
                    fillArray(t, cla);
                } else {
                    try {
                        fillField(t, classGrc);
                    } catch (NoSuchFieldException | IllegalAccessException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            return t;
        }

        /**
         * 填充数组
         *
         * @param o   数组引用对象
         * @param cla 数组类型
         */
        private void fillArray(Object o, Class<?> cla) {
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
                        int nextSize = mockConfig.getArraySize(getComponentClass(realClaDepp));
                        arr = Array.newInstance(realClaDepp, nextSize);
                    }
                    fillArray(arr, componentType);
                    Array.set(o, i, arr);
                } else {
                    Array.set(o, i, mockClass(ReflectUtil.getClassGrc(componentType)));
                }
            }
        }

        /**
         * 为对象填充属性
         *
         * @param t        目标对象
         * @param classGrc 目标对象类信息
         */
        private void fillField(Object t, ClassGrc classGrc) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
            // 对象为空则忽略
            if (t == null) {
                return;
            }
            Class<?> cla = classGrc.getTarget();
            // 获取泛型映射表
            Map<String, ClassGrc> genericsMap = ReflectUtil.getGenericsMap(classGrc);
            // 遍历属性进行填充
            List<Field> allFields = FieldUtil.getAllFields(cla);
            for (Field field : allFields) {
                // 获取属性的完整类型
                FieldGrc fieldGrc = FieldUtil.getFieldGrc(field, genericsMap);
                // 获取属性的当前类型
                Class<?> fieldCla = fieldGrc.getTarget();
                // 获取属性的元素类型
                Class<?> componentClass = getComponentClass(fieldCla);
                // 判断此属性是否支持构建
                if (isAllowedField(t, field) && isAllowedClass(componentClass)) {
                    String fieldKey = NamingUtil.getKeyName(field);
                    String claKey = NamingUtil.getKeyName(componentClass);
                    // 判定属性引用次数是否已到达最大值
                    int max = getCreatingDepth(fieldKey, claKey);
                    if (counter.getCount(fieldKey) < max) {
                        // 设定可访问
                        boolean oldAcc = field.isAccessible();
                        if (!oldAcc) {
                            field.setAccessible(true);
                        }
                        Object o;
                        try {
                            // 获取属性可能存在的对象
                            o = field.get(t);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                        // 如果对象已存在则判断是否重新创建
                        if (isAcceptField(o, field, fieldCla)) {
                            // 从属性数据池中获取数据
                            o = getObjectFromDataPool(fieldCla, field.getName());
                            if (o == null) {
                                int count = counter.count(fieldKey);
                                // 判定类是否存在构造器
                                DataCreator<?> configCreator = getDataCreator(fieldGrc);
                                // 构造器存在则使用构造器进行构造
                                if (configCreator != null) {
                                    o = configCreator.mock(new MockSrc(fieldGrc, null), this);
                                } else {
                                    o = newInstance(fieldCla);
                                    // 判断属性是否允许级联构造
                                    if (fieldCla.isArray()) {
                                        fillArray(o, fieldCla);
                                    } else if (isCascadeCreate(claKey) || isCascadeCreate(fieldKey)) {
                                        // 进行级联构造
                                        fillField(o, fieldGrc);
                                    }
                                }
                                counter.setCount(fieldKey, count - 1);
                            }
                            if (o != null) {
                                try {
                                    field.set(t, o);
                                } catch (IllegalAccessException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                        // 还原权限
                        if (!oldAcc) {
                            field.setAccessible(false);
                        }
                    }
                }
            }
        }

        private int getCreatingDepth(String fieldKey, String claKey) {
            int defaultDepth = mockConfig.getCreatingDepth(null);
            int fieldDepth = mockConfig.getCreatingDepth(fieldKey);
            if (fieldDepth != defaultDepth) {
                return fieldDepth;
            }
            return mockConfig.getCreatingDepth(claKey);
        }

        /**
         * 获取元素类，数组获取元素对应的类，其他类则返回本身
         */
        private Class<?> getComponentClass(Class<?> arrayClass) {
            while (arrayClass.isArray()) {
                arrayClass = arrayClass.getComponentType();
            }
            return arrayClass;
        }

        private boolean isCascadeCreate(String key) {
            return MockDataCreator.this.isCascadeCreate(key) && mockConfig.isCascadeCreate(key);
        }

        private boolean isAllowedField(Object target, Field field) {
            return MockDataCreator.this.isAllowedField(target, field) && mockConfig.isAllowedField(target, field);
        }

        private boolean isAcceptField(Object fieldObject, Field field, Class<?> fieldCla) {
            if (mockConfig.getFieldOptions() == 0) {
                return true;
            }
            int options = (fieldObject == null ? FieldOption.ALLOWED_NULL : FieldOption.ALLOWED_NOTNULL)
                    | (fieldCla.isPrimitive() ? FieldOption.ALLOWED_PRIMITIVE : FieldOption.ALLOWED_CLASS);
            return mockConfig.acceptFieldOption(options);
        }

        private boolean isAllowedClass(Class<?> cla) {
            return MockDataCreator.this.isAllowedClass(cla) && mockConfig.isAllowedClass(cla);
        }

        /**
         * 创建新实例
         *
         * @param cla 目标类
         * @param <T> 目标类
         * @return 实例对象
         */
        public <T> T newInstance(Class<T> cla, Object... params) {
            InstanceCreator<T> instanceCreator = getInstanceCreator(cla);
            // 实例构造器不存在时，尝试进行参数构造
            if (instanceCreator == null) {
                if (cla.isArray()) {
                    return (T) Array.newInstance(cla.getComponentType(), mockConfig.getArraySize(getComponentClass(cla)));
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
         * 获取实例化构造器
         *
         * @param cla 目标类
         */
        public <T> InstanceCreator<T> getInstanceCreator(Class<T> cla) {
            InstanceCreator<T> instanceCreator = mockConfig.getInstanceCreator(cla);
            if (instanceCreator == null) {
                instanceCreator = MockDataCreator.this.getInstanceCreator(cla);
            }
            return instanceCreator;
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
                creator = MockDataCreator.this.getDataCreator(key);
            }
            return creator;
        }

        /**
         * 获取接口构造器
         *
         * @param cla 目标类
         */
        public DataCreator<?> getInterfaceCreator(Class<?> cla) {
            DataCreator<?> creator = mockConfig.getInterfaceValue(cla);
            if (creator == null) {
                creator = MockDataCreator.this.getInterfaceValue(cla);
            }
            return creator;
        }

        /**
         * 从数据池获取随机数据
         *
         * @param cl  目标类
         * @param key 目标key
         * @return 随机数据
         */
        public Object getObjectFromDataPool(Class<?> cl, String key) {
            Object o = mockConfig.randomDataFromDataPool(cl, key);
            if (o == null) {
                o = MockDataCreator.this.randomDataFromDataPool(cl, key);
            }
            return o;
        }

        /**
         * 获取数据构造器
         *
         * @param cla 目标类
         * @return 目标类型对应的数据构造器
         */
        public DataCreator<?> getDataCreator(Class<?> cla) {
            String key = NamingUtil.getKeyName(cla);
            DataCreator<?> creator = getDataCreator(key);
            if (creator == null) {
                if (cla.isEnum()) {
                    // 对枚举类进行特殊操作
                    return getDataCreator(NamingUtil.getKeyName(cla.getSuperclass()));
                } else {
                    // 尝试接口获取
                    LOOP_while:
                    while (cla != null) {
                        if (cla.isInterface()) {
                            return getInterfaceCreator(cla);
                        }
                        Class<?>[] claInterfaces = cla.getInterfaces();
                        for (Class<?> anInterface : claInterfaces) {
                            creator = getInterfaceCreator(anInterface);
                            if (anInterface != null) {
                                break LOOP_while;
                            }
                        }
                        cla = cla.getSuperclass();
                    }
                }
            }
            return creator;
        }

        /**
         * 获取数据构造器
         *
         * @param fieldGrc 目标类型
         * @return 目标类型对应的数据构造器
         */
        public DataCreator<?> getDataCreator(FieldGrc fieldGrc) {
            // 优先从配置中获取属性构造器
            DataCreator<?> creator = getDataCreator(NamingUtil.getKeyName(fieldGrc.getField()));
            if (creator != null) {
                return creator;
            }
            // 获取属性类构造器
            Class<?> cla = fieldGrc.getTarget();
            return getDataCreator(cla);
        }
    }
}
