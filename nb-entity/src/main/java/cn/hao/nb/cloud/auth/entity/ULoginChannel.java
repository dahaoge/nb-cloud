package cn.hao.nb.cloud.auth.entity;

import cn.hao.nb.cloud.common.entity.Qw;
import cn.hao.nb.cloud.common.entity.RedisUser;
import cn.hao.nb.cloud.common.penum.ELoginChannelScop;
import cn.hao.nb.cloud.common.penum.ELoginType;
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
 * 登录渠道
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "ULoginChannel对象", description = "登录渠道")
public class ULoginChannel implements Serializable {

    @Data
    public class SearchParams {

        @ApiModelProperty(value = "排序字段")
        private String sort = "update_time";

        @ApiModelProperty(value = "排序方式,可选值:ASC/DESC")
        private ESqlOrder order = ESqlOrder.DESC;

        public Qw<ULoginChannel> preWrapper(Qw<ULoginChannel> qw) {
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
    @TableId(value = "t_id", type = IdType.INPUT)
    private Long tId;
    @ApiModelProperty(value = "用户id")
    private Long userId;
    @ApiModelProperty(value = "登录类型")
    private ELoginType loginType;
    @ApiModelProperty(value = "登录id")
    private String loginId;
    @ApiModelProperty(value = "登录渠道")
    private ELoginChannelScop loginChannelScope;

    public static final String VERSION = "version";
    public static final String CREATE_BY = "create_by";
    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE_BY = "update_by";
    public static final String UPDATE_TIME = "update_time";
    public static final String DELETED = "deleted";
    public static final String T_ID = "t_id";
    public static final String USER_ID = "user_id";
    public static final String LOGIN_TYPE = "login_type";
    public static final String LOGIN_ID = "login_id";
    public static final String LOGIN_CHANNEL_SCOPE = "login_channel_scope";

    public static Qw<ULoginChannel> select(Qw<ULoginChannel> qw) {
        if (CheckUtil.objIsEmpty(qw))
            qw = Qw.create();
        qw.select(
                ULoginChannel.USER_ID,
                ULoginChannel.LOGIN_TYPE,
                ULoginChannel.LOGIN_ID,
                ULoginChannel.LOGIN_CHANNEL_SCOPE
        );
        return qw;
    }

}