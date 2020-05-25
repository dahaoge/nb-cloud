package cn.hao.nb.cloud.ydglMock.controller;

import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.entity.Rv;
import cn.hao.nb.cloud.common.penum.EDateType;
import cn.hao.nb.cloud.common.penum.EErrorCode;
import cn.hao.nb.cloud.ydglExternalApi.entity.ElecBill;
import cn.hao.nb.cloud.ydglExternalApi.entity.ElecMonthFeeGuess;
import cn.hao.nb.cloud.ydglExternalApi.entity.ElecYearFeeStatistics;
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
 * @Date: 2020/5/20 20:04
 * @Description:
 */
@Api(description = "公共接口")
@Slf4j
@RestController
@RequestMapping("/ydgl/elecFee")
public class ElecFeeController {

    @ApiOperation(value = "年度或月度电费账单", notes = "年度或月度电费账单\n实体:ElecBill" +
            "\nEDateType:可选值day|month")
    @GetMapping("/getElecBill")
    public Rv<ElecBill> getElecBill(
            @ApiParam(value = "年份或月份时间", name = "time", required = true) @RequestParam Date time,
            @ApiParam(value = "年|月", name = "dateType", required = true) @RequestParam EDateType dateType,
            @ApiParam(value = "组织机构id", name = "deptId", required = false) @RequestParam(required = false) String deptId
    ) {
//        return Rv.getInstance(
//                new ElecBill()
//        );
        throw NBException.create(EErrorCode.c404, "功能暂未开放");
    }

    @ApiOperation(value = "按年统计各月份平均电价", notes = "按年统计各月份平均电价" +
            "\n实体:ElecYearFeeStatistics")
    @GetMapping("/avgElecFeeByYear")
    public Rv<ElecYearFeeStatistics> avgElecFeeByYear(
            @ApiParam(value = "年份", name = "year", required = true) @RequestParam Date year,
            @ApiParam(value = "组织机构id", name = "deptId", required = false) @RequestParam(required = false) String deptId
    ) {
//        return Rv.getInstance(new ElecYearFeeStatistics());
        throw NBException.create(EErrorCode.c404, "功能暂未开放");
    }

    @ApiOperation(value = "按月份电费估算", notes = "按月份电费估算" +
            "\n实体:ElecMonthFeeGuess")
    @GetMapping("/guessMonthFee")
    public Rv<ElecMonthFeeGuess> guessMonthFee(
            @ApiParam(value = "月份", name = "month", required = true) @RequestParam Date month,
            @ApiParam(value = "组织机构id", name = "deptId", required = false) @RequestParam(required = false) String deptId
    ) {
        return Rv.getInstance(new ElecMonthFeeGuess());
    }
}
