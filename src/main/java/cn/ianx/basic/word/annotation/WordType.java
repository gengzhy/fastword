package cn.ianx.basic.word.annotation;

import java.lang.annotation.*;

/**
 * 标注一个用于写World模板的数据实体
 *
 * @author gengz
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface WordType {

    /**
     * 实体名称，如果指定值，则以当前值作为写出Word的文件名
     * 如未指定，则取当前实体类的名称进行写出
     *
     * @return
     */
    String value() default "";
}
