package cn.hao.nb.cloud.ydglExternalApi.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: hao
 * @Date: 2020/5/20 21:48
 * @Description:
 */
@Data
public class ElecAccount {
    @ApiModelProperty(value = "组织机构id")
    String deptId;
    @ApiModelProperty(value = "组织机构名称")
    String deptName;
    @ApiModelProperty(value = "户号")
    String elecAccountNum;
    @ApiModelProperty(value = "地址")
    String address;
}
