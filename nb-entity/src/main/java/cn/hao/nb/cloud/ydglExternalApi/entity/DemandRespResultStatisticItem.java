package cn.hao.nb.cloud.ydglExternalApi.entity;

import cn.hao.nb.cloud.common.penum.EDemandRespType;
import cn.hao.nb.cloud.common.penum.EDemandType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hao
 * @Date: 2020/5/25 22:36
 * @Description:
 */
@Data
@ApiModel(value = "需求响应结果统计项", description = "需求响应结果统计项")
public class DemandRespResultStatisticItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("需求类型")
    EDemandType demandType;
    @ApiModelProperty("响应类型")
    EDemandRespType respType;
    @ApiModelProperty("有效次数")
    Integer effectiveCnt;
    @ApiModelProperty("无效次数")
    Integer invalidCnt;
    @ApiModelProperty("未响应次数")
    Integer noRespCnt;
}
