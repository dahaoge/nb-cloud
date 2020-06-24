package cn.hao.nb.cloud.ydgl.controller;

import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.entity.Rv;
import cn.hao.nb.cloud.common.penum.EErrorCode;
import cn.hao.nb.cloud.ydgl.service.impl.CommonService;
import cn.hao.nb.cloud.ydglExternalApi.entity.DemandRespMonitor;
import cn.hao.nb.cloud.ydglExternalApi.entity.DemandRespStatistics;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/ydgl/demandResp")
public class DemandRespController {

    @Autowired
    CommonService commonService;

    @ApiOperation(value = "实时监测数据", notes = "实时监测数据" +
            "\nDemandRespMonitor,DemandResp,LoadItem,DemandRespAlarm")
    @GetMapping("/dailyMonitor")
    public Rv<DemandRespMonitor> getDemandRespMonitorData(
            @ApiParam(value = "组织机构id", name = "deptId", required = false) @RequestParam(required = false) String deptId,
            @ApiParam(value = "日期", name = "day", required = false) @RequestParam(required = false) Date day) {
        throw NBException.create(EErrorCode.noData, "功能完善中,敬请期待");
//        return commonService.sendYdglRequest(
//                ECompanyRequestSuffix.demandRespDailyMonitor,
//                Qd.create()
//                        .add("deptId", deptId)
//                        .add("time", day)
//        );
    }

    @ApiOperation(value = "调控效果分析", notes = "调控效果分析" +
            "\nDemandRespStatistics,DemandRespResultStatisticItem,DemandRespDetailItem,DemandRespDetail")
    @GetMapping("/demandRespResultFx")
    public Rv<DemandRespStatistics> demandRespResultFx(
            @ApiParam(value = "组织机构id", name = "deptId", required = false) @RequestParam String deptId,
            @ApiParam(value = "年", name = "year", required = false) @RequestParam Date year) {
        throw NBException.create(EErrorCode.noData, "功能完善中,敬请期待");
//        return commonService.sendYdglRequest(
//                ECompanyRequestSuffix.demandRespResultFx,
//                Qd.create()
//                        .add("deptId", deptId)
//                        .add("year", year)
//        );
    }
}
