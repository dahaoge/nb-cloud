package cn.hao.nb.cloud.ydgl.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Auther: hao
 * @Date: 2020/4/11 09:34
 * @Description:
 */
@Api(description = "没啥用")
@Slf4j
@RestController
@RequestMapping("/temp")
public class TestController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/testRestTemplate")
    public String testRestTemplate() {
        return restTemplate.getForObject("http://nb-auth:/temp/test/hao", String.class);
    }

    @GetMapping("/testHttp")
    public String testHttp() {
        Object result = new RestTemplate().getForObject("http://192.168.31.9:10090/cm/getAllEnum", Object.class);
        return JSON.toJSONString(result);
    }
}
