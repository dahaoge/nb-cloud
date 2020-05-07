package cn.hao.nb.cloud.ydglExternalApi.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: hao
 * @Date: 2020/5/6 20:22
 * @Description:
 */
@Data
@ApiModel(value = "用电总体统计信息", description = "用电总体统计信息")
public class TotalStatisiticsData implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "今日用电量(kWh)")
    Integer todayConsumption;
    @ApiModelProperty(value = "昨日用电量(kWh)")
    Integer yesterdayConsumption;
    @ApiModelProperty(value = "当前负荷(kW)")
    Integer currentLoad;
    @ApiModelProperty(value = "当前负荷采集时间")
    Date currentLoadCollectionTime;
    @ApiModelProperty(value = "今日最高负荷(kWh)")
    Integer todayMaxLoad;
    @ApiModelProperty(value = "今日最高负荷采集时间")
    Date todayMaxLoadCollectionTime;
    @ApiModelProperty(value = "今日最低负荷")
    Integer todayMinLoad;
    @ApiModelProperty(value = "今日最低负荷采集时间")
    Date todayMinLoadCollectionTime;
}
