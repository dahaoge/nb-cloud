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
import java.util.Map;

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
    public Rv<TimeRangeLoadData> totalStatisticsByTimeRange(
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
    public Rv<Map<String, Object>> statisticsByTimeRange(
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

    @ApiOperation(value = "日负荷分析", notes = "日负荷分析\n" +
            "实体:TimeRangeLoadData\n" +
            "实体:LoadItem\n" +
            "loadStatistics:当日负荷统计(TimeRangeLoadData)\n" +
            "currentList:当日负荷列表(LoadItem)\n" +
            "baseDayLoad:基准日负荷\n" +
            "lastList:昨日负荷列表(LoadItem)")
    @GetMapping("/statisticsByDay")
    public Rv<Map<String, Object>> statisticsByDay(
            @ApiParam(value = "取点密度", name = "collectionCycle", required = true) @RequestParam EYdglDataCollectionCycle collectionCycle,
            @ApiParam(value = "时间", name = "day", required = true) @RequestParam Date day,
            @ApiParam(value = "组织机构Id", name = "deptId", required = true) @RequestParam String deptId,
            @ApiParam(value = "设备id", name = "deviceId", required = false) @RequestParam(required = false) String deviceId
    ) {
        return Rv.getInstance(
                Qd.create()
                        .add("loadStatistics", new TimeRangeLoadData())
                        .add("currentList", Lists.newArrayList(
                                new LoadItem(),
                                new LoadItem(),
                                new LoadItem(),
                                new LoadItem()
                        ))
                        .add("lastList", Lists.newArrayList(
                                new LoadItem(),
                                new LoadItem(),
                                new LoadItem(),
                                new LoadItem()
                        ))
                        .add("baseDayLoad", ElecDesc.kWDesc("基准日"))
        );
    }

    @ApiOperation(value = "月负荷分析", notes = "月负荷分析\n" +
            "实体:TimeRangeLoadData\n" +
            "loadStatistics:负荷统计(TimeRangeLoadData)\n" +
            "currentList:按天获取的当月负荷列表(TimeRangeLoadData不需要获取当前值)\n" +
            "lastList:按天获取的上月负荷列表(TimeRangeLoadData不需要获取当前值)")
    @GetMapping("/statisticsByMonth")
    public Rv<Map<String, Object>> statisticsByMonth(
            @ApiParam(value = "月度时间", name = "month", required = true) @RequestParam Date month,
            @ApiParam(value = "组织机构Id", name = "deptId", required = true) @RequestParam String deptId,
            @ApiParam(value = "设备id", name = "deviceId", required = false) @RequestParam(required = false) String deviceId
    ) {
        return Rv.getInstance(
                Qd.create()
                        .add("loadStatistics", new TimeRangeLoadData())
                        .add("currentList", Lists.newArrayList(
                                new TimeRangeLoadData(),
                                new TimeRangeLoadData(),
                                new TimeRangeLoadData(),
                                new TimeRangeLoadData()
                        ))
                        .add("lastList", Lists.newArrayList(
                                new TimeRangeLoadData(),
                                new TimeRangeLoadData(),
                                new TimeRangeLoadData(),
                                new TimeRangeLoadData()
                        ))

        );
    }
}
