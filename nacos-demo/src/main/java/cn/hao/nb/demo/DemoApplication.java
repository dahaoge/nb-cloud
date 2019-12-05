package cn.hao.nb.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

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
        log.info("\033[1;93;32m【spring start up ok!!】\033[m");
//        }catch (Exception e){
//            log.error("服务xxx-support启动报错", e);
//        }
    }

}
