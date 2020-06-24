package cn.hao.nb.cloud.auth.entity;

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

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "SysPermission对象", description = "权限表 ")
public class SysPermission implements Serializable {

    @Data
    public class SearchParams {

        private String permissionCode;

        private String permissionName;

        @ApiModelProperty(value = "排序字段")
        private String sort = "update_time";

        @ApiModelProperty(value = "排序方式,可选值:ASC/DESC")
        private ESqlOrder order = ESqlOrder.DESC;

        public Qw<SysPermission> preWrapper(Qw<SysPermission> qw) {
            if (CheckUtil.objIsEmpty(qw))
                qw = Qw.create();
            if (CheckUtil.objIsEmpty(this.getPermissionCode()))
                qw.like(SysPermission.PERMISSION_CODE, this.getPermissionCode());
            if (CheckUtil.objIsEmpty(this.getPermissionName()))
                qw.like(SysPermission.PERMISSION_NAME, this.getPermissionName());
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

    private Long createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    private Long updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "删除标记")
    @TableLogic
    private Integer deleted = 0;

    @TableId(value = "permission_id", type = IdType.INPUT)
    private Long permissionId;

    private String permissionCode;

    private String permissionName;


    public static final String VERSION = "version";

    public static final String CREATE_BY = "create_by";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_BY = "update_by";

    public static final String UPDATE_TIME = "update_time";

    public static final String DELETED = "deleted";

    public static final String PERMISSION_ID = "permission_id";

    public static final String PERMISSION_CODE = "permission_code";

    public static final String PERMISSION_NAME = "permission_name";

    public static Qw<SysPermission> select(Qw<SysPermission> qw) {
        if (CheckUtil.objIsEmpty(qw))
            qw = Qw.create();
        qw.select(
                SysPermission.PERMISSION_CODE,
                SysPermission.PERMISSION_NAME
        );
        return qw;
    }

}