package cn.ianx.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class ClassUtils {
    /**
     * 基类
     */
    private static final String OBJECT = "Object";

    /**
     * 获取目标类的所有属性
     *
     * @param cls 目标类
     * @param includeAnn 仅复制该注解标注的属性
     * @param excludeAnn 排除该注解标注的属性
     * @param loadSupper 是否复制父类属性
     * @return
     */
    public static List<Field> getAllFields(Class<?> cls, Class<? extends Annotation> includeAnn,
                                           Class<? extends Annotation> excludeAnn, boolean loadSupper) {
        // 获得本类所有属性
        Set<Field> fields = Arrays.stream(cls.getDeclaredFields()).collect(Collectors.toSet());

        // 获取父类的所有属性
        if (loadSupper && !OBJECT.equals(cls.getSuperclass().getSimpleName())) {
            fields.addAll(Arrays.asList(cls.getSuperclass().getDeclaredFields()));
        }

        // 处理标注注解的属性
        return fields.stream()
                .filter(field -> (includeAnn == null || field.isAnnotationPresent(includeAnn))
                        && !(excludeAnn != null && field.isAnnotationPresent(excludeAnn)))
                .collect(Collectors.toList());
    }

    /**
     * 根据属性名称拿到目标类的Field
     *
     * @param cls 目标类
     * @param fieldName 属性名称
     * @param includeAnn 仅复制该注解标注的属性
     * @param excludeAnn 排除该注解标注的属性
     * @param loadSupper – 是否复制父类属性
     *
     * @return
     */
    public static Field getField(Class<?> cls, String fieldName,
                                 Class<? extends Annotation> includeAnn,
                                 Class<? extends Annotation> excludeAnn,
                                 boolean loadSupper) {
        return ClassUtils.fieldListToMap(getAllFields(cls, includeAnn, excludeAnn, loadSupper)).get(fieldName);
    }

    /**
     * 根据属性名称拿到目标类的Field
     *
     * @param cls 目标类
     * @param fieldName 属性名称
     * @param includeAnn 仅复制该注解标注的属性
     * @param excludeAnn 排除该注解标注的属性
     *
     * @return
     */
    public static Field getField(Class<?> cls, String fieldName,
                                 Class<? extends Annotation> includeAnn,
                                 Class<? extends Annotation> excludeAnn) {
        return getField(cls, fieldName, includeAnn, excludeAnn, true);
    }

    /**
     * 根据属性名称拿到目标类的Field
     *
     * @param cls 目标类
     * @param fieldName 属性名称
     * @return
     */
    public static Field getField(Class<?> cls, String fieldName) {
        return getField(cls, fieldName, null, null, true);
    }

    /**
     * 属性List转换为Map集合
     *
     * @param list
     * @return
     */
    public static Map<String, Field> fieldListToMap(List<Field> list) {
        Map<String, Field> mp = new HashMap<>(list.size());
        list.forEach(f -> mp.put(f.getName(), f));
        return mp;
    }

    /**
     * 获得目标类的所有public修饰的方法
     *
     * @param cls 目标类
     * @return
     */
    public static List<Method> getMethods(Class<?> cls) {
        Set<Method> methods = Arrays.stream(cls.getMethods()).collect(Collectors.toSet());
        if (!OBJECT.equals(cls.getSuperclass().getSimpleName())) {
            methods.addAll(Arrays.asList(cls.getSuperclass().getMethods()));
        }
        return methods.stream().collect(Collectors.toList());
    }

    /**
     * 方法List转换为Map集合
     *
     * @param list
     * @return
     */
    public static Map<String, Method> methodListToMap(List<Method> list) {
        Map<String, Method> mp = new HashMap<>(list.size());
        list.forEach(m -> mp.put(m.getName(), m));
        return mp;
    }

    /**
     * 根据方法名称拿到目标类的方法
     *
     * @param cls 目标类
     * @param methodName 方法名称
     * @return
     */
    public static Method getMethod(Class<?> cls, String methodName) {
        return methodListToMap(getMethods(cls)).get(methodName);
    }

    /**
     * 给实例设置属性值
     *
     * @param instance 实例
     * @param field 属性
     * @param value 需设置的属性值
     * @param <R>
     * @return
     */
    public <R> R setFieldValue(R instance, Field field, Object value) {
        field.setAccessible(true);
        try {
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(String.format("无法给%s.%s设置属性值.", instance.getClass().getName(), field.getName()));
        }
        return instance;
    }

    /**
     * 首字母转大写
     *
     * @param str
     * @return
     */
    public static String capitalize(String str) {
        return StringUtils.capitalize(str);
    }
 }
