package cn.ianx.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class BeanCopyUtils {

    /**
     * 一个实例对象复制属性给另一个目标类
     *
     * @param instances 属性值源实例
     * @param targetClassType 目标类
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> List<R> copyProperties(List<T> instances, Class<R> targetClassType) {
        if (instances == null && targetClassType == null) {
            return null;
        }
        List<R> results = new ArrayList<>(instances.size());
        try {
            instances.forEach(e -> results.add(copyProperties(e, targetClassType)));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return results;
    }

    /**
     * 一个实例对象复制属性给另一个目标类
     *
     * @param instance 属性值源实例
     * @param targetClassType 目标类
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> R copyProperties(T instance, Class<R> targetClassType) {
        if (instance == null && targetClassType == null) {
            return null;
        }
        R targetInstance;
        try {
            targetInstance = targetClassType.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return copyProperties(instance, targetInstance);
    }

    /**
     * 一个实例对象复制属性给另一实例对象
     *
     * @param instance 属性值源实例
     * @param targetInstance 目标实例
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> R copyProperties(T instance, R targetInstance) {
        return copyProperties(instance, targetInstance, null, null);
    }

    /**
     * 一个实例对象复制属性给另一实例对象
     *
     * @param instance 属性值源实例
     * @param targetInstance 目标实例
     * @param includeAnn 仅复制该注解标注的属性
     * @param excludeAnn 排除该注解标注的属性
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> R copyProperties(T instance, R targetInstance,
                                          Class<? extends Annotation> includeAnn,
                                          Class<? extends Annotation> excludeAnn) {
        if (instance == null && targetInstance == null) {
            return null;
        }
        // 拿到目标类
        Class<R> targetClass = (Class<R>) targetInstance.getClass();
        try {
            // 得到源类信息
            BeanInfo beanInfo = Introspector.getBeanInfo(instance.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            Object val;
            Field resultField;
            for (PropertyDescriptor p : propertyDescriptors) {
                Method readMethod = p.getReadMethod();
                if (readMethod == null || (val = readMethod.invoke(instance)) == null || p.getWriteMethod() == null) {
                    continue;
                }
                try {
                    // 拿到目标类的属性
                    resultField = ClassUtils.getField(targetClass, p.getName(), includeAnn, excludeAnn);

                    // 拿到目标类属性的setter方法，给目标类对应属性赋值
                    Method targetSetMethod = ClassUtils.getMethod(targetClass, "set" + ClassUtils.capitalize(resultField.getName()));
                    targetSetMethod.invoke(targetInstance, val);
                } catch (Exception e) {
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return targetInstance;
    }
}
