package cn.hao.nb.cloud.basic.controller;

import cn.hao.nb.cloud.basic.entity.SysAppVersion;
import cn.hao.nb.cloud.basic.service.ISysAppVersionService;
import cn.hao.nb.cloud.basic.service.ISysDictService;
import cn.hao.nb.cloud.common.entity.Rv;
import cn.hao.nb.cloud.common.util.PEnumUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: hao
 * @Date: 2020/4/22 15:40
 * @Description:
 */
@Api(description = "公共接口")
@Slf4j
@RestController
@RequestMapping("/cm")
public class CmController {

    @Autowired
    PEnumUtil enumUtil;
    @Autowired
    ISysDictService dictService;
    @Autowired
    ISysAppVersionService iSysAppVersionService;

    @ApiOperation(value = "app更新", notes = "app更新")
    @GetMapping("/getCurrentVersion")
    @ResponseBody
    public Rv getCurrentVersion(SysAppVersion appVersion) {
        return Rv.getInstance(iSysAppVersionService.getCurrentVersion(appVersion));
    }

    @ApiOperation(value = "获取所有枚举", notes = "获取所有枚举")
    @GetMapping("/getAllEnum")
    @ResponseBody
    public Rv getAllEnum() {
        return Rv.getInstance(enumUtil.getRtnMap());
    }

    @ApiOperation(value = "获取所有字典", notes = "获取所有字典")
    @GetMapping("/getAllDict")
    @ResponseBody
    public Rv getAllDict() {
        return Rv.getInstance(dictService.dictMap());
    }

}
