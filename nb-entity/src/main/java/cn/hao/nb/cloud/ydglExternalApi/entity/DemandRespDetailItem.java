package cn.hao.nb.cloud.ydglExternalApi.entity;

import cn.hao.nb.cloud.common.penum.EDemandType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: hao
 * @Date: 2020/5/25 22:47
 * @Description:
 */
@Data
@ApiModel(value = "需求响应详情", description = "需求响应详情")
public class DemandRespDetailItem extends DemandRespDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("需求类型")
    EDemandType demandType;
    @ApiModelProperty("响应开始时间")
    Date respStartTime;
    @ApiModelProperty("响应结束时间")
    Date respEndTime;
    @ApiModelProperty("约定响应量")
    Integer promissKw;
}
