package cn.hao.nb.cloud.ydglExternalApi.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hao
 * @Date: 2020/5/20 20:14
 * @Description:
 */
@Data
public class ElecBasicQuality implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("A向电压(带单位)")
    String voltageDirectionA;
    @ApiModelProperty("B向电压(带单位)")
    String voltageDirectionB;
    @ApiModelProperty("C向电压(带单位)")
    String voltageDirectionC;
    @ApiModelProperty("AB向电压(带单位)")
    String voltageDirectionAB;
    @ApiModelProperty("AC向电压(带单位)")
    String voltageDirectionAC;
    @ApiModelProperty("BC向电压(带单位)")
    String voltageDirectionBC;
    @ApiModelProperty("A向电流(带单位)")
    String currentDirectionA;
    @ApiModelProperty("B向电流(带单位)")
    String currentDirectionB;
    @ApiModelProperty("C向电流(带单位)")
    String currentDirectionC;
    @ApiModelProperty("零序电流(带单位)")
    String currentDirectionZero;
    @ApiModelProperty("有功功率(带单位)")
    String ygLoad;
    @ApiModelProperty("无功功率(带单位)")
    String wgLoad;
    @ApiModelProperty("视在功率(带单位)")
    String szLoad;
    @ApiModelProperty("功率因数")
    Float loadFactor;
    @ApiModelProperty("频率(带单位)")
    String frequency;
}
