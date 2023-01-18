package idea.verlif.mock.data.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Verlif
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MockData {

    /**
     * 属性key
     *
     * @return 属性在数据构造器中的key值
     */
    String value() default "";

    /**
     * 是否级联构造
     */
    boolean cascadeCreate() default true;

    /**
     * 属性填充的循环次数
     *
     * @return 循环次数
     */
    int circleCount() default -1;

    /**
     * 忽略此属性
     */
    boolean ignored() default false;
}
