package cn.hao.nb.cloud.ydglExternalApi.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hao
 * @Date: 2020/5/8 16:56
 * @Description:
 */
@Data
@ApiModel(value = "单位产量能耗", description = "单位产量能耗")
public class UnitOutputItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "产出(t)")
    Integer output;
    @ApiModelProperty(value = "用电量(kWh)")
    Integer elecConsumption;
    @ApiModelProperty(value = "比率")
    Float ratio;
    @ApiModelProperty(value = "组织机构id")
    String deptId;
    @ApiModelProperty(value = "组织机构名称")
    String deptName;
    @ApiModelProperty(value = "排名")
    Integer rank;
}
