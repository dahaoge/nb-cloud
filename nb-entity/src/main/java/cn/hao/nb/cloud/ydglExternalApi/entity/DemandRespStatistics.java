package cn.hao.nb.cloud.ydglExternalApi.entity;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hao
 * @Date: 2020/5/25 22:33
 * @Description:
 */
@Data
@ApiModel(value = "需求响应统计", description = "需求响应统计")
public class DemandRespStatistics implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("按照需求&类型分组统计数据-约定峰&约定谷&实时峰&实时谷")
    List<DemandRespResultStatisticItem> respResultStatisticItems;
    @ApiModelProperty("响应详情")
    List<DemandRespDetailItem> respDetailItems;
    @ApiModelProperty("按照负荷类型分组统计各个类型实际最大响应负荷")
    List<DemandRespDetail> maxRespLoadGroupByLoadType;
    @ApiModelProperty("按照负荷类型分组统计各个类型实际响应负荷总量占比")
    List<DemandRespDetail> totalRespLoadGroupByLoadType;

    public static DemandRespStatistics createMockData() {
        DemandRespStatistics data = new DemandRespStatistics();
        data.setRespResultStatisticItems(
                Lists.newArrayList(
                        new DemandRespResultStatisticItem(),
                        new DemandRespResultStatisticItem()
                )
        );
        data.setRespDetailItems(
                Lists.newArrayList(
                        new DemandRespDetailItem(),
                        new DemandRespDetailItem()
                )
        );
        data.setMaxRespLoadGroupByLoadType(
                Lists.newArrayList(
                        new DemandRespDetail(),
                        new DemandRespDetail()
                )
        );
        data.setTotalRespLoadGroupByLoadType(
                Lists.newArrayList(
                        new DemandRespDetail(),
                        new DemandRespDetail()
                )
        );
        return data;
    }

}
