package cn.ianx.basic.function;

/**
 * 有返回值的函数式接口
 *
 * @param <R>
 */
@FunctionalInterface
public interface ICallbackFunction<R> {
    R call();
}
