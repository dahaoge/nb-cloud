package cn.hao.nb.cloud.ydglExternalApi.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: hao
 * @Date: 2020/5/7 21:16
 * @Description:
 */
@Data
@ApiModel(value = "负荷采点", description = "负荷采点")
public class LoadItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "负荷采集时间")
    Date collectionTime;
    @ApiModelProperty(value = "负荷(kW)")
    Integer load;
}
