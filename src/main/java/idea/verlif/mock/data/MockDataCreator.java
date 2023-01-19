package idea.verlif.mock.data;

import idea.verlif.mock.data.annotation.MockData;
import idea.verlif.mock.data.config.MockDataConfig;
import idea.verlif.mock.data.creator.DataCreator;
import idea.verlif.mock.data.creator.InstanceCreator;
import idea.verlif.mock.data.creator.data.*;
import idea.verlif.mock.data.domain.MockField;
import idea.verlif.mock.data.domain.counter.StringCounter;
import idea.verlif.mock.data.exception.NoMatchedCreatorException;
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
    }

    /**
     * 添加或是替换数据创造器
     *
     * @param creator 数据创造器
     */
    public void addDefaultCreator(DataCreator<?> creator) {
        for (Class<?> cla : creator.types()) {
            do {
                defaultCreatorMap.put(NamingUtil.getKeyName(cla), creator);
                cla = cla.getSuperclass();
            } while (cla != null);
        }
    }

    /**
     * 获取数据构造器
     *
     * @param key 构造器key
     * @return key对应的数据构造器
     */
    public DataCreator<?> getDataCreator(String key) {
        DataCreator<?> creator = config.getDataCreator(key);
        if (creator == null) {
            creator = defaultCreatorMap.get(key);
        }
        return creator;
    }

    /**
     * mock数据
     *
     * @param t   实例类或对象实例
     * @param <T> 目标泛型
     * @return 若参数是类则返回新实例，否则返回对象本身
     */
    public <T> T mock(T t) throws IllegalAccessException {
        Creator creator = new Creator();
        return creator.mock(t, (Class<T>) t.getClass());
    }

    public <T> T mock(Class<T> cla) throws IllegalAccessException {
        Creator creator = new Creator();
        return creator.mockClass(cla);
    }

    private final class Creator {

        private final StringCounter counter;

        public Creator() {
            counter = new StringCounter();
        }

        /**
         * mock数据
         *
         * @param cla    目标类
         * @param params 构造方法参数
         * @param <T>    目标类泛型
         * @return 目标类
         */
        public <T> T mockClass(Class<T> cla, Object... params) throws IllegalAccessException {
            String claKey = NamingUtil.getKeyName(cla);
            if (config.isIgnoredFiled(claKey)) {
                return null;
            }
            // 检测是否存在自定义构建器
            DataCreator<?> dataCreator = getDataCreator(claKey);
            // 不存在则直接mock
            if (dataCreator == null) {
                // 特殊类型处理
                if (cla.isArray()) {
                    int size = config.getArraySize();
                    Class<?> realCla = cla.getComponentType();
                    // 构建数组对象
                    Object o = Array.newInstance(realCla, size);
                    fillArray(o, cla);
                    return (T) o;
                } else if (cla.isEnum()) {
                    // TODO: 枚举类型构建
                }
                T t = newInstance(cla, params);
                // 如果此类允许级联构造则进行mock或者是非java.lang包下的类
                if (config.isCascadeCreate(claKey)) {
                    return mock(t, cla);
                } else {
                    return t;
                }
            } else {
                return (T) dataCreator.mock(null, MockDataCreator.this);
            }
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
            if (config.isIgnoredFiled(NamingUtil.getKeyName(cla))) {
                return null;
            }
            // 数组
            if (cla.isArray()) {
                fillArray(t, cla);
            } else if (cla.isEnum()) {
                // 枚举对象直接返回
                return t;
            } else {
                fillField(t, cla);
            }
            counter.clearAll();
            return t;
        }

        private void fillArray(Object o, Class<?> cla) throws IllegalAccessException {
            // 当前的实际类
            Class<?> realCla = cla.getComponentType();
            // 当前的多维数组
            Object[] arr = (Object[]) o;
            int size = arr.length;
            // 遍历多维数组的最外层
            for (int i = 0; i < arr.length; i++) {
                // 如果多维数组的降维后还是数组
                if (realCla.isArray()) {
                    if (arr[i] != null) {
                        size = ((Object[]) arr[i]).length;
                    }
                    // 进行递归
                    // 当前的实际类
                    Class<?> realClaDepp = realCla.getComponentType();
                    arr[i] = Array.newInstance(realClaDepp, size);
                    fillArray(arr[i], realCla);
                } else {
                    Array.set(o, i, MockDataCreator.this.mock(realCla));
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
            List<Field> allFields = ReflectUtil.getAllFields(cla);
            for (Field field : allFields) {
                // 判断是否忽略
                String key = NamingUtil.getKeyName(field);
                if (config.isIgnoredFiled(key)) {
                    continue;
                }
                MockField mockField = new MockField(field);
                // 获取引用深度
                MockData mockData = mockField.getMock();
                int max = config.getCircleCount();
                if (mockData != null && mockData.circleCount() >= 0) {
                    max = mockData.circleCount();
                }
                // 判定属性引用次数
                if (counter.getCount(key) < max) {
                    Object o;
                    // 判定类是否存在构造器
                    DataCreator<?> configCreator = getDataCreator(key);
                    if (configCreator != null) {
                        o = configCreator.mock(field, MockDataCreator.this);
                    } else {
                        // 级联构造的类则进行递归构造
                        String claKey = NamingUtil.getKeyName(field.getType());
                        // TODO: 自定义构造器没有使用到
                        if (config.isCascadeCreate(claKey)) {
                            o = newInstance(field.getType());
                            counter.count(key);
                            fillField(o, field.getType());
                        } else {
                            o = create(mockField);
                        }
                    }
                    if (o != null) {
                        boolean oldAcc = field.isAccessible();
                        if (!oldAcc) {
                            field.setAccessible(true);
                        }
                        field.set(t, o);
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
        private <T> T newInstance(Class<T> cla, Object... params) {
            InstanceCreator<T> instanceCreator = config.getInstanceCreator(cla);
            // 实例构造器不存在时，尝试进行参数构造
            if (instanceCreator == null) {
                try {
                    return ReflectUtil.newInstance(cla, params);
                } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            } else {
                return instanceCreator.newInstance();
            }
        }

        /**
         * 通过Mock属性创建数据
         *
         * @param field 目标属性
         * @param <T>   目标属性类型
         * @return 属性对应数据
         */
        private <T> T create(MockField field) {
            String key = field.getKey();
            DataCreator<T> creator = config.getDataCreator(key);
            if (creator == null) {
                String tmpKey = NamingUtil.getKeyName(field.getField().getType());
                creator = (DataCreator<T>) defaultCreatorMap.get(tmpKey);
                if (creator == null) {
                    if (config.isIgnoredUnknownField()) {
                        return null;
                    } else {
                        throw new NoMatchedCreatorException(key);
                    }
                }
            }
            return create(field.getField(), creator);
        }

        /**
         * 通过类创建数据
         *
         * @param cla 目标类
         * @param <T> 目标类
         * @return 类对应数据
         */
        private <T> T create(Class<?> cla) {
            String key = NamingUtil.getKeyName(cla);
            DataCreator<T> creator = config.getDataCreator(key);
            if (creator == null) {
                if (config.isIgnoredUnknownField()) {
                    return null;
                } else {
                    throw new NoMatchedCreatorException(key);
                }
            }
            return create(null, creator);
        }

        private <T> T create(Field field, DataCreator<T> creator) {
            return creator.mock(field, MockDataCreator.this);
        }
    }
}
