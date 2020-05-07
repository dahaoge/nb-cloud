package cn.hao.nb.cloud.ydglExternalApi.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: hao
 * @Date: 2020/4/24 11:20
 * @Description:
 */
@Data
@ApiModel(value = "设备报警信息", description = "设备报警信息")
public class DeviceAlarmMsg implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "告警序号,非必填")
    Integer alarmNum;
    @ApiModelProperty(value = "设备id")
    String deviceId;
    @ApiModelProperty(value = "设备名称")
    String deviceName;
    @ApiModelProperty(value = "组织机构id")
    String alarmMsg;
    @ApiModelProperty(value = "组织机构id")
    Date alarmTime;
}
