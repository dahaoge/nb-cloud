package cn.hao.nb.cloud.ydglMock.controller;

import cn.hao.nb.cloud.common.entity.Rv;
import cn.hao.nb.cloud.ydglExternalApi.entity.DemandRespMonitor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Auther: hao
 * @Date: 2020/5/25 21:48
 * @Description:
 */
@Api(description = "需求响应接口")
@Slf4j
@RestController
@RequestMapping("/demandResp")
public class DemandRespController {

    @ApiOperation(value = "实时监测数据", notes = "实时监测数据" +
            "\n")
    @GetMapping("/monitor")
    public Rv<DemandRespMonitor> getDemandRespMonitorData(
            @ApiParam(value = "组织机构id", name = "deptId", required = false) @RequestParam String deptId,
            @ApiParam(value = "日期", name = "time", required = false) @RequestParam Date time) {
        return Rv.getInstance(DemandRespMonitor.createMockData());
    }
}
