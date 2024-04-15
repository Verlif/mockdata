package idea.verlif.mock.data.config;

import idea.verlif.reflection.domain.ClassGrc;
import idea.verlif.reflection.domain.SFunction;
import idea.verlif.reflection.util.FieldUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 属性数据池
 */
public class FieldDataPool implements DataPool {

    private static final String EMPTY = "";
    private final Map<Class<?>, PatternValues<?>> patternValuesMap;

    public FieldDataPool() {
        this.patternValuesMap = new HashMap<>();
    }

    public void addPatternValues(Class<?> cl, PatternValues<?> pv) {
        if (patternValuesMap.containsKey(cl)) {
            PatternValues<?> oldPv = patternValuesMap.get(cl);
            oldPv.addPatternValues(pv);
        } else {
            patternValuesMap.put(cl, pv);
        }
    }

    /**
     * 追加属性数据池
     */
    public void appendFieldDataPool(FieldDataPool fieldDataPool) {
        this.patternValuesMap.putAll(fieldDataPool.patternValuesMap);
    }

    public <T> T[] getValues(ClassGrc classGrc) {
        return getValues(classGrc, EMPTY);
    }

    @Override
    public <T> T[] getValues(ClassGrc classGrc, String key) {
        // Map的key只支持包装类型
        Class<?> target = classGrc.getTarget();
        PatternValues<?> patternValues = patternValuesMap.get(target);
        if (patternValues == null) {
            patternValues = patternValuesMap.get(null);
        }
        if (patternValues != null) {
            return (T[]) patternValues.getValues(key);
        }
        return null;
    }

    public <T> PatternValues<T> type(Class<? extends T> cl, T... values) {
        return typeName(cl, null, values);
    }

    public <T> PatternValues<T> typeName(String fieldName, T... values) {
        return typeName(null, fieldName, values);
    }

    public <T> PatternValues<T> typeName(Class<? extends T> cl, String fieldName, T... values) {
        PatternValues<T> pv = (PatternValues<T>) patternValuesMap.get(cl);
        if (pv == null) {
            pv = new PatternValues<>();
            patternValuesMap.put(cl, pv);
        }
        if (values.length == 0) {
            return pv;
        } else if (fieldName == null) {
            return pv.values(values);
        } else {
            return pv.values(values, fieldName);
        }
    }

    public <C, T> PatternValues<T> like(SFunction<C, T> function, T... values) {
        Field field = FieldUtil.getFieldFromLambda(function);
        return likeName((Class<T>) field.getType(), field.getName(), values);
    }

    public <T> PatternValues<T> likeName(String fieldName, T... values) {
        return likeName(null, fieldName, values);
    }

    public <T> PatternValues<T> likeName(Class<? extends T> cl, String fieldName, T... values) {
        PatternValues<T> pv = type(cl);
        pv.values(values, ".*" + fieldName + ".*", Pattern.CASE_INSENSITIVE);
        return pv;
    }

    /**
     * 正则key值数组存储。<br/>
     * 通常是一个类对应一个存储器，key则表示了值数组名称。
     *
     * @param <T> 值类型
     */
    public final class PatternValues<T> {

        private final ArrayList<Pattern> patterns;

        private final ArrayList<T[]> values;

        public PatternValues() {
            this.patterns = new ArrayList<>();
            this.values = new ArrayList<>();
        }

        public PatternValues<T> values(T... values) {
            return values(values, ".*");
        }

        public PatternValues<T> values(T[] values, String... regexes) {
            for (String regex : regexes) {
                patterns.add(Pattern.compile(regex));
                this.values.add(values);
            }
            return this;
        }

        public PatternValues<T> values(T[] values, String regex, int flags) {
            patterns.add(Pattern.compile(regex, flags));
            this.values.add(values);
            return this;
        }

        public synchronized void addPatternValues(PatternValues<?> pv) {
            for (Object[] value : pv.values) {
                this.values.add((T[]) value);
            }
            this.patterns.addAll(pv.patterns);
        }

        public FieldDataPool next() {
            return FieldDataPool.this;
        }

        public boolean isMatched(String str) {
            if (patterns.isEmpty()) {
                return true;
            }
            for (Pattern pattern : patterns) {
                if (pattern.matcher(str).matches()) {
                    return true;
                }
            }
            return false;
        }

        public T[] getValues(String str) {
            for (int i = 0; i < patterns.size(); i++) {
                Pattern pattern = patterns.get(i);
                if (pattern.matcher(str).matches()) {
                    return values.get(i);
                }
            }
            return null;
        }
    }
}
