package cn.hao.nb.cloud.auth.controller;

import cn.hao.nb.cloud.auth.entity.UUserInfo;
import cn.hao.nb.cloud.auth.service.IUUserInfoService;
import cn.hao.nb.cloud.common.entity.Pg;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: hao
 * @Date: 2019-11-22 17:29
 * @Description:
 */
@Api(description = "没啥用")
@Slf4j
@RestController
@RequestMapping("/temp")
public class TempController {

    @Autowired
    IUUserInfoService userInfoService;

    @GetMapping("/test/{str}")
    public String test(@PathVariable String str) {
        return "hellow " + str;
    }

    @GetMapping("/getUserByPhone/{phone}")
    public Object getUserByPhone(@PathVariable String phone) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like(UUserInfo.PHONE, phone);
        return userInfoService.page(Pg.page(1, 10), queryWrapper);
    }
}
