package cn.ianx.basic.param;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

public class ParamBuilder {
    private ParamBuilder(){}
    // 请求地址
    private String host;
    // 请求IP地址
    private String ipAddr;
    // 请求端口号
    private int port;
    // 请求方式
    private String method;
    // 请求参数集合
    private List<Param.Attr> attrs;

    private LocalDateTime ts;

    /**
     * 当前线程请求
     */
    private HttpServletRequest request;

    /**
     * 当前线程响应
     */
    private HttpServletResponse response;

    /**
     * 创建参数工厂
     * @return
     */
    public static ParamBuilder create() {
        return new ParamBuilder();
    }

    public ParamBuilder ofHost(String host) {
        this.host = host;
        return this;
    }

    public ParamBuilder ofIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
        return this;
    }

    public ParamBuilder ofPort(int port) {
        this.port = port;
        return this;
    }

    public ParamBuilder ofMethod(String method) {
        this.method = method;
        return this;
    }

    public ParamBuilder ofaAtrs(List<Param.Attr> attrs) {
        this.attrs = attrs;
        return this;
    }

    public Param build() {
        return new Param(host, ipAddr, method, port, attrs, null, null)
                .ofTs(LocalDateTime.now());
    }
}
