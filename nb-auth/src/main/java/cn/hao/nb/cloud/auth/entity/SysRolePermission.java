package cn.hao.nb.cloud.auth.entity;

import cn.hao.nb.cloud.common.entity.Qw;
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
import java.util.Map;

/**
 * <p>
 * 角色权限
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "SysRolePermission对象", description = "角色权限 ")
public class SysRolePermission implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String VERSION = "version";
    public static final String CREATE_BY = "create_by";

    @ApiModelProperty(value = "乐观锁")
    @Version
    private Integer version;
    public static final String CREATE_TIME = "create_time";

    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    public static final String UPDATE_BY = "update_by";

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "删除标记")
    @TableLogic
    private Integer deleted = 0;
    public static final String UPDATE_TIME = "update_time";
    public static final String DELETED = "deleted";
    public static final String ROLE_PERMISSION_ID = "role_permission_id";
    public static final String ROLE_CODE = "role_code";
    public static final String PERMISSION_CODE = "permission_code";
    @ApiModelProperty(value = "创建人信息")
    @TableField(exist = false)
    private Map<String, Object> createUserMap;
    @ApiModelProperty(value = "修改人信息")
    @TableField(exist = false)
    private Map<String, Object> updateUserMap;
    @ApiModelProperty(value = "创建人")
    private String createBy;
    @ApiModelProperty(value = "更新人")
    private String updateBy;
    @ApiModelProperty(value = "ID")
    @TableId(value = "role_permission_id", type = IdType.INPUT)
    private String rolePermissionId;
    @ApiModelProperty(value = "角色code")
    private String roleCode;
    @ApiModelProperty(value = "权限code")
    private String permissionCode;
    @TableField(exist = false)
    private SysPermission permission;

    @Data
    public class SearchParams {
        @ApiModelProperty(value = "排序字段")
        private String sort = "update_time";

        @ApiModelProperty(value = "排序方式,可选值:ASC/DESC")
        private ESqlOrder order = ESqlOrder.DESC;

        public Qw<SysRolePermission> preWrapper(Qw<SysRolePermission> qw) {
            if (CheckUtil.objIsEmpty(qw))
                qw = Qw.create();
            return qw;
        }
    }

}