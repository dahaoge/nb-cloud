package cn.hao.nb.cloud.ydglExternalApi.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: hao
 * @Date: 2020/5/7 21:26
 * @Description:
 */
@Data
@ApiModel(value = "电压采点", description = "电压采点")
public class VoltageItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "A向电压(V)")
    Integer directionA;
    @ApiModelProperty(value = "B向电压(V)")
    Integer directionB;
    @ApiModelProperty(value = "C向电压(V)")
    Integer directionC;
    @ApiModelProperty(value = "采集时间")
    Date collectionTime;
}