package cn.hao.nb.cloud.ydglExternalApi.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hao
 * @Date: 2020/5/21 20:59
 * @Description:
 */
@Data
@ApiModel(value = "电压偏差统计值")
public class VoltageDeviationStatistics implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "最大值")
    Float max;
    @ApiModelProperty(value = "平均值")
    Float avg;
    @ApiModelProperty(value = "95%概率值")
    Float percent95Probability;
    @ApiModelProperty(value = "限制值")
    Float limitValue;
    @ApiModelProperty(value = "越限次数")
    Float overLimitCnt;

}
