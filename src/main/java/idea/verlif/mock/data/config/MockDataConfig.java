package idea.verlif.mock.data.config;

import idea.verlif.mock.data.creator.DataCreator;
import idea.verlif.mock.data.creator.InstanceCreator;
import idea.verlif.mock.data.domain.SFunction;
import idea.verlif.mock.data.util.NamingUtil;
import idea.verlif.mock.data.util.ReflectUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Verlif
 */
public class MockDataConfig {

    /**
     * 属性填充的循环次数
     */
    private int creatingDepth = 3;

    /**
     * 构建数组时的填充长度
     */
    private int arraySize = 5;

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
     * 构造时忽略的属性
     */
    private final Set<String> ignoredFiledSet;

    /**
     * 构造时忽略的属性正则列表
     */
    private final List<Pattern> ignoredFiledPattern;

    /**
     * 允许构建private关键字
     */
    private int modifiers = Modifier.PRIVATE;

    /**
     * 强制生成新对象
     */
    private boolean forceNew = false;

    public MockDataConfig() {
        fieldCreatorMap = new HashMap<>();
        instanceCreatorMap = new HashMap<>();
        cascadeCreateSet = new HashSet<>();
        cascadeCreatePattern = new ArrayList<>();
        ignoredFiledSet = new HashSet<>();
        ignoredFiledPattern = new ArrayList<>();
    }

    public MockDataConfig copy() {
        MockDataConfig config = new MockDataConfig();
        config.creatingDepth = this.creatingDepth;
        config.arraySize = this.arraySize;
        config.fieldCreatorMap.putAll(this.fieldCreatorMap);
        config.instanceCreatorMap.putAll(this.instanceCreatorMap);
        config.autoCascade = this.autoCascade;
        config.cascadeCreateSet.addAll(this.cascadeCreateSet);
        config.cascadeCreatePattern.addAll(this.cascadeCreatePattern);
        config.ignoredFiledSet.addAll(this.ignoredFiledSet);
        config.ignoredFiledPattern.addAll(this.ignoredFiledPattern);
        config.modifiers = this.modifiers;
        config.forceNew = this.forceNew;

        return config;
    }

    public int getCreatingDepth() {
        return creatingDepth;
    }

    public void setCreatingDepth(int creatingDepth) {
        this.creatingDepth = creatingDepth;
    }

    public MockDataConfig creatingDepth(int creatingDepth) {
        this.creatingDepth = creatingDepth;
        return this;
    }

    public boolean isAllowPrivate() {
        return Modifier.isPrivate(modifiers);
    }

    public void setAllowPrivate(boolean allowPrivate) {
        this.modifiers = allowPrivate ? this.modifiers & Modifier.PRIVATE : this.modifiers ^ Modifier.PRIVATE;
    }

    public boolean isAllowPublic() {
        return Modifier.isPublic(modifiers);
    }

    public void setAllowPublic(boolean allowPublic) {
        this.modifiers = allowPublic ? this.modifiers | Modifier.PUBLIC : this.modifiers ^ Modifier.PUBLIC;
    }

    public boolean isAllowProtect() {
        return Modifier.isProtected(modifiers);
    }

    public void setAllowProtect(boolean allowProtect) {
        this.modifiers = allowProtect ? this.modifiers | Modifier.PROTECTED : this.modifiers ^ Modifier.PROTECTED;
    }

    public boolean isAllowStatic() {
        return Modifier.isStatic(modifiers);
    }

    public void setAllowStatic(boolean allowStatic) {
        this.modifiers = allowStatic ? this.modifiers | Modifier.STATIC : this.modifiers ^ Modifier.STATIC;
    }

    public void setAllowedModifiers(int modifiers) {
        this.modifiers = modifiers;
    }

    /**
     * 添加允许的属性修饰符
     *
     * @param modifiers 属性修饰符
     */
    public MockDataConfig allowedModifiers(int... modifiers) {
        for (int modifier : modifiers) {
            this.modifiers |= modifier;
        }
        return this;
    }

    /**
     * 移除允许的属性修饰符
     *
     * @param modifiers 属性修饰符
     */
    public MockDataConfig blockedModifiers(int... modifiers) {
        for (int modifier : modifiers) {
            this.modifiers -= modifier;
        }
        return this;
    }

    public boolean isAllowedModifier(int mod) {
        return (mod & modifiers) != 0;
    }

    public int getArraySize() {
        return arraySize;
    }

    public void setArraySize(int arraySize) {
        this.arraySize = arraySize;
    }

    public MockDataConfig arraySize(int arraySize) {
        this.arraySize = arraySize;
        return this;
    }

    public boolean isForceNew() {
        return forceNew;
    }

    public void setForceNew(boolean forceNew) {
        this.forceNew = forceNew;
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
    public <T> void addFieldCreator(SFunction<T, ?> function, DataCreator<?> creator) {
        Field field = ReflectUtil.getFieldFromLambda(function, true);
        addFieldCreator(NamingUtil.getKeyName(field), creator);
    }

    /**
     * 添加或替换属性数据创造器
     *
     * @param function 属性获取表达式
     * @param creator  数据创造器
     */
    public <T> MockDataConfig fieldCreator(SFunction<T, ?> function, DataCreator<?> creator) {
        addFieldCreator(function, creator);
        return this;
    }

    /**
     * 添加或替换属性数据创造器
     *
     * @param key     属性key值
     * @param creator 数据创造器
     */
    public void addFieldCreator(String key, DataCreator<?> creator) {
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
    public MockDataConfig fieldCreator(String key, DataCreator<?> creator) {
        addFieldCreator(key, creator);
        return this;
    }

    /**
     * 添加或替换属性数据创造器
     *
     * @param cla     目标类
     * @param creator 数据创造器
     */
    public void addFieldCreator(Class<?> cla, DataCreator<?> creator) {
        fieldCreatorMap.put(
                NamingUtil.getKeyName(cla),
                creator);
    }

    /**
     * 添加或替换属性数据创造器
     *
     * @param creator 数据创造器
     */
    public void addFieldCreator(DataCreator<?> creator) {
        for (Class<?> cla : creator.types()) {
            fieldCreatorMap.put(
                    NamingUtil.getKeyName(cla),
                    creator);
        }
    }

    /**
     * 添加或替换属性数据创造器
     *
     * @param cla     目标类
     * @param creator 数据创造器
     */
    public MockDataConfig fieldCreator(Class<?> cla, DataCreator<?> creator) {
        addFieldCreator(cla, creator);
        return this;
    }

    /**
     * 添加或替换属性数据创造器
     *
     * @param creator 数据创造器
     */
    public MockDataConfig fieldCreator(DataCreator<?> creator) {
        addFieldCreator(creator);
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
    public void addInstanceCreator(InstanceCreator<?> creator) {
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
     * @param cla 需要级联构造的类
     */
    public void addCascadeCreateKey(Class<?> cla) {
        addCascadeCreateKey(NamingUtil.getKeyName(cla));
    }

    /**
     * 添加级联构造的类
     *
     * @param cla 需要级联构造的类
     */
    public MockDataConfig cascadeCreateKey(Class<?> cla) {
        addCascadeCreateKey(cla);
        return this;
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
     * 添加级联构造的属性
     *
     * @param function 需要级联构造的属性
     */
    public MockDataConfig cascadeCreateKey(SFunction<?, ?> function) {
        addCascadeCreateKey(function);
        return this;
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
    public void addCascadeCreatePattern(String regex) {
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
    public void addCascadeCreatePackage(String packName) {
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
    public void removeCascadeCreateKey(SFunction<?, ?> function) {
        removeCascadeCreateKey(NamingUtil.getKeyName(ReflectUtil.getFieldFromLambda(function, true)));
    }

    /**
     * 移除级联构造的key
     *
     * @param key 需要级联构造的key
     */
    public void removeCascadeCreateKey(String key) {
        cascadeCreateSet.remove(key);
    }

    /**
     * 设置自动级联构造标识
     *
     * @param autoCascade 是否自动级联构造
     */
    public void setAutoCascade(boolean autoCascade) {
        this.autoCascade = autoCascade;
    }

    /**
     * 设置自动级联构造标识
     *
     * @param autoCascade 是否自动级联构造
     */
    public MockDataConfig autoCascade(boolean autoCascade) {
        setAutoCascade(autoCascade);
        return this;
    }

    /**
     * 查询属性是否级联构造
     *
     * @param key 目标key
     * @return 目标key是否级联构造
     */
    public boolean isCascadeCreate(String key) {
        return autoCascade || checkContains(key, cascadeCreateSet, cascadeCreatePattern);
    }

    private boolean checkContains(String key, Set<String> stringSet, List<Pattern> patternList) {
        if (stringSet.contains(key)) {
            return true;
        }
        if (patternList.size() != 0) {
            for (Pattern pattern : patternList) {
                if (pattern.matcher(key).matches()) {
                    return true;
                }
            }
        }
        return false;
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
    public <T> MockDataConfig ignoredField(SFunction<T, ?> function) {
        addIgnoredField(function);
        return this;
    }

    /**
     * 增加忽略的属性
     */
    public void addIgnoredField(Class<?> cla) {
        ignoredFiledSet.add(NamingUtil.getKeyName(cla));
    }

    /**
     * 增加忽略的属性
     */
    public MockDataConfig ignoredField(Class<?> cla) {
        addIgnoredField(cla);
        return this;
    }

    /**
     * 增加忽略的属性key的正则表达
     */
    public void addIgnoredFieldRegex(String regex) {
        ignoredFiledPattern.add(Pattern.compile(regex));
    }

    /**
     * 增加忽略的属性key的正则表达
     */
    public MockDataConfig ignoredFieldRegex(String... regex) {
        for (String s : regex) {
            addIgnoredFieldRegex(s);
        }
        return this;
    }

    /**
     * 增加忽略的属性key的类包名
     *
     * @param packName 忽略的属性key的类包名
     */
    public MockDataConfig ignoredFieldPackage(String... packName) {
        for (String s : packName) {
            addIgnoredFieldPackage(s);
        }
        return this;
    }

    /**
     * 增加忽略的属性key的类包名
     *
     * @param packName 忽略的属性key的类包名
     */
    public void addIgnoredFieldPackage(String packName) {
        addIgnoredFieldRegex(packName + ".*" + NamingUtil.KEY_SUFFIX_CLASS);
    }

    /**
     * 判断该属性是否是被忽略的
     *
     * @param key 属性key
     */
    public boolean isIgnoredFiled(String key) {
        return checkContains(key, ignoredFiledSet, ignoredFiledPattern);
    }

    /**
     * 属性是否被允许构建
     *
     * @param field 属性对象
     */
    public boolean isAllowField(Field field) {
        return (field.getModifiers() | modifiers) == modifiers;
    }
}
