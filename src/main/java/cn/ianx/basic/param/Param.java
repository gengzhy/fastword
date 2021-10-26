package cn.ianx.basic.param;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 请求参数
 */
public class Param {
    // 请求地址
    private String host;
    // 请求IP地址
    private String ipAddr;
    // 请求端口号
    private int port;
    // 请求方式
    private String method;
    // 请求参数集合
    private List<Attr> attrs;

    private LocalDateTime ts;

    /**
     * 当前线程请求
     */
    private HttpServletRequest request;

    /**
     * 当前线程响应
     */
    private HttpServletResponse response;

    public Param() {
    }

    public Param(String host, String ipAddr, String method, int port, List<Attr> attrs,
                 HttpServletRequest request, HttpServletResponse response) {
        this.host = host;
        this.ipAddr = ipAddr;
        this.method = method;
        this.port = port;
        this.attrs = attrs;
        this.request = request;
        this.response = response;
    }

    public Param ofTs(LocalDateTime ts) {
        this.ts = Optional.ofNullable(ts).orElse(LocalDateTime.now());
        return this;
    }

    public Param ofRequest(HttpServletRequest request) {
        this.request = request;
        return this;
    }

    public Param ofResponse(HttpServletResponse response) {
        this.response = response;
        return this;
    }

    /**
     * 属性集合
     * @param <R>
     */
    public static class Attr<R> {
        String key;
        R value;
        public Attr(String key, R value) {
            this.key = key;
            this.value = value;
        }
        public R get() {
            return this.value;
        }

        @Override
        public String toString() {
            return "Attr{" +
                    "key='" + key + '\'' +
                    ", value=" + value +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Param{" +
                "host='" + host + '\'' +
                ", ipAddr='" + ipAddr + '\'' +
                ", port=" + port +
                ", method='" + method + '\'' +
                ", attrs=" + attrs +
                ", ts=" + ts +
                ", request=" + request +
                ", response=" + response +
                '}';
    }

    public String getHost() {
        return host;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public int getPort() {
        return port;
    }

    public String getMethod() {
        return method;
    }

    public List<Attr> getAttrs() {
        return attrs;
    }

    public LocalDateTime getTs() {
        return ts;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }
}
