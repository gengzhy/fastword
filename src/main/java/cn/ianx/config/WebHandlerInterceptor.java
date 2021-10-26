package cn.ianx.config;

import cn.ianx.basic.param.Param;
import cn.ianx.basic.holder.ContextHolder;
import cn.ianx.basic.param.ParamBuilder;
import cn.ianx.util.IPUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author gengz
 */
public class WebHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 加入缓存
        this.buildRequestData(request, response);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 清空缓存
        ContextHolder.clear();
    }

    private void buildRequestData(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String ip = IPUtils.getRealIP(request);
        int port = request.getLocalPort();

        Enumeration<String> attributeNames = request.getParameterNames();
        List<Param.Attr> attrs = new ArrayList<>();
        while (attributeNames.hasMoreElements()) {
            String key = attributeNames.nextElement();
            Object value = request.getParameter(key);
            attrs.add(new Param.Attr(key, value));
        }
        ContextHolder.set(ParamBuilder.create()
                .ofHost(url)
                .ofIpAddr(ip)
                .ofMethod(method)
                .ofPort(port)
                .ofaAtrs(attrs)
                .build()
                .ofRequest(request)
                .ofResponse(response));
    }
}
