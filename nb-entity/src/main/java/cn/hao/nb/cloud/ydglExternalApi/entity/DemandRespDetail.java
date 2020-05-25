package cn.hao.nb.cloud.ydglExternalApi.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hao
 * @Date: 2020/5/25 23:01
 * @Description:
 */
@Data
@ApiModel(value = "需求响应详情", description = "需求响应详情")
public class DemandRespDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("负荷类型-一类|二类|非关键|非生产")
    String loadType;
    @ApiModelProperty("实际响应量")
    Integer realKw;
}
