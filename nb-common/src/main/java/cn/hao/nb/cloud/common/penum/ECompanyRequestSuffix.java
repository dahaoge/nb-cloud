package cn.hao.nb.cloud.common.penum;

import cn.hao.nb.cloud.common.entity.PEnum;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @Auther: hao
 * @Date: 2020/4/12 17:18
 * @Description:
 */
public enum ECompanyRequestSuffix implements PEnum<String> {
    loadDept("拉取组织架构信息", "/ydgl/cm/dept/loadAllDisDeptTreeByDeptId"),
    deviceTree("根据组织机构id获取设备树", "/ydgl/cm/device/tree"),
    @Deprecated
    listDeviceByDeptIdAndType("拉取设备信息", "/ydgl/cm/device/listByDeptId"),
    totalStatisticsByDate("按天统计概览数据", "/ydgl/cm/totalStatisticsByDate"),
    getUnitOutputRankByMonth("单位产能比排名", "/ydgl/cm/getUnitOutputRankByMonth"),
    statisticsElecConsumptionByMonth("按照月份周期统计用电量", "/ydgl/elecConsumption/statisticsByMonth"),
    statisticsElecConsumptionByDay("按照天统计用电量", "/ydgl/elecConsumption/statisticsByDay"),
    guessMonthFee("按月份电费估算", "/ydgl/elecFee/guessMonthFee"),
    totalStatisticsLoadByTimeRange("按照时间周期统计负荷", "/ydgl/elecLoad/totalStatisticsByTimeRange"),
    getLoadList("按照时间周期获取负荷曲线", "/ydgl/elecLoad/getLoadList"),
    statisticsLoadByDay("日负荷分析", "/ydgl/elecLoad/statisticsByDay"),
    statisticsLoadByMonth("月负荷分析", "/ydgl/elecLoad/statisticsByMonth"),
    demandRespDailyMonitor("需求响应实时监控数据", "/ydgl/demandResp/dailyMonitor"),
    demandRespResultFx("需求响应调控结果分析", "/ydgl/demandResp/demandRespResultFx"),
    @Deprecated
    getDeviceAlarmMsg("设备告警信息", "/ydgl/cm/getDeviceAlarmMsg"),
    @Deprecated
    getElecBill("年度或月度电费账单", "/ydgl/elecFee/getElecBill"),
    @Deprecated
    avgElecFeeByYear("按年统计各月份平均电价", "/ydgl/elecFee/avgElecFeeByYear"),
    @Deprecated
    powerQualityElectricCurrentMonitor("用电监控---电流", "/ydgl/powerQuality/monitor/elecCurrent"),
    @Deprecated
    powerQualityElectricVoltageMonitor("用电监控---电压", "/ydgl/powerQuality/monitor/voltage"),
    @Deprecated
    powerQualityElectricKWHMonitor("用电监控---示数", "/ydgl/powerQuality/monitor/kwh"),
    @Deprecated
    powerQualityElectricBasicMonitor("电能质量---基本检测", "/ydgl/powerQuality/monitor/basic"),
    @Deprecated
    powerQualityElectricDistortionMonitor("电能质量---畸变", "/ydgl/powerQuality/monitor/distortion"),
    @Deprecated
    powerQualityElectricImbalance("电能质量---不平衡度", "/ydgl/powerQuality/monitor/imbalance"),
    @Deprecated
    powerQualityStatistics("电能质量统计", "/ydgl/powerQuality/statistics");


    private String desc;
    private String path;

    ECompanyRequestSuffix(String desc, String path) {
        this.desc = desc;
        this.path = path;
    }

    @Override
    public String getValue() {
        return name();
    }

    @Override
    public String toString() {
        return name();
    }

    @Override
    public String toChString() {
        return desc;
    }

    public String getPath() {
        return path;
    }

    public static Map<ECompanyRequestSuffix, String> toMap() {
        Map<ECompanyRequestSuffix, String> result = Maps.newHashMap();
        for (ECompanyRequestSuffix item : ECompanyRequestSuffix.values()) {
            result.put(item, item.getPath());
        }
        return result;
    }

    public String toDesc() {
        return this.desc.concat("[").concat(this.path).concat("]");
    }
}