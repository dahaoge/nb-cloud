package cn.hao.nb.cloud.ydglExternalApi.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: hao
 * @Date: 2020/4/14 11:18
 * @Description:
 */
@Data
@ApiModel(value = "外部组织机构", description = "外部组织机构\n" +
        "1.当组织机构名称区分全称和简称时取简称")
public class ExternalDepartment {

    @ApiModelProperty(value = "组织机构id")
    String deptId;
    @ApiModelProperty(value = "组织机构名称")
    String deptName;
    @ApiModelProperty(value = "父级组织机构id")
    String parentDeptId;
    @ApiModelProperty(value = "子节点")
    ExternalDepartment children;
}
