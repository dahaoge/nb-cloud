package cn.hao.nb.cloud.ydglMock.controller;

import cn.hao.nb.cloud.common.entity.Qd;
import cn.hao.nb.cloud.common.entity.Rv;
import cn.hao.nb.cloud.common.penum.EYdglDataCollectionCycle;
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

    @ApiOperation(value = "用电监控---电流", notes = "用电监控---电流\ndata:{current:'当前电流',timeRange:'按照时间区间和采集密度获取的列表数据'}")
    @GetMapping("/monitor/elecCurrent")
    public Rv electricCurrentMonitor(
            @ApiParam(value = "取点密度", name = "collectionCycle", required = true) @RequestParam EYdglDataCollectionCycle collectionCycle,
            @ApiParam(value = "开始时间", name = "startTime", required = true) @RequestParam Date startTime,
            @ApiParam(value = "结束时间", name = "endTime", required = true) @RequestParam Date endTime,
            @ApiParam(value = "组织机构id", name = "deptId", required = true) @RequestParam String deptId,
            @ApiParam(value = "设备Id", name = "deviceId") @RequestParam(required = false) String deviceId) {
        return Rv.getInstance(
                Qd.create().add("current", Qd.create()
                        .add("directionA", ElecDesc.aDesc("a向"))
                        .add("directionB", ElecDesc.aDesc("b向"))
                        .add("directionC", ElecDesc.aDesc("c向"))
                ).add("timeRange", Lists.newArrayList(
                        Qd.create()
                                .add("directionA", ElecDesc.aDesc("a向"))
                                .add("directionB", ElecDesc.aDesc("b向"))
                                .add("directionC", ElecDesc.aDesc("c向"))
                                .add("collectionTime", ElecDesc.timeDesc("")),
                        Qd.create()
                                .add("directionA", ElecDesc.aDesc("a向"))
                                .add("directionB", ElecDesc.aDesc("b向"))
                                .add("directionC", ElecDesc.aDesc("c向"))
                                .add("collectionTime", ElecDesc.timeDesc("")),
                        Qd.create()
                                .add("directionA", ElecDesc.aDesc("a向"))
                                .add("directionB", ElecDesc.aDesc("b向"))
                                .add("directionC", ElecDesc.aDesc("c向"))
                                .add("collectionTime", ElecDesc.timeDesc("")),
                        Qd.create()
                                .add("directionA", ElecDesc.aDesc("a向"))
                                .add("directionB", ElecDesc.aDesc("b向"))
                                .add("directionC", ElecDesc.aDesc("c向"))
                                .add("collectionTime", ElecDesc.timeDesc("")),
                        Qd.create()
                                .add("directionA", ElecDesc.aDesc("a向"))
                                .add("directionB", ElecDesc.aDesc("b向"))
                                .add("directionC", ElecDesc.aDesc("c向"))
                                .add("collectionTime", ElecDesc.timeDesc(""))
                ))
        );
    }

    @ApiOperation(value = "用电监控---电压", notes = "用电监控---电压\ndata:{current:'当前电压',timeRange:'按照时间区间和采集密度获取的列表数据'}")
    @GetMapping("/monitor/voltage")
    public Rv electricVoltageMonitor(
            @ApiParam(value = "取点密度", name = "collectionCycle", required = true) @RequestParam EYdglDataCollectionCycle collectionCycle,
            @ApiParam(value = "开始时间", name = "startTime", required = true) @RequestParam Date startTime,
            @ApiParam(value = "结束时间", name = "endTime", required = true) @RequestParam Date endTime,
            @ApiParam(value = "组织机构id", name = "deptId", required = true) @RequestParam String deptId,
            @ApiParam(value = "设备Id", name = "deviceId") @RequestParam(required = false) String deviceId) {
        return Rv.getInstance(
                Qd.create().add("current", Qd.create()
                        .add("directionA", ElecDesc.vDesc("a向"))
                        .add("directionB", ElecDesc.vDesc("b向"))
                        .add("directionC", ElecDesc.vDesc("c向"))
                ).add("timeRange", Lists.newArrayList(
                        Qd.create()
                                .add("directionA", ElecDesc.vDesc("a向"))
                                .add("directionB", ElecDesc.vDesc("b向"))
                                .add("directionC", ElecDesc.vDesc("c向"))
                                .add("collectionTime", ElecDesc.timeDesc("")),
                        Qd.create()
                                .add("directionA", ElecDesc.vDesc("a向"))
                                .add("directionB", ElecDesc.vDesc("b向"))
                                .add("directionC", ElecDesc.vDesc("c向"))
                                .add("collectionTime", ElecDesc.timeDesc("")),
                        Qd.create()
                                .add("directionA", ElecDesc.vDesc("a向"))
                                .add("directionB", ElecDesc.vDesc("b向"))
                                .add("directionC", ElecDesc.vDesc("c向"))
                                .add("collectionTime", ElecDesc.timeDesc("")),
                        Qd.create()
                                .add("directionA", ElecDesc.vDesc("a向"))
                                .add("directionB", ElecDesc.vDesc("b向"))
                                .add("directionC", ElecDesc.vDesc("c向"))
                                .add("collectionTime", ElecDesc.timeDesc("")),
                        Qd.create()
                                .add("directionA", ElecDesc.vDesc("a向"))
                                .add("directionB", ElecDesc.vDesc("b向"))
                                .add("directionC", ElecDesc.vDesc("c向"))
                                .add("collectionTime", ElecDesc.timeDesc(""))
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
                Qd.create().add("current", Qd.create()
                        .add("positiveActive", ElecDesc.kWhDesc("正向有功"))
                        .add("positiveReactive", ElecDesc.kWhDesc("正向无功"))
                        .add("reverseActive", ElecDesc.kWhDesc("反向有功"))
                        .add("reverseReactive", ElecDesc.kWhDesc("反向无功"))
                ).add("timeRange", Lists.newArrayList(
                        Qd.create()
                                .add("positiveActive", ElecDesc.kWhDesc("正向有功"))
                                .add("positiveReactive", ElecDesc.kWhDesc("正向无功"))
                                .add("reverseActive", ElecDesc.kWhDesc("反向有功"))
                                .add("reverseReactive", ElecDesc.kWhDesc("反向无功"))
                                .add("collectionTime", ElecDesc.timeDesc("")),
                        Qd.create()
                                .add("positiveActive", ElecDesc.kWhDesc("正向有功"))
                                .add("positiveReactive", ElecDesc.kWhDesc("正向无功"))
                                .add("reverseActive", ElecDesc.kWhDesc("反向有功"))
                                .add("reverseReactive", ElecDesc.kWhDesc("反向无功"))
                                .add("collectionTime", ElecDesc.timeDesc("")),
                        Qd.create()
                                .add("positiveActive", ElecDesc.kWhDesc("正向有功"))
                                .add("positiveReactive", ElecDesc.kWhDesc("正向无功"))
                                .add("reverseActive", ElecDesc.kWhDesc("反向有功"))
                                .add("reverseReactive", ElecDesc.kWhDesc("反向无功"))
                                .add("collectionTime", ElecDesc.timeDesc("")),
                        Qd.create()
                                .add("positiveActive", ElecDesc.kWhDesc("正向有功"))
                                .add("positiveReactive", ElecDesc.kWhDesc("正向无功"))
                                .add("reverseActive", ElecDesc.kWhDesc("反向有功"))
                                .add("reverseReactive", ElecDesc.kWhDesc("反向无功"))
                                .add("collectionTime", ElecDesc.timeDesc("")),
                        Qd.create()
                                .add("positiveActive", ElecDesc.kWhDesc("正向有功"))
                                .add("positiveReactive", ElecDesc.kWhDesc("正向无功"))
                                .add("reverseActive", ElecDesc.kWhDesc("反向有功"))
                                .add("reverseReactive", ElecDesc.kWhDesc("反向无功"))
                                .add("collectionTime", ElecDesc.timeDesc(""))
                ))
        );
    }

    @ApiOperation(value = "电能质量---基本检测", notes = "电能质量---基本检测")
    @GetMapping("/monitor/basic")
    public Rv electricKWHMonitor(
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

}
