package cn.hao.nb.cloud.ydglMock.controller;

import cn.hao.nb.cloud.common.entity.Rv;
import cn.hao.nb.cloud.ydglExternalApi.entity.ElecConsumptionData;
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
import java.util.List;

/**
 * @Auther: hao
 * @Date: 2020/4/14 16:09
 * @Description:
 */
@Api(description = "电量接口")
@Slf4j
@RestController
@RequestMapping("/ydgl/elecConsumption")
public class ElecConsumptionController {


    @ApiOperation(value = "按照月份周期统计用电量", notes = "按照月份周期统计用电量\n实体:ElecConsumptionData" +
            "\ntime字段:yyyy-MM")
    @GetMapping("/statisticsByMonth")
    public Rv statisticsByMonth(
            @ApiParam(value = "开始时间", name = "startTime", required = true) @RequestParam Date startTime,
            @ApiParam(value = "结束时间", name = "endTime", required = true) @RequestParam Date endTime,
            @ApiParam(value = "组织机构Id", name = "deptId", required = true) @RequestParam String deptId,
            @ApiParam(value = "设备id", name = "deviceId", required = false) @RequestParam(required = false) String deviceId
    ) {
        List list = Lists.newArrayList();
        for (int i = 1; i < 10; i++) {
            list.add(new ElecConsumptionData());
        }
        return Rv.getInstance(list);
    }

    @ApiOperation(value = "按照天统计用电量", notes = "按照天统计用电量\n实体:ElecConsumptionData" +
            "\ntime字段:yyyy-MM-dd")
    @GetMapping("/statisticsByDay")
    public Rv statisticsByDay(
            @ApiParam(value = "开始时间", name = "startTime", required = true) @RequestParam Date startTime,
            @ApiParam(value = "结束时间", name = "endTime", required = true) @RequestParam Date endTime,
            @ApiParam(value = "组织机构Id", name = "deptId", required = true) @RequestParam String deptId,
            @ApiParam(value = "设备id", name = "deviceId", required = false) @RequestParam(required = false) String deviceId
    ) {
        List list = Lists.newArrayList();
        for (int i = 1; i < 31; i++) {
            list.add(new ElecConsumptionData());
        }
        return Rv.getInstance(list);
    }
}
