package cn.hao.nb.cloud.ydgl.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: hao
 * @Date: 2020/4/10 16:00
 * @Description:
 */
@Api(description = "用电管理业务接口")
@Slf4j
@RestController
@RequestMapping("/ydgl")
@RefreshScope
public class YdglController {
}
