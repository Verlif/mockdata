package idea.verlif.mock.data.example;

import com.alibaba.fastjson2.JSONArray;
import idea.verlif.mock.data.config.FieldDataPool;
import idea.verlif.mock.data.exception.MockDataException;
import idea.verlif.parser.ParamParserService;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * Properties文件数据池<br/>
 * 解析的格式如下：<br/>
 * string#name|nickname=["小明", "小红", "小张", "小丽"]<br/>
 * 这里#以前的表示类型，除了八个基本类型外，都要填写全类名。
 * #后面的是属性名，如果不指定属性名，则可以没有#号<br/>
 * =后面的是数据池，使用英文,分隔开。
 */
public class PropertiesDataPool extends FieldDataPool {

    private static final char SIGN = '#';
    private static final String SPLIT = "#";
    private static final String SPLIT_OR = "\\|";

    private final ParamParserService pps;

    public PropertiesDataPool() {
        pps = new ParamParserService();
    }

    public void load(String filepath) throws IOException {
        load(new File(filepath));
    }

    public void load(File file) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader(file));
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            Class<?> type = parseClass(key.substring(0, key.indexOf(SIGN)));
            PatternValues<?> pv = parsePV(key, value);
            if (pv != null) {
                addPatternValues(type, pv);
            }
        }
    }

    @Override
    public void addPatternValues(Class<?> cl, PatternValues<?> pv) {
        super.addPatternValues(cl, pv);
        if (cl.isPrimitive()) {
            super.addPatternValues(getPackClass(cl), pv);
        }
    }

    private <T> PatternValues<T> parsePV(String pvStr, String valuesStr) {
        String[] split = pvStr.split("#");
        if (split.length > 0) {
            String pkgName = split[0];
            Class<T> type = (Class<T>) getPackClass(parseClass(pkgName));
            // 解析数据
            T[] values = parseData(type, valuesStr);
            PatternValues<T> pv = new PatternValues<>();
            if (split.length > 1) {
                // 获取解析属性名
                String[] names = split[1].split(SPLIT_OR);
                for (String name : names) {
                    pv.values(values, name, Pattern.CASE_INSENSITIVE);
                }
            }
            return pv;
        }
        return null;
    }

    private Class<?> parseClass(String pkgName) {
        switch (pkgName.toLowerCase()) {
            case "int":
                return int.class;
            case "short":
                return short.class;
            case "long":
                return long.class;
            case "byte":
                return byte.class;
            case "char":
                return char.class;
            case "boolean":
                return boolean.class;
            case "float":
                return float.class;
            case "double":
                return double.class;
            case "string":
                return String.class;
        }
        if (pkgName.indexOf('.') == -1) {
            pkgName = "java.lang." + pkgName;
        }
        // 获取类型
        try {
            return Class.forName(pkgName);
        } catch (ClassNotFoundException e) {
            throw new MockDataException(e);
        }
    }

    private Class<?> getPackClass(Class<?> cla) {
        if (cla.isPrimitive()) {
            switch (cla.getSimpleName()) {
                case "int":
                    return Integer.class;
                case "short":
                    return Short.class;
                case "long":
                    return Long.class;
                case "byte":
                    return Byte.class;
                case "char":
                    return Character.class;
                case "boolean":
                    return Boolean.class;
                case "float":
                    return Float.class;
                case "double":
                    return Double.class;
            }
        }
        return cla;
    }

    private <T> T[] parseData(Class<T> type, String values) {
        JSONArray objects = JSONArray.parseArray(values);
        Object[] array = objects.toArray();
        T[] ts = (T[]) Array.newInstance(type, array.length);
        for (int i = 0; i < array.length; i++) {
            Object o = array[i];
            ts[i] = pps.parse(type, o.toString());
        }
        return ts;
    }
}
