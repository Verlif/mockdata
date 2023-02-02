package idea.verlif.mock.data.util;

import idea.verlif.mock.data.domain.SFunction;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 反射方法
 *
 * @author Verlif
 */
public class ReflectUtil {

    private static final Pattern RETURN_TYPE_PATTERN = Pattern.compile("\\(.*\\)L(.*);");
    private static final Pattern PARAMETER_TYPE_PATTERN = Pattern.compile("\\((.*)\\).*");

    private static final Map<SFunction<?, ?>, Field> FUNCTION_FIELD_MAP = new HashMap<>();
    private static final Map<SFunction<?, ?>, Method> FUNCTION_METHOD_MAP = new HashMap<>();

    /**
     * 获取类的所有属性
     *
     * @param cla 目标类
     * @return 目标类的属性列表，包括父类
     */
    public static List<Field> getAllFields(Class<?> cla) {
        List<Field> fields = new ArrayList<>();
        do {
            Collections.addAll(fields, cla.getDeclaredFields());
            cla = cla.getSuperclass();
        } while (cla != null);
        return fields;
    }

    /**
     * 获取类的泛型列表
     *
     * @param cla 目标类
     * @return 泛型列表
     */
    public static List<Class<?>> getActualTypeOfClass(Class<?> cla) {
        List<Class<?>> list = new ArrayList<>();
        if (cla.isInterface()) {
            Type[] types = cla.getGenericInterfaces();
            for (Type type : types) {
                addActualTypeToList(type, list);
            }
        } else {
            Type type = cla.getGenericSuperclass();
            addActualTypeToList(type, list);
        }
        return list;
    }

    public static void addActualTypeToList(Type type, List<Class<?>> list) {
        for (Type argument : ((ParameterizedType) type).getActualTypeArguments()) {
            String typename = argument.getTypeName();
            int i = typename.indexOf('<');
            if (i > -1) {
                typename = typename.substring(0, i);
            }
            try {
                Class<?> cl = Class.forName(typename);
                list.add(cl);
            } catch (ClassNotFoundException ignored) {
            }
        }
    }

    /**
     * 获取Lambda表达式对应的属性，不调用缓存
     *
     * @param function lambda表达式，对应get方法
     * @param <T>      泛型
     * @return 属性对象
     */
    public static <T> Field getFieldFromLambda(SFunction<T, ?> function) {
        return getFieldFromLambda(function, false);
    }

    /**
     * 获取Lambda表达式对应的属性
     *
     * @param function lambda表达式，对应get方法
     * @param cache    是否允许缓存
     * @param <T>      泛型
     * @return 属性对象
     */
    public static <T> Field getFieldFromLambda(SFunction<T, ?> function, boolean cache) {
        Field field;
        // 获取缓存值
        if (cache) {
            field = FUNCTION_FIELD_MAP.get(function);
            if (field != null) {
                return field;
            }
        }

        SerializedLambda serializedLambda = getSerializedLambda(function);
        // 从Lambda表达式中获取属性名
        String implMethodName = serializedLambda.getImplMethodName();
        // 确保方法是符合规范的get方法，boolean类型是is开头
        if (!implMethodName.startsWith("is") && !implMethodName.startsWith("get")) {
            throw new RuntimeException("It's not the standard name - " + implMethodName);
        }

        // 构建属性名
        int prefixLen = implMethodName.startsWith("is") ? 2 : 3;
        String fieldName = implMethodName.substring(prefixLen);
        String firstChar = fieldName.substring(0, 1);
        fieldName = fieldName.replaceFirst(firstChar, firstChar.toLowerCase());
        // 获取属性
        try {
            field = Class.forName(serializedLambda.getImplClass().replace("/", ".")).getDeclaredField(fieldName);
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        // 更新缓存
        if (cache) {
            FUNCTION_FIELD_MAP.put(function, field);
        }
        return field;
    }


    /**
     * 获取Lambda表达式对应的属性
     *
     * @param function lambda表达式，对应get方法
     * @param cache    是否允许缓存
     * @param <T>      泛型
     * @return 属性对象
     */
    public static <T, R> Method getMethodFromLambda(SFunction<T, R> function, boolean cache) {
        Method method = null;
        // 获取缓存值
        if (cache) {
            method = FUNCTION_METHOD_MAP.get(function);
            if (method != null) {
                return method;
            }
        }
        SerializedLambda serializedLambda = getSerializedLambda(function);

        // 获取方法参数类型
        String expr = serializedLambda.getImplMethodSignature();
        Matcher matcher = PARAMETER_TYPE_PATTERN.matcher(expr);
        if (!matcher.find() || matcher.groupCount() != 1) {
            throw new RuntimeException("Lambda parsing failed!");
        }
        expr = matcher.group(1);
        Class<?>[] claArray;
        if (expr.length() == 0) {
            claArray = new Class[0];
        } else {
            claArray = Arrays.stream(expr.split(";"))
                    .filter(s -> !s.isEmpty())
                    .map(s -> s.replace("L", "").replace("/", "."))
                    .map(s -> {
                        try {
                            return Class.forName(s);
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException("Can not found class - " + s, e);
                        }
                    }).toArray(Class[]::new);
        }
        try {
            Class<?> aClass = Class.forName(serializedLambda.getImplClass().replace("/", "."));
            method = aClass.getMethod(serializedLambda.getImplMethodName(), claArray);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        // 更新缓存
        if (cache) {
            FUNCTION_METHOD_MAP.put(function, method);
        }
        return method;
    }

    /**
     * 获取Lambda表达式的SerializedLambda对象
     * @param function Lambda表达式
     * @return SerializedLambda对象
     */
    public static <T> SerializedLambda getSerializedLambda(SFunction<T, ?> function) {
        // 获取序列化方法
        Method writeReplaceMethod;
        try {
            writeReplaceMethod = function.getClass().getDeclaredMethod("writeReplace");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        // 从序列化方法中获取Lambda表达式信息
        boolean isAccessible = writeReplaceMethod.isAccessible();
        // 如果isAccessible为false则进行设定
        if (!isAccessible) {
            writeReplaceMethod.setAccessible(true);
        }
        SerializedLambda serializedLambda;
        try {
            serializedLambda = (SerializedLambda) writeReplaceMethod.invoke(function);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        // 还原access设定
        if (!isAccessible) {
            writeReplaceMethod.setAccessible(false);
        }
        return serializedLambda;
    }

    /**
     * 通过构造器产生实例对象
     *
     * @param cla    目标类
     * @param params 构造器参数
     * @param <T>    实例类
     * @return 实例对象
     */
    public static <T> T newInstance(Class<T> cla, Object... params) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        // 当没有参数是直接调用无参构造器方法
        if (params.length == 0) {
            return cla.newInstance();
        }
        // 遍历构造器查询对应参数的构造器方法
        Constructor<?>[] constructors = cla.getConstructors();
        LOOP_constructor:
        for (Constructor<?> constructor : constructors) {
            // 如果构造器参数数量相同则进一步验证
            if (constructor.getParameterCount() == params.length) {
                Class<?>[] types = constructor.getParameterTypes();
                // 遍历构造器参数并逐一校验参数类型是否相同
                for (int i = 0; i < types.length; i++) {
                    // 参数不相同则跳过此构造器
                    if (!recalculate(types[i]).isAssignableFrom(params[i].getClass())) {
                        continue LOOP_constructor;
                    }
                }
                return (T) constructor.newInstance(params);
            }
        }
        return null;
    }

    private static Class<?> recalculate(Class<?> cl) {
        switch (cl.getSimpleName()) {
            case "int":
                cl = Integer.class;
                break;
            case "double":
                cl = Double.class;
                break;
            case "float":
                cl = Float.class;
                break;
            case "byte":
                cl = Byte.class;
                break;
            case "short":
                cl = Short.class;
                break;
            case "long":
                cl = Long.class;
                break;
            case "boolean":
                cl = Boolean.class;
                break;
            case "char":
                cl = Character.class;
                break;
            default:
        }
        return cl;
    }
}
