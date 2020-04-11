package cn.hao.nb.demo.controller;

import cn.hao.nb.demo.Constant.SecurityProps;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Auther: hao
 * @Date: 2019-11-22 15:32
 * @Description:
 */

@Api(description = "没啥用")
@Slf4j
@RestController
@RequestMapping("/temp")
@RefreshScope
public class TempController {

    @Value("${app.name}")
    private String appName;
    @Autowired
    SecurityProps securityProps;
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/appName")
    public String appName() {
        log.info(JSON.toJSONString(securityProps.getIgnoreUrls()));
        log.info(JSON.toJSONString(securityProps.getSourceClients()));
        return appName;
    }

    @GetMapping("/testRestTemplate")
    public String testRestTemplate() {
        return restTemplate.getForObject("http://auth/temp/test/hao", String.class);
    }

}
