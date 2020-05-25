package cn.hao.nb.cloud.ydglExternalApi.entity;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hao
 * @Date: 2020/5/25 21:41
 * @Description:
 */
@Data
@ApiModel(value = "需求响应", description = "需求响应")
public class DemandRespMonitor implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("需求响应列表")
    List<DemandResp> demandResps;
    @ApiModelProperty("负荷曲线")
    List<LoadItem> loadItems;
    @ApiModelProperty("告警信息")
    List<DemandRespAlarm> alarms;

    public static DemandRespMonitor createMockData() {
        DemandRespMonitor monitor = new DemandRespMonitor();
        monitor.setDemandResps(
                Lists.newArrayList(
                        new DemandResp(),
                        new DemandResp()
                )
        );
        monitor.setLoadItems(
                Lists.newArrayList(
                        new LoadItem(),
                        new LoadItem()
                )
        );
        monitor.setAlarms(
                Lists.newArrayList(
                        Lists.newArrayList(
                                new DemandRespAlarm(),
                                new DemandRespAlarm()
                        )
                )
        );
        return monitor;
    }

}
