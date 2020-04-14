package cn.hao.nb.cloud.ydglMock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Auther: hao
 * @Date: 2020/4/14 10:59
 * @Description:
 */
@Slf4j
@EnableSwagger2
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"cn.hao.nb.cloud"})
public class YdglMockApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(YdglMockApplication.class, args);
            log.info("\033[1;93;32m【Server startup done.】\033[m");
        } catch (Exception e) {
            log.error("服务xxx-support启动报错", e);
        }
    }

}