package cn.hao.nb.cloud.ydglExternalApi.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: hao
 * @Date: 2020/5/7 21:22
 * @Description:
 */
@Data
@ApiModel(value = "电流采点", description = "电流采点")
public class CurrentItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "A向电流(A)")
    Integer directionA;
    @ApiModelProperty(value = "B向电流(A)")
    Integer directionB;
    @ApiModelProperty(value = "C向电流(A)")
    Integer directionC;
    @ApiModelProperty(value = "采集时间")
    Date collectionTime;
}
