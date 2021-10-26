package cn.ianx.basic.word.model;

import cn.ianx.util.BeanCopyUtils;

import java.io.Serializable;

/**
 * Word数据模板基类
 *
 */
public interface Model extends Serializable {
    default <T extends Model> void copyProperties(T instance) {
        BeanCopyUtils.copyProperties(instance, this);
    }

}
