package cn.hao.nb.cloud.ydglExternalApi.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: hao
 * @Date: 2020/5/20 22:01
 * @Description:
 */
@Data
public class ElecMonthFeeGuess extends ElecAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "月份")
    Date month;
    @ApiModelProperty(value = "预估电费")
    Float guessFee;

}
