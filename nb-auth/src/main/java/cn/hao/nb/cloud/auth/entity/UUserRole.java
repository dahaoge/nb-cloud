package cn.hao.nb.cloud.auth.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fgzy.mc.common.entity.Qw;
import com.fgzy.mc.common.penum.EOrder;
import com.fgzy.mc.common.util.CheckUtil;
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
 * 用户角色
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "UUserRole对象", description = "用户角色 ")
public class UUserRole implements Serializable {

    public static final String VERSION = "version";
    public static final String CREATE_BY = "create_by";
    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE_BY = "update_by";
    public static final String UPDATE_TIME = "update_time";
    public static final String DELETED = "deleted";
    public static final String UR_ID = "ur_id";
    public static final String USER_ID = "user_id";
    public static final String ROLE_CODE = "role_code";
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "创建人信息")
    @TableField(exist = false)
    private Map
            <String, Object> createUserMap;
    @ApiModelProperty(value = "修改人信息")
    @TableField(exist = false)
    private Map
            <String, Object> updateUserMap;
    @ApiModelProperty(value = "乐观锁")
    @Version
    private Integer version;
    private String createBy;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    private String updateBy;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "删除标记")
    @TableLogic
    private Integer deleted = 0;
    @TableId(value = "ur_id", type = IdType.INPUT)
    private String urId;
    private String userId;
    private String roleCode;

    @Data
    public class SearchParams {


        @ApiModelProperty(value = "排序字段")
        private String sort = "update_time";

        @ApiModelProperty(value = "排序方式,可选值:ASC/DESC")
        private EOrder order = EOrder.DESC;

        public Qw<UUserRole> preWrapper(Qw<UUserRole> qw) {
            if (CheckUtil.objIsEmpty(qw))
                qw = Qw.create();
            return qw;
        }
    }

}