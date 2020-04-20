package cn.hao.nb.cloud.ydglMock.controller;

import cn.hao.nb.cloud.ydglExternalApi.entity.ExternalDepartment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: hao
 * @Date: 2020/4/20 15:03
 * @Description:
 */
@RestController
public class EntityController {
    @GetMapping("/getExternalDepartment")
    public ExternalDepartment getExternalDepartment() {
        return null;
    }
}
