package cn.hao.nb.cloud.ydglExternalApi.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hao
 * @Date: 2020/5/6 20:14
 * @Description:
 */
@Data
@ApiModel(value = "设备信息", description = "设备信息")
public class DeviceInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "设备id")
    String deviceId;
    @ApiModelProperty(value = "设备名称")
    String deviceName;
}
