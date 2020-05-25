package cn.hao.nb.cloud.ydglExternalApi.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: hao
 * @Date: 2020/5/25 21:05
 * @Description:
 */
@Data
@ApiModel(value = "需求响应告警信息", description = "需求响应告警信息")
public class DemandRespAlarm implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("需求响应类型")
    String demandType;
    @ApiModelProperty("告警信息")
    String alarmMsg;
    @ApiModelProperty("告警时间")
    Date alarmTime;
}
