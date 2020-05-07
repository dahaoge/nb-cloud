package cn.hao.nb.cloud.ydglExternalApi.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hao
 * @Date: 2020/5/7 08:44
 * @Description:
 */
@Data
@ApiModel(value = "用电量统计信息", description = "用电量统计信息")
public class ElecConsumptionData implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "时间字符串,根据接口不同返回[年-月]或[月-日]数据")
    String time;
    @ApiModelProperty(value = "峰值电量(kWh)")
    Integer peak;
    @ApiModelProperty(value = "谷值电量(kWh)")
    Integer valley;
    @ApiModelProperty(value = "平值电量(kWh)")
    Integer normal;
    @ApiModelProperty(value = "尖峰电量(kWh)")
    Integer mountainPeak;

}
