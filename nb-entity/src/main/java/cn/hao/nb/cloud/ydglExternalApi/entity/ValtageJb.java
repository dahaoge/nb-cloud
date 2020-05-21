package cn.hao.nb.cloud.ydglExternalApi.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hao
 * @Date: 2020/5/20 20:26
 * @Description:
 */
@Data
public class ValtageJb implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "UA电压偏差")
    String uaDistortion;
    @ApiModelProperty(value = "UB电压偏差")
    String ubDistortion;
    @ApiModelProperty(value = "UC电压偏差")
    String ucDistortion;
    @ApiModelProperty(value = "UAB电压偏差")
    String uabDistortion;
    @ApiModelProperty(value = "UAC电压偏差")
    String uacDistortion;
    @ApiModelProperty(value = "UBC电压偏差")
    String ubcDistortion;
    @ApiModelProperty(value = "A向畸变率(百分比)")
    Integer directionADistortionRate;
    @ApiModelProperty(value = "B向畸变率(百分比)")
    Integer directionBDistortionRate;
    @ApiModelProperty(value = "C向畸变率(百分比)")
    Integer directionCDistortionRate;

}
