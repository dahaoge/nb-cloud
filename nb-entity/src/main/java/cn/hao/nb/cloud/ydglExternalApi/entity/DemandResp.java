package cn.hao.nb.cloud.ydglExternalApi.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: hao
 * @Date: 2020/5/25 20:48
 * @Description:
 */
@Data
@ApiModel(value = "需求响应", description = "需求响应")
public class DemandResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("响应类型-削峰|填谷")
    String demandType;
    @ApiModelProperty("响应开始时间")
    Date demandBeginTime;
    @ApiModelProperty("响应结束时间")
    Date demandEndTime;
    @ApiModelProperty("约定响应量")
    String promissKw;
    @ApiModelProperty("实际响应量")
    String actualKw;
}
