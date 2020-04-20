package cn.hao.nb.cloud.ydglMock.controller;

import cn.hao.nb.cloud.common.entity.Qd;
import cn.hao.nb.cloud.common.entity.Rv;
import cn.hao.nb.cloud.common.penum.EYdglDataCollectionCycle;
import cn.hao.nb.cloud.ydglMock.ElecDesc;
import com.google.common.collect.Lists;
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
 * @Date: 2020/4/14 17:13
 * @Description:
 */
@Api(description = "用电负荷接口")
@Slf4j
@RestController
@RequestMapping("/ydgl/elecLoad")
public class ElecLoadController {

    @ApiOperation(value = "按照时间周期统计负荷", notes = "按照时间周期统计负荷")
    @GetMapping("/totalStatisticsByTimeRange")
    public Rv totalStatisticsByTimeRange(
            @ApiParam(value = "开始时间", name = "startTime", required = true) @RequestParam Date startTime,
            @ApiParam(value = "结束时间", name = "endTime", required = true) @RequestParam Date endTime,
            @ApiParam(value = "组织机构Id", name = "deptId", required = true) @RequestParam String deptId,
            @ApiParam(value = "设备id", name = "deviceId", required = false) @RequestParam(required = false) String deviceId
    ) {
        return Rv.getInstance(
                Qd.create()
                        .add("minLoad", ElecDesc.kWDesc("最小"))
                        .add("minLoadTime", ElecDesc.timeDesc("最小负荷"))
                        .add("currentLoad", ElecDesc.kWDesc("当前"))
                        .add("currentLoadTime", ElecDesc.timeDesc("当前负荷"))
                        .add("maxLoad", ElecDesc.kWDesc("最大"))
                        .add("maxLoadTime", ElecDesc.timeDesc("最大负荷"))
        );
    }

    @ApiOperation(value = "按照时间周期获取负荷曲线", notes = "按照时间周期获取负荷曲线")
    @GetMapping("/getLoadList")
    public Rv statisticsByTimeRange(
            @ApiParam(value = "取点密度", name = "collectionCycle", required = true) @RequestParam EYdglDataCollectionCycle collectionCycle,
            @ApiParam(value = "开始时间", name = "startTime", required = true) @RequestParam Date startTime,
            @ApiParam(value = "结束时间", name = "endTime", required = true) @RequestParam Date endTime,
            @ApiParam(value = "组织机构Id", name = "deptId", required = true) @RequestParam String deptId,
            @ApiParam(value = "设备id", name = "deviceId", required = false) @RequestParam(required = false) String deviceId
    ) {
        return Rv.getInstance(
                Qd.create()
                        .add("loadList", Lists.newArrayList(
                                Qd.create()
                                        .add("collectionTime", ElecDesc.timeDesc(""))
                                        .add("load", ElecDesc.kWDesc("")),
                                Qd.create()
                                        .add("collectionTime", ElecDesc.timeDesc(""))
                                        .add("load", ElecDesc.kWDesc("")),
                                Qd.create()
                                        .add("collectionTime", ElecDesc.timeDesc(""))
                                        .add("load", ElecDesc.kWDesc("")),
                                Qd.create()
                                        .add("collectionTime", ElecDesc.timeDesc(""))
                                        .add("load", ElecDesc.kWDesc(""))
                        ))
                        .add("baseDayLoad", ElecDesc.kWDesc("基准日"))

        );
    }
}
