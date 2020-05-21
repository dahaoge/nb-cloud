package cn.hao.nb.cloud.ydglExternalApi.entity;

import cn.hao.nb.cloud.common.penum.EDateType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: hao
 * @Date: 2020/5/21 20:14
 * @Description:
 */
@Data
@ApiModel(value = "电能质量统计信息")
public class ElecQualityStatistics implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("组织机构id")
    String deptId;
    @ApiModelProperty("组织机构名称")
    String deptName;
    @ApiModelProperty("设备id")
    String deviceId;
    @ApiModelProperty("设备名称")
    String deviceName;
    @ApiModelProperty("统计时间")
    Date time;
    @ApiModelProperty("统计时间类型")
    EDateType dateType;
    @ApiModelProperty("统计开始时间")
    Date statisticsTimeBegin;
    @ApiModelProperty("统计结束时间")
    Date statisticsTimeEnd;
    @ApiModelProperty("存储间隔")
    String saveInterval;
    @ApiModelProperty("存储次数")
    Integer saveCnt;
    @ApiModelProperty("额定电压")
    String defaultVoltage;
    @ApiModelProperty("A相电压正偏差")
    VoltageDeviationStatistics positiveA;
    @ApiModelProperty("A相电压负偏差")
    VoltageDeviationStatistics negativeA;
    @ApiModelProperty("B相电压正偏差")
    VoltageDeviationStatistics positiveB;
    @ApiModelProperty("B相电压负偏差")
    VoltageDeviationStatistics negativeB;
    @ApiModelProperty("C相电压正偏差")
    VoltageDeviationStatistics positiveC;
    @ApiModelProperty("C相电压负偏差")
    VoltageDeviationStatistics negativeC;
    @ApiModelProperty("频率统计")
    HzStatistics hzStatistics;
    @ApiModelProperty("功率因数统计")
    PowerFactorStatistics powerFactorStatistics;
    @ApiModelProperty("电压正序偏差")
    ImbalanceStatistics positiveDeviation;
    @ApiModelProperty("电压负序偏差")
    ImbalanceStatistics negativeDeviation;

    @Data
    @ApiModel("频率统计结果")
    public class HzStatistics implements Serializable {

        private static final long serialVersionUID = 1L;

        @ApiModelProperty("合格率")
        Float hzQualifiedRate;
        @ApiModelProperty("合格时间,单位秒(s)")
        Long hzQualifiedTime;
        @ApiModelProperty("异常时间,单位秒(s)")
        Long hzbnormalTime;
    }

    @Data
    @ApiModel("功率因数统计结果")
    public class PowerFactorStatistics implements Serializable {
        private static final long serialVersionUID = 1L;

        @ApiModelProperty("总有功电量")
        Integer activePower;
        @ApiModelProperty("总无功电量")
        Integer reactivePower;
        @ApiModelProperty("平均功率因数")
        Float avgPowerFactor;
    }

    @Data
    @ApiModel("不平衡度统计结果")
    public class ImbalanceStatistics implements Serializable {
        private static final long serialVersionUID = 1L;

        @ApiModelProperty("不平衡度")
        Integer Imbalance;
        @ApiModelProperty("正序")
        Integer positiveSequence;
        @ApiModelProperty("负序")
        Integer negativeSequence;
    }


}
