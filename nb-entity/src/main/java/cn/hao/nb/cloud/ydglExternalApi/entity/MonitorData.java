package cn.hao.nb.cloud.ydglExternalApi.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hao
 * @Date: 2020/5/20 20:41
 * @Description:
 */
@Data
public class MonitorData<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "当前数据")
    T current;
    @ApiModelProperty(value = "按照时间区间和采集密度获取的列表数据")
    List<T> timeRange;

    public static <T> MonitorData<T> create(T current, List<T> timeRange) {
        MonitorData<T> monitorData = new MonitorData<T>();
        monitorData.setCurrent(current);
        monitorData.setTimeRange(timeRange);
        return monitorData;
    }
}
