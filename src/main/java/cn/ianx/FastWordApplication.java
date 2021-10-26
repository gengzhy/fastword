package cn.ianx;

import cn.ianx.basic.param.Param;
import cn.ianx.basic.holder.ContextHolder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gengz
 */
@RestController
@SpringBootApplication
public class FastWordApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastWordApplication.class, args);
    }

    @RequestMapping("/hello")
    public String hello() {
        Param param = ContextHolder.get();
        return "hello, 你本次请求信息：" + param;
    }
}
