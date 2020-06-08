package cn.hao.nb.cloud.common.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hao
 * @Date: 2020/6/6 16:12
 * @Description:
 */
@Data
@ApiModel(value = "MiniDept对象", description = "组织机构")
public class MiniDept implements Serializable {
    private static final long serialVersionUID = 1L;


    Long deptId;
    String deptName;
    String externalDeptId;
    Long pId;
}
