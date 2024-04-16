package idea.verlif.mock.data.config.filter.impl;

import idea.verlif.mock.data.config.filter.ClassFilter;
import idea.verlif.mock.data.util.ContainsUtil;
import idea.verlif.mock.data.util.NamingUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 关键词方式的类过滤器
 *
 * @author Verlif
 */
public class ClassKeyFilter implements ClassFilter {

    /**
     * 构造时忽略的类
     */
    private final Set<Class<?>> ignoredClassSet;

    /**
     * 构造时忽略的类正则列表
     */
    private final List<Pattern> ignoredClassPattern;

    public ClassKeyFilter() {
        ignoredClassSet = new HashSet<>();
        ignoredClassPattern = new ArrayList<>();
    }

    /**
     * 增加忽略的类
     */
    private void addIgnoredClass(Class<?> cla) {
        ignoredClassSet.add(cla);
    }

    /**
     * 增加忽略的类
     */
    public ClassKeyFilter ignoredClass(Class<?>... clas) {
        for (Class<?> cla : clas) {
            addIgnoredClass(cla);
        }
        return this;
    }

    /**
     * 增加忽略的类key的正则表达
     */
    private void addIgnoredClassRegex(String regex) {
        ignoredClassPattern.add(Pattern.compile(regex));
    }

    /**
     * 增加忽略的类key的正则表达
     * @param regexes 需要忽略的正则表达式列表。此处的参数格式会被用于匹配demo.Person这种key值。<br/>
     *                示例：
     *                <ul>
     *                <li>{@code .*Impl} - 所有的以Impl结尾的类会被忽略。</li>
     *                <li>{@code demo.person.*} - demo.person包下的所有类会被忽略。</li>
     *                </ul>
     */
    public ClassKeyFilter ignoredClassRegex(String... regexes) {
        for (String s : regexes) {
            addIgnoredClassRegex(s);
        }
        return this;
    }

    /**
     * 增加忽略的类key的类包名
     *
     * @param packName 忽略的类key的类包名
     */
    public ClassKeyFilter ignoredClassPackage(String... packName) {
        for (String s : packName) {
            addIgnoredClassPackage(s);
        }
        return this;
    }

    /**
     * 增加忽略的类key的类包名
     *
     * @param packName 忽略的类key的类包名
     */
    private void addIgnoredClassPackage(String packName) {
        addIgnoredClassRegex(packName + ".*" + NamingUtil.KEY_SUFFIX_CLASS);
    }

    @Override
    public boolean accept(Class<?> cla) {
        if (ignoredClassSet.contains(cla)) {
            return false;
        }
        return !ContainsUtil.checkContains(NamingUtil.getKeyName(cla), null, ignoredClassPattern);
    }
}
