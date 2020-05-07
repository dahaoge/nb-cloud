package cn.hao.nb.cloud.ydgl.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hao
 * @Date: 2020/4/12 22:41
 * @Description:
 */
@Data
public class OutDepartment implements Serializable {

    private static final long serialVersionUID = 1L;

    String deptId;
    String deptName;
    String parentDeptId;
}
