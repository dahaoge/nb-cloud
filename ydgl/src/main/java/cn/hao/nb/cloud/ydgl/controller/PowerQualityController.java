package cn.hao.nb.cloud.ydgl.controller;

import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.entity.Qd;
import cn.hao.nb.cloud.common.entity.Rv;
import cn.hao.nb.cloud.common.penum.ECompanyRequestSuffix;
import cn.hao.nb.cloud.common.penum.EDateType;
import cn.hao.nb.cloud.common.penum.EErrorCode;
import cn.hao.nb.cloud.common.penum.EYdglDataCollectionCycle;
import cn.hao.nb.cloud.ydgl.service.impl.CommonService;
import cn.hao.nb.cloud.ydglExternalApi.entity.*;
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
 * @Date: 2020/4/20 20:00
 * @Description:
 */
@Api(description = "用电负荷接口")
@Slf4j
@RestController
@RequestMapping("/ydgl/powerQuality")
public class PowerQualityController {

    @Autowired
    CommonService commonService;

    @ApiOperation(value = "用电监控---电流", notes = "用电监控---电流\n" +
            "实体:monitorData,CurrentItem")
    @GetMapping("/monitor/elecCurrent")
    public Rv<MonitorData<CurrentItem>> electricCurrentMonitor(
            @ApiParam(value = "取点密度", name = "collectionCycle", required = true) @RequestParam EYdglDataCollectionCycle collectionCycle,
            @ApiParam(value = "开始时间", name = "startTime", required = true) @RequestParam Date startTime,
            @ApiParam(value = "结束时间", name = "endTime", required = true) @RequestParam Date endTime,
            @ApiParam(value = "组织机构id", name = "deptId", required = true) @RequestParam Long deptId,
            @ApiParam(value = "设备Id", name = "deviceId") @RequestParam(required = false) String deviceId) {

        return commonService.sendYdglRequest(
                ECompanyRequestSuffix.powerQualityElectricCurrentMonitor,
                Qd.create()
                        .add("collectionCycle", collectionCycle)
                        .add("startTime", startTime)
                        .add("endTime", endTime)
                        .add("deptId", deptId)
                        .add("deviceId", deviceId)
        );
    }

    @ApiOperation(value = "用电监控---电压", notes = "用电监控---电压\ndata:{current:'当前电压',timeRange:'按照时间区间和采集密度获取的列表数据'}\n" +
            "实体:VoltageItem")
    @GetMapping("/monitor/voltage")
    public Rv<MonitorData<VoltageItem>> electricVoltageMonitor(
            @ApiParam(value = "取点密度", name = "collectionCycle", required = true) @RequestParam EYdglDataCollectionCycle collectionCycle,
            @ApiParam(value = "开始时间", name = "startTime", required = true) @RequestParam Date startTime,
            @ApiParam(value = "结束时间", name = "endTime", required = true) @RequestParam Date endTime,
            @ApiParam(value = "组织机构id", name = "deptId", required = true) @RequestParam Long deptId,
            @ApiParam(value = "设备Id", name = "deviceId") @RequestParam(required = false) String deviceId) {

        return commonService.sendYdglRequest(
                ECompanyRequestSuffix.powerQualityElectricVoltageMonitor,
                Qd.create()
                        .add("collectionCycle", collectionCycle)
                        .add("startTime", startTime)
                        .add("endTime", endTime)
                        .add("deptId", deptId)
                        .add("deviceId", deviceId)
        );
    }

    @ApiOperation(value = "用电监控---示数", notes = "用电监控---示数\ndata:{current:'当前电压',timeRange:'按照时间区间和采集密度获取的列表数据'}")
    @GetMapping("/monitor/kwh")
    public Rv<MonitorData<KWhItem>> electricKWHMonitor(
            @ApiParam(value = "取点密度", name = "collectionCycle", required = true) @RequestParam EYdglDataCollectionCycle collectionCycle,
            @ApiParam(value = "开始时间", name = "startTime", required = true) @RequestParam Date startTime,
            @ApiParam(value = "结束时间", name = "endTime", required = true) @RequestParam Date endTime,
            @ApiParam(value = "组织机构id", name = "deptId", required = true) @RequestParam Long deptId,
            @ApiParam(value = "设备Id", name = "deviceId") @RequestParam(required = false) String deviceId) {

        return commonService.sendYdglRequest(
                ECompanyRequestSuffix.powerQualityElectricKWHMonitor,
                Qd.create()
                        .add("collectionCycle", collectionCycle)
                        .add("startTime", startTime)
                        .add("endTime", endTime)
                        .add("deptId", deptId)
                        .add("deviceId", deviceId)
        );
    }

    @ApiOperation(value = "电能质量---基本检测", notes = "电能质量---基本检测\n" +
            "实体:ElecBasicQuality")
    @GetMapping("/monitor/basic")
    @Deprecated
    public Rv<ElecBasicQuality> electricBasicMonitor(
            @ApiParam(value = "组织机构id", name = "deptId", required = true) @RequestParam Long deptId,
            @ApiParam(value = "设备Id", name = "deviceId") @RequestParam(required = false) String deviceId) {
//        return Rv.getInstance(
//                new ElecBasicQuality()
//        );
        throw NBException.create(EErrorCode.c404, "功能暂未开放");
    }

    @ApiOperation(value = "电能质量---畸变", notes = "电能质量---畸变\n" +
            "实体:ValtageJb")
    @GetMapping("/monitor/distortion")
    @Deprecated
    public Rv<ValtageJb> electricDistortionMonitor(
            @ApiParam(value = "组织机构id", name = "deptId", required = true) @RequestParam Long deptId,
            @ApiParam(value = "设备Id", name = "deviceId") @RequestParam(required = false) String deviceId) {
//        return Rv.getInstance(
//                new ValtageJb()
//        );
        throw NBException.create(EErrorCode.c404, "功能暂未开放");
    }

    @ApiOperation(value = "电能质量---不平衡度", notes = "电能质量---不平衡度\n{distortion:{畸变值},distortionRate:{畸变率}}")
    @GetMapping("/monitor/imbalance")
    @Deprecated
    public Rv electricImbalance(
            @ApiParam(value = "组织机构id", name = "deptId", required = true) @RequestParam Long deptId,
            @ApiParam(value = "设备Id", name = "deviceId") @RequestParam(required = false) String deviceId) {
        throw NBException.create(EErrorCode.noData, "暂时未采集该项目数据");
    }

    @ApiOperation(value = "电能质量统计", notes = "电能质量统计\n实体:VoltageDeviationStatistics")
    @GetMapping("/statistics")
    @Deprecated
    public Rv<VoltageDeviationStatistics> statistics(
            @ApiParam(value = "组织机构id", name = "deptId", required = true) @RequestParam Long deptId,
            @ApiParam(value = "设备Id", name = "deviceId") @RequestParam(required = false) String deviceId,
            @ApiParam(value = "统计时间", name = "time", required = true) @RequestParam Date time,
            @ApiParam(value = "月|日", name = "dateType", required = true) @RequestParam EDateType dateType
    ) {
//        return Rv.getInstance(ElecQualityStatistics.createMockData());
        throw NBException.create(EErrorCode.c404, "功能暂未开放");
    }

}
