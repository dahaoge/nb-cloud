package cn.hao.nb.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Auther: hao
 * @Date: 2019-12-04 10:20
 * @Description:
 */
@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
public class DemoApplication {

    public static void main(String[] args) {
//        try {
        SpringApplication.run(DemoApplication.class, args);
        log.info("\033[1;93;32m【Server startup done.】\033[m");
//        }catch (Exception e){
//            log.error("服务xxx-support启动报错", e);
//        }
    }

}
