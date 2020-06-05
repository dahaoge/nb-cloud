package cn.hao.nb.cloud.ydgl.controller;

import cn.hao.nb.cloud.common.entity.Qd;
import cn.hao.nb.cloud.common.util.CheckUtil;
import cn.hao.nb.cloud.common.util.HttpUtil;
import cn.hao.nb.cloud.common.util.ListUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

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
        RestTemplate http = new RestTemplate();
        String get = "http://localhost:10090/temp/testGet";
        String post = "http://localhost:10090/temp/testPost1";
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        map.add("personName", "pName");
        map.add("phone", "17686688903");
        RequestEntity requestEntity = new RequestEntity(map, null, null);
//        Rv<Person> result=http
//                .exchange(post,
//                        HttpMethod.POST,
//                        requestEntity,
//                        new ParameterizedTypeReference<Rv<Person>>(){},map).getBody();
//        Rv result=http.postForEntity(post,new HttpEntity<MultiValueMap<String,Object>>(map,new HttpHeaders()),Rv.class).getBody();
        Map qd = Qd.create().add("personName", "pName").add("phone", "123");
        Map qd1 = Qd.create().add("personName", "pName").add("phone", "1234").add("id", "aa");
        System.out.println(HttpUtil.httpPostRv(post, qd));
        ;

//        Rv result1=http.getForEntity(preGetParams(get, Qd.create().add("personName","pName").add("phone","123"))
//                ,Rv.class).getBody();
        System.out.println(HttpUtil.httpGetRv(get, qd1));
        return JSON.toJSONString("result1");
    }

    public static String preGetParams(String url, Map params) {
        if (CheckUtil.objIsEmpty(params))
            return url;
        List list = Lists.newArrayList();
        params.keySet().forEach(key -> {
            list.add(key.toString().concat("=".concat(params.get(key).toString())));
        });
        String result = url.concat("?".concat(ListUtil.join(list, "&")));
        System.out.println(result);
        return result;
    }

}
