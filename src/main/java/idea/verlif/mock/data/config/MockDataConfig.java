package idea.verlif.mock.data.config;

import idea.verlif.mock.data.domain.counter.StringCounter;
import idea.verlif.mock.data.util.NamingUtil;
import idea.verlif.reflection.domain.SFunction;
import idea.verlif.reflection.util.FieldUtil;

/**
 * @author Verlif
 */
public class MockDataConfig extends CommonConfig {

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
     * 自动级联构建
     */
    private boolean autoCascade;

    /**
     * 使用setter方法进行参数填充
     */
    private boolean useSetter;

    /**
     * 属性选项值。<br>
     * 用于生成属性值时，选择性忽略。
     */
    private int fieldOptions = FieldOption.ALLOWED_NULL | FieldOption.ALLOWED_NOTNULL | FieldOption.ALLOWED_CLASS | FieldOption.ALLOWED_PRIMITIVE;

    public MockDataConfig copy() {
        MockDataConfig config = new MockDataConfig();
        config.depthCounter = this.depthCounter;
        config.arraySizeCreator = this.arraySizeCreator;
        config.fieldCreatorMap.putAll(this.fieldCreatorMap);
        config.interfaceCreatorMap.putAll(this.interfaceCreatorMap);
        config.instanceCreatorMap.putAll(this.instanceCreatorMap);
        config.autoCascade = this.autoCascade;
        config.cascadeCreateSet.addAll(this.cascadeCreateSet);
        config.cascadeCreatePattern.addAll(this.cascadeCreatePattern);
        config.fieldFilters.addAll(this.fieldFilters);
        config.classFilters.addAll(this.classFilters);
        config.fieldOptions = this.fieldOptions;
        config.fieldDataPool = this.fieldDataPool;

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
        setKeyDepth(NamingUtil.getKeyName(FieldUtil.getFieldFromLambda(function)), depth);
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

    public boolean acceptFieldOption(int fieldOptions) {
        return (this.fieldOptions | fieldOptions) == this.fieldOptions;
    }

    public MockDataConfig appendFieldOption(int fieldOptions) {
        this.fieldOptions |= fieldOptions;
        return this;
    }

    public MockDataConfig blockFieldOption(int fieldOptions) {
        this.fieldOptions &= ~fieldOptions;
        return this;
    }

    public boolean isForceNew() {
        return (fieldOptions & FieldOption.ALLOWED_NOTNULL) > 0;
    }

    public MockDataConfig forceNew(boolean forceNew) {
        // 只考虑变更情况
        if (!isForceNew() && forceNew) {
            appendFieldOption(FieldOption.ALLOWED_NOTNULL);
        } else if (isForceNew() && !forceNew) {
            blockFieldOption(FieldOption.ALLOWED_NOTNULL);
        }
        return this;
    }

    public int getFieldOptions() {
        return fieldOptions;
    }

    public MockDataConfig setFieldOptions(int fieldOptions) {
        this.fieldOptions = fieldOptions;
        return this;
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

    public boolean isUseSetter() {
        return useSetter;
    }

    public void setUseSetter(boolean useSetter) {
        this.useSetter = useSetter;
    }

    /**
     * 使用setter方法进行参数填充
     *
     * @param useSetter 是否使用setter方式进行参数填充
     */
    public MockDataConfig useSetter(boolean useSetter) {
        this.useSetter = useSetter;
        return this;
    }

    /**
     * 查询属性是否级联构造
     *
     * @param key 目标key
     * @return 目标key是否级联构造
     */
    @Override
    public boolean isCascadeCreate(String key) {
        return autoCascade || super.isCascadeCreate(key);
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

}
