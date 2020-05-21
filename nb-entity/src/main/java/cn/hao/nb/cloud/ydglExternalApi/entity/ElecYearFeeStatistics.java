package cn.hao.nb.cloud.ydglExternalApi.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Auther: hao
 * @Date: 2020/5/20 21:51
 * @Description:
 */
@Data
public class ElecYearFeeStatistics extends ElecAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("月度电费列表")
    List<ElecFeeItem> monthFees;
    @ApiModelProperty("年份")
    Date year;
}
