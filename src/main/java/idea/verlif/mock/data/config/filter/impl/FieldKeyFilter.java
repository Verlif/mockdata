package idea.verlif.mock.data.config.filter.impl;

import idea.verlif.mock.data.config.filter.FieldFilter;
import idea.verlif.mock.data.util.ContainsUtil;
import idea.verlif.mock.data.util.NamingUtil;
import idea.verlif.reflection.domain.SFunction;
import idea.verlif.reflection.util.ReflectUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 关键词方式的属性过滤器
 *
 * @author Verlif
 */
public class FieldKeyFilter implements FieldFilter {

    /**
     * 构造时忽略的属性
     */
    private final Set<String> ignoredFiledSet;

    /**
     * 构造时忽略的属性正则列表
     */
    private final List<Pattern> ignoredFiledPattern;

    public FieldKeyFilter() {
        ignoredFiledSet = new HashSet<>();
        ignoredFiledPattern = new ArrayList<>();
    }

    /**
     * 增加忽略的属性
     */
    private <T> void addIgnoredField(SFunction<T, ?> function) {
        ignoredFiledSet.add(NamingUtil.getKeyName(ReflectUtil.getFieldFromLambda(function)));
    }

    /**
     * 增加忽略的属性
     */
    public <T> FieldKeyFilter ignoredField(SFunction<T, ?> function) {
        addIgnoredField(function);
        return this;
    }

    /**
     * 增加忽略的属性
     */
    private void addIgnoredField(Class<?> cla) {
        ignoredFiledSet.add(NamingUtil.getKeyName(cla));
    }

    /**
     * 增加忽略的属性
     */
    public FieldKeyFilter ignoredField(Class<?>... clas) {
        for (Class<?> cla : clas) {
            addIgnoredField(cla);
        }
        return this;
    }

    /**
     * 增加忽略的属性key的正则表达
     */
    private void addIgnoredFieldRegex(String regex) {
        ignoredFiledPattern.add(Pattern.compile(regex));
    }

    /**
     * 增加忽略的属性key的正则表达
     */
    public FieldKeyFilter ignoredFieldRegex(String... regex) {
        for (String s : regex) {
            addIgnoredFieldRegex(s);
        }
        return this;
    }

    @Override
    public boolean accept(Field field) {
        return !ContainsUtil.checkContains(NamingUtil.getKeyName(field), ignoredFiledSet, ignoredFiledPattern)
                && !ContainsUtil.checkContains(NamingUtil.getKeyName(field.getType()), ignoredFiledSet, ignoredFiledPattern);
    }
}
