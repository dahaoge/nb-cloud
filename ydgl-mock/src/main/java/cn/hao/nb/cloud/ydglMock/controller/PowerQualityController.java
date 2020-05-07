package cn.hao.nb.cloud.ydglMock.controller;

import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.entity.Qd;
import cn.hao.nb.cloud.common.entity.Rv;
import cn.hao.nb.cloud.common.penum.EErrorCode;
import cn.hao.nb.cloud.common.penum.EYdglDataCollectionCycle;
import cn.hao.nb.cloud.ydglExternalApi.entity.CurrentItem;
import cn.hao.nb.cloud.ydglExternalApi.entity.KWhItem;
import cn.hao.nb.cloud.ydglExternalApi.entity.VoltageItem;
import cn.hao.nb.cloud.ydglMock.ElecDesc;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Auther: hao
 * @Date: 2020/4/20 20:00
 * @Description:
 */
@RestController("/powerQuality")
public class PowerQualityController {

    @ApiOperation(value = "用电监控---电流", notes = "用电监控---电流\ndata:{current:'当前电流',timeRange:'按照时间区间和采集密度获取的列表数据'}\n" +
            "实体:CurrentItem")
    @GetMapping("/monitor/elecCurrent")
    public Rv electricCurrentMonitor(
            @ApiParam(value = "取点密度", name = "collectionCycle", required = true) @RequestParam EYdglDataCollectionCycle collectionCycle,
            @ApiParam(value = "开始时间", name = "startTime", required = true) @RequestParam Date startTime,
            @ApiParam(value = "结束时间", name = "endTime", required = true) @RequestParam Date endTime,
            @ApiParam(value = "组织机构id", name = "deptId", required = true) @RequestParam String deptId,
            @ApiParam(value = "设备Id", name = "deviceId") @RequestParam(required = false) String deviceId) {
        return Rv.getInstance(
                Qd.create().add("current", new CurrentItem()
                ).add("timeRange", Lists.newArrayList(
                        new CurrentItem(), new CurrentItem(),
                        new CurrentItem(), new CurrentItem()
                ))
        );
    }

    @ApiOperation(value = "用电监控---电压", notes = "用电监控---电压\ndata:{current:'当前电压',timeRange:'按照时间区间和采集密度获取的列表数据'}\n" +
            "实体:VoltageItem")
    @GetMapping("/monitor/voltage")
    public Rv electricVoltageMonitor(
            @ApiParam(value = "取点密度", name = "collectionCycle", required = true) @RequestParam EYdglDataCollectionCycle collectionCycle,
            @ApiParam(value = "开始时间", name = "startTime", required = true) @RequestParam Date startTime,
            @ApiParam(value = "结束时间", name = "endTime", required = true) @RequestParam Date endTime,
            @ApiParam(value = "组织机构id", name = "deptId", required = true) @RequestParam String deptId,
            @ApiParam(value = "设备Id", name = "deviceId") @RequestParam(required = false) String deviceId) {
        return Rv.getInstance(
                Qd.create().add("current", new VoltageItem()
                ).add("timeRange", Lists.newArrayList(
                        new VoltageItem(),
                        new VoltageItem(),
                        new VoltageItem(),
                        new VoltageItem()
                ))
        );
    }

    @ApiOperation(value = "用电监控---示数", notes = "用电监控---示数\ndata:{current:'当前电压',timeRange:'按照时间区间和采集密度获取的列表数据'}")
    @GetMapping("/monitor/kwh")
    public Rv electricKWHMonitor(
            @ApiParam(value = "取点密度", name = "collectionCycle", required = true) @RequestParam EYdglDataCollectionCycle collectionCycle,
            @ApiParam(value = "开始时间", name = "startTime", required = true) @RequestParam Date startTime,
            @ApiParam(value = "结束时间", name = "endTime", required = true) @RequestParam Date endTime,
            @ApiParam(value = "组织机构id", name = "deptId", required = true) @RequestParam String deptId,
            @ApiParam(value = "设备Id", name = "deviceId") @RequestParam(required = false) String deviceId) {
        return Rv.getInstance(
                Qd.create().add("current", new KWhItem()
                ).add("timeRange", Lists.newArrayList(
                        new KWhItem(),
                        new KWhItem(),
                        new KWhItem(),
                        new KWhItem()
                ))
        );
    }

    @ApiOperation(value = "电能质量---基本检测", notes = "电能质量---基本检测")
    @GetMapping("/monitor/basic")
    public Rv electricBasicMonitor(
            @ApiParam(value = "组织机构id", name = "deptId", required = true) @RequestParam String deptId,
            @ApiParam(value = "设备Id", name = "deviceId") @RequestParam(required = false) String deviceId) {
        return Rv.getInstance(
                Qd.create()
                        .add("voltageDirectionA", ElecDesc.dy("A向"))
                        .add("voltageDirectionB", ElecDesc.dy("B向"))
                        .add("voltageDirectionC", ElecDesc.dy("C向"))
                        .add("voltageDirectionAB", ElecDesc.dy("AB向"))
                        .add("voltageDirectionAC", ElecDesc.dy("AC向"))
                        .add("voltageDirectionBC", ElecDesc.dy("BC向"))
                        .add("currentDirectionA", ElecDesc.dl("A向"))
                        .add("currentDirectionB", ElecDesc.dl("B向"))
                        .add("currentDirectionC", ElecDesc.dl("C向"))
                        .add("currentDirectionZero", ElecDesc.dl("零序"))
                        .add("ygLoad", ElecDesc.gl("有功"))
                        .add("wgLoad", ElecDesc.gl("无功"))
                        .add("szLoad", ElecDesc.gl("视在"))
                        .add("loadFactor", "功率因数")
                        .add("frequency", "频率(带单位)---示例:50Hz|kHz")
        );
    }

    @ApiOperation(value = "电能质量---畸变", notes = "电能质量---畸变\n{distortion:{畸变值},distortionRate:{畸变率}}")
    @GetMapping("/monitor/distortion")
    public Rv electricDistortionMonitor(
            @ApiParam(value = "组织机构id", name = "deptId", required = true) @RequestParam String deptId,
            @ApiParam(value = "设备Id", name = "deviceId") @RequestParam(required = false) String deviceId) {
        return Rv.getInstance(
                Qd.create()
                        .add("distortion",
                                Qd.create()
                                        .add("ua", ElecDesc.dypc("UA"))
                                        .add("ub", ElecDesc.dypc("UB"))
                                        .add("uc", ElecDesc.dypc("UC"))
                                        .add("uab", ElecDesc.dypc("UAB"))
                                        .add("uac", ElecDesc.dypc("UAC"))
                                        .add("ubc", ElecDesc.dypc("UBC"))
                        )
                        .add("distortionRate",
                                Qd.create()
                                        .add("directionA", ElecDesc.bfb("A相畸变率"))
                                        .add("directionB", ElecDesc.bfb("B相畸变率"))
                                        .add("directionC", ElecDesc.bfb("C相畸变率"))
                        )
        );
    }

    @ApiOperation(value = "电能质量---不平衡度", notes = "电能质量---不平衡度\n{distortion:{畸变值},distortionRate:{畸变率}}")
    @GetMapping("/monitor/imbalance")
    public Rv electricImbalance(
            @ApiParam(value = "组织机构id", name = "deptId", required = true) @RequestParam String deptId,
            @ApiParam(value = "设备Id", name = "deviceId") @RequestParam(required = false) String deviceId) {
        throw NBException.create(EErrorCode.noData, "暂时未采集该项目数据");
    }

}
