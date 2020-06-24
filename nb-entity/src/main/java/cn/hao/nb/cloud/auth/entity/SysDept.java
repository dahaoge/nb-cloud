package cn.hao.nb.cloud.auth.entity;

import cn.hao.nb.cloud.common.entity.MiniDept;
import cn.hao.nb.cloud.common.entity.Qw;
import cn.hao.nb.cloud.common.entity.RedisUser;
import cn.hao.nb.cloud.common.penum.ESqlOrder;
import cn.hao.nb.cloud.common.util.CheckUtil;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 组织机构
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "SysDept对象", description = "组织机构")
public class SysDept implements Serializable {

    @Data
    public class SearchParams {

        @ApiModelProperty(value = "排序字段")
        private String sort = "update_time";

        @ApiModelProperty(value = "排序方式,可选值:ASC/DESC")
        private ESqlOrder order = ESqlOrder.DESC;

        public Qw<SysDept> preWrapper(Qw<SysDept> qw) {
            if (CheckUtil.objIsEmpty(qw))
                qw = Qw.create();
            if (ESqlOrder.DESC.equals(this.getOrder()))
                qw.orderByDesc(this.getSort());
            else
                qw.orderByAsc(this.getSort());
            return qw;
        }
    }

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "创建人信息")
    @TableField(exist = false)
    private RedisUser createUserMap;
    @ApiModelProperty(value = "修改人信息")
    @TableField(exist = false)
    private RedisUser updateUserMap;
    @ApiModelProperty(value = "乐观锁")
    @Version
    private Integer version;
    @ApiModelProperty(value = "创建人")
    private Long createBy;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新人")
    private Long updateBy;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "删除标记")
    @TableLogic
    private Integer deleted = 0;
    @ApiModelProperty(value = "ID")
    @TableId(value = "dept_id", type = IdType.INPUT)
    private Long deptId;
    @ApiModelProperty(value = "名称")
    private String deptName;
    @ApiModelProperty(value = "父节点id")
    private Long pId;
    @ApiModelProperty(value = "组织机构外部id")
    private String externalDeptId;
    @ApiModelProperty(value = "公司id")
    private Long companyId;

    @TableField(exist = false)
    private List<SysDept> children;

    public MiniDept toMiniDept() {
        MiniDept miniDept = new MiniDept();
        miniDept.setDeptId(this.getDeptId());
        miniDept.setDeptName(this.getDeptName());
        miniDept.setExternalDeptId(this.getExternalDeptId());
        miniDept.setPId(this.getPId());
        return miniDept;
    }

    public static final String VERSION = "version";
    public static final String CREATE_BY = "create_by";
    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE_BY = "update_by";
    public static final String UPDATE_TIME = "update_time";
    public static final String DELETED = "deleted";
    public static final String DEPT_ID = "dept_id";
    public static final String DEPT_NAME = "dept_name";
    public static final String P_ID = "p_id";
    public static final String EXTERNAL_DEPT_ID = "external_dept_id";
    public static final String COMPANY_ID = "company_id";


    public static Qw<SysDept> select(Qw<SysDept> qw) {
        if (CheckUtil.objIsEmpty(qw))
            qw = Qw.create();
        qw.select(
                SysDept.DEPT_NAME,
                SysDept.P_ID,
                SysDept.EXTERNAL_DEPT_ID,
                SysDept.COMPANY_ID
        );
        return qw;
    }
}