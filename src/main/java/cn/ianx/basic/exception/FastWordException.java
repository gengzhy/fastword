package cn.ianx.basic.exception;

/**
 * 公共异常类
 *
 * @author gengz
 */
public class FastWordException extends RuntimeException {
    public FastWordException() {
        super();
    }

    /**
     * message中请使用"%s"占位符，且与params的参数个人数一致。
     *
     * 尽可能不超过5个参数
     *
     * @param message
     * @param params
     */
    public FastWordException(String message, Object... params) {
        super(params != null && params.length > 0 ? String.format(message, params) : message);
    }

    public FastWordException(String message, Throwable cause) {
        super(message, cause);
    }

    public FastWordException(Throwable cause) {
        super(cause);
    }
}
