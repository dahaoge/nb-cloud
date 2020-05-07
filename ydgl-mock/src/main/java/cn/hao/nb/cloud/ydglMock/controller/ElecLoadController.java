package cn.hao.nb.cloud.ydglMock.controller;

import cn.hao.nb.cloud.common.entity.Qd;
import cn.hao.nb.cloud.common.entity.Rv;
import cn.hao.nb.cloud.common.penum.EYdglDataCollectionCycle;
import cn.hao.nb.cloud.ydglExternalApi.entity.LoadItem;
import cn.hao.nb.cloud.ydglExternalApi.entity.TimeRangeLoadData;
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

    @ApiOperation(value = "按照时间周期统计负荷", notes = "按照时间周期统计负荷\n实体:TimeRangeLoadData")
    @GetMapping("/totalStatisticsByTimeRange")
    public Rv totalStatisticsByTimeRange(
            @ApiParam(value = "开始时间", name = "startTime", required = true) @RequestParam Date startTime,
            @ApiParam(value = "结束时间", name = "endTime", required = true) @RequestParam Date endTime,
            @ApiParam(value = "组织机构Id", name = "deptId", required = true) @RequestParam String deptId,
            @ApiParam(value = "设备id", name = "deviceId", required = false) @RequestParam(required = false) String deviceId
    ) {
        return Rv.getInstance(
                new TimeRangeLoadData()
        );
    }

    @ApiOperation(value = "按照时间周期获取负荷曲线", notes = "按照时间周期获取负荷曲线\n" +
            "实体:LoadItem\n" +
            "loadList:负荷采点列表\n" +
            "baseDayLoad:基准日负荷")
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
                                new LoadItem(),
                                new LoadItem()
                        ))
                        .add("baseDayLoad", ElecDesc.kWDesc("基准日"))

        );
    }
}
