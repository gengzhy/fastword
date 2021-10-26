package cn.ianx.basic.holder;

import cn.ianx.basic.param.Param;

public class ContextHolder {
    /**
     * 一次请求参数集
     */
    private final static ThreadLocal<Param> ONCE_REQUEST_DATA = new ThreadLocal<>();

    public static void set(Param param) {
        ONCE_REQUEST_DATA.set(param);
    }

    public static Param get() {
        return ONCE_REQUEST_DATA.get();
    }

    public static void clear() {
        ONCE_REQUEST_DATA.remove();
    }
}
