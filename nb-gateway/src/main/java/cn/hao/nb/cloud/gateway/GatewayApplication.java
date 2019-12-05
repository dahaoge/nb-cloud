package cn.hao.nb.cloud.gateway;

//import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Auther: hao
 * @Date: 2019-11-30 09:49
 * @Description:
 */
//@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

    public static void main(String[] args) {
//        try {
        SpringApplication.run(GatewayApplication.class, args);
//        log.info("\033[1;93;32m【Server startup done.】\033[m");
//        }catch (Exception e){
//            log.error("服务xxx-support启动报错", e);
//        }
    }
}
