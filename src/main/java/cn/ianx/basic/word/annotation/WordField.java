package cn.ianx.basic.word.annotation;

import java.lang.annotation.*;

/**
 * 标注一个用于写World模板的数据实体
 *
 * @author gengz
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WordField {

    /**
     * 指定Word文件模板中需要替换的属性。如未指定，则取标注的属性的名称
     * @return
     */
    String fieldName() default "";

    /**
     * 是否忽略当前属性，默认为false
     * @return
     */
    boolean ignore() default false;

}
