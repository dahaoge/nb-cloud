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
    listDeviceByDeptIdAndType("拉取设备信息", "/ydgl/cm/device/listByDeptId"),
    totalStatisticsByDate("按天统计概览数据", "/ydgl/cm/totalStatisticsByDate"),
    getUnitOutputRankByMonth("单位产能比排名", "/ydgl/cm/getUnitOutputRankByMonth"),
    statisticsElecConsumptionByMonth("按照月份周期统计用电量", "/ydgl/elecConsumption/statisticsByMonth"),
    statisticsElecConsumptionByDay("按照天统计用电量", "/ydgl/elecConsumption/statisticsByDay"),
    guessMonthFee("按月份电费估算", "/ydgl/elecFee/guessMonthFee"),
    totalStatisticsLoadByTimeRange("按照时间周期统计负荷", "/ydgl/elecLoad/totalStatisticsByTimeRange"),
    getLoadList("按照时间周期获取负荷曲线", "/ydgl/elecLoad/getLoadList"),
    statisticsLoadByDay("日负荷分析", "/ydgl/elecLoad/statisticsByDay"),
    statisticsLoadByMonth("月负荷分析", "/ydgl/elecLoad/statisticsByMonth");


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
}