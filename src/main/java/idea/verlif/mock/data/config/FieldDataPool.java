package idea.verlif.mock.data.config;

import idea.verlif.mock.data.domain.SFunction;
import idea.verlif.mock.data.util.ReflectUtil;

import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 属性数据池
 */
public class FieldDataPool {

    private final Map<Class<?>, PatternValues<?>> patternValuesMap;

    public FieldDataPool() {
        this.patternValuesMap = new HashMap<>();
    }

    public void addPatternValues(Class<?> cl, PatternValues<?> pv) {
        patternValuesMap.put(cl, pv);
    }

    public <T> T[] getValues(Class<?> cl) {
        return getValues(cl, "");
    }

    public <T> T[] getValues(Class<?> cl, String key) {
        PatternValues<?> patternValues = patternValuesMap.get(cl);
        if (patternValues != null && patternValues.isMatched(key)) {
            return (T[]) patternValues.getValues();
        }
        return null;
    }

    public <T> PatternValues<T> type(Class<? extends T> cl, String... regexes) {
        PatternValues<T> pv = (PatternValues<T>) patternValuesMap.get(cl);
        if (pv == null) {
            pv = new PatternValues<>();
            patternValuesMap.put(cl, pv);
        }
        return pv.regex(regexes);
    }

    public <T> PatternValues<T> like(Class<? extends T> cl, String... fieldNames) {
        PatternValues<T> pv = type(cl);
        for (String fieldName : fieldNames) {
            pv.regex(".*" + fieldName + ".*", Pattern.CASE_INSENSITIVE);
        }
        return pv;
    }

    public <C, T> PatternValues<T> like(SFunction<C, T> function) {
        Field field = ReflectUtil.getFieldFromLambda(function);
        PatternValues<T> pv = (PatternValues<T>) type(field.getType());
        pv.regex(".*" + field.getName() + ".*", Pattern.CASE_INSENSITIVE);
        return pv;
    }

    public final class PatternValues<T> {

        private final List<Pattern> patterns;

        private T[] values;

        public PatternValues() {
            this.patterns = new ArrayList<>();
        }

        public PatternValues<T> regex(String... regexes) {
            for (String regex : regexes) {
                patterns.add(Pattern.compile(regex));
            }
            return this;
        }

        public PatternValues<T> regex(String regex, int flag) {
            patterns.add(Pattern.compile(regex, flag));
            return this;
        }

        @SafeVarargs
        public final PatternValues<T> values(T... values) {
            if (this.values == null) {
                this.values = values;
            } else {
                int i = this.values.length;
                int size = i + values.length;
                T[] newValues = Arrays.copyOf(this.values, size);
                System.arraycopy(values, 0, newValues, i, values.length);
                this.values = newValues;
            }
            return this;
        }

        public FieldDataPool next() {
            return FieldDataPool.this;
        }

        public boolean isMatched(String str) {
            if (patterns.size() == 0) {
                return true;
            }
            for (Pattern pattern : patterns) {
                if (pattern.matcher(str).matches()) {
                    return true;
                }
            }
            return false;
        }

        public T[] getValues() {
            return values;
        }
    }
}
