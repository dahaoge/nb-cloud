package cn.hao.nb.cloud.ydglExternalApi.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: hao
 * @Date: 2020/5/7 09:02
 * @Description:
 */
@Data
@ApiModel(value = "按照时间周期统计的负荷信息", description = "按照时间周期统计的负荷信息")
public class TimeRangeLoadData implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "最小负荷(kW)")
    Integer minLoad;
    @ApiModelProperty(value = "最小负荷采集时间")
    Date minLoadTime;
    @ApiModelProperty(value = "当前负荷(kW)")
    Integer currentLoad;
    @ApiModelProperty(value = "当前负荷采集时间")
    Date currentLoadTime;
    @ApiModelProperty(value = "最大负荷(kW)")
    Integer maxLoad;
    @ApiModelProperty(value = "最大负荷采集时间")
    Date maxLoadTime;

}
