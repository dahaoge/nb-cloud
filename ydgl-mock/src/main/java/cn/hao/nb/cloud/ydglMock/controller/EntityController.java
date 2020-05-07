package cn.hao.nb.cloud.ydglMock.controller;

import cn.hao.nb.cloud.ydglExternalApi.entity.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: hao
 * @Date: 2020/4/20 15:03
 * @Description:
 */
@RestController
public class EntityController {
    @GetMapping("/getLoadItem")
    public LoadItem getLoadItem() {
        return null;
    }

    @GetMapping("/getCurrentItem")
    public CurrentItem getCurrentItem() {
        return null;
    }

    @GetMapping("/getExternalDepartment")
    public ExternalDepartment getExternalDepartment() {
        return null;
    }

    @GetMapping("/getKWhItem")
    public KWhItem getKWhItem() {
        return null;
    }

    @GetMapping("/getTotalStatisiticsData")
    public TotalStatisiticsData getTotalStatisiticsData() {
        return null;
    }

    @GetMapping("/getTimeRangeLoadData")
    public TimeRangeLoadData getTimeRangeLoadData() {
        return null;
    }

    @GetMapping("/getDeviceAlarmMsg")
    public DeviceAlarmMsg getDeviceAlarmMsg() {
        return null;
    }

    @GetMapping("/getVoltageItem")
    public VoltageItem getVoltageItem() {
        return null;
    }

    @GetMapping("/getDeviceInfo")
    public DeviceInfo getDeviceInfo() {
        return null;
    }

    @GetMapping("/getElecConsumptionData")
    public ElecConsumptionData getElecConsumptionData() {
        return null;
    }


}
