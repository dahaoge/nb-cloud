package cn.hao.nb.cloud.ydglExternalApi.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Auther: hao
 * @Date: 2020/5/9 11:02
 * @Description:
 */
@Data
@ApiModel(value = "电费账单", description = "电费账单")
public class ElecBill extends ElecAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账单时间")
    Date billTime;
    @ApiModelProperty(value = "金额")
    BigDecimal fee;
    @ApiModelProperty(value = "电量")
    Float elecConsumption;
    @ApiModelProperty(value = "积分电费")
    BigDecimal baseFee;
    @ApiModelProperty(value = "电度电费")
    BigDecimal ddFee;
    @ApiModelProperty(value = "力调电费")
    BigDecimal ltFee;
    @ApiModelProperty(value = "电压等级")
    String voltageLevel;
    @ApiModelProperty(value = "综合倍率")
    Float combinedRate;
    @ApiModelProperty(value = "有功电量")
    Float activeConsumption;
    @ApiModelProperty(value = "无功电量")
    Float reactiveConsumption;
}
