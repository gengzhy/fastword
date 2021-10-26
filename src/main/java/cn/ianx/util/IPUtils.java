package cn.ianx.util;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public final class IPUtils {

    private static String UNKNOWN = "unknown";
    private static String LOCALHOST_IPv4 = "127.0.0.1";
    private static String LOCALHOST_IPv6 = "0:0:0:0:0:0:0:1";

    /**
     * 如果网站设置了nginx反向代理
     * 需要在nginx配置文件加上如下配置才能获取用户真实ip
     * proxy_set_header Host $host;
     * proxy_set_header X-Real-IP $remote_addr;
     * proxy_set_header REMOTE-HOST $remote_addr;
     * proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
     * @param request
     * @return
     */
    public static String getRealIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && IPUtils.UNKNOWN.equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || IPUtils.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || IPUtils.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || IPUtils.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || IPUtils.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || IPUtils.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || IPUtils.UNKNOWN.equalsIgnoreCase(ip)) {
            if (IPUtils.LOCALHOST_IPv4.equals(request.getRemoteAddr()) || IPUtils.LOCALHOST_IPv6.equals(request.getRemoteAddr())) {
                // 根据网卡获取真实IP
                InetAddress inert = null;
                try {
                    inert = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip = inert.getHostAddress();
            }
        }
        return ip;
    }
}
