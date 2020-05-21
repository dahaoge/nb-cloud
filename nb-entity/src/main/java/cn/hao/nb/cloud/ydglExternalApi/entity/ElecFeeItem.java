package cn.hao.nb.cloud.ydglExternalApi.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: hao
 * @Date: 2020/5/20 21:52
 * @Description:
 */
@Data
public class ElecFeeItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "时间")
    Date time;
    @ApiModelProperty(value = "价格(单位元/kWh)")
    Float fee;

}
