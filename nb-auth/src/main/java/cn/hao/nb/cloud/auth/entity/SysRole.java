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
 * 角色
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "SysRole对象", description = "角色 ")
public class SysRole implements Serializable {

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
    public static final String ROLE_ID = "role_id";
    public static final String ROLE_CODE = "role_code";
    public static final String ROLE_NAME = "role_name";
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
    @TableId(value = "role_id", type = IdType.INPUT)
    private String roleId;
    @ApiModelProperty(value = "角色编码")
    private String roleCode;
    @ApiModelProperty(value = "角色名称")
    private String roleName;
    @TableField(exist = false)
    private UUserRole userRole;

    @Data
    public class SearchParams {


        @ApiModelProperty(value = "排序字段")
        private String sort = "update_time";

        @ApiModelProperty(value = "排序方式,可选值:ASC/DESC")
        private ESqlOrder order = ESqlOrder.DESC;

        public Qw<SysRole> preWrapper(Qw<SysRole> qw) {
            if (CheckUtil.objIsEmpty(qw))
                qw = Qw.create();
            return qw;
        }
    }

}