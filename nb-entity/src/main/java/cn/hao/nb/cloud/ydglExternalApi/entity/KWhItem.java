package cn.hao.nb.cloud.ydglExternalApi.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: hao
 * @Date: 2020/5/7 21:31
 * @Description:
 */
@Data
@ApiModel(value = "功采点", description = "功采点")
public class KWhItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "正向有功(kWh)")
    Integer positiveActive;
    @ApiModelProperty(value = "正向无功(kWh)")
    Integer positiveReactive;
    @ApiModelProperty(value = "反向有功(kWh)")
    Integer reverseActive;
    @ApiModelProperty(value = "反向无功")
    Integer reverseReactive;
    @ApiModelProperty(value = "采集时间")
    Date collectionTime;
}
