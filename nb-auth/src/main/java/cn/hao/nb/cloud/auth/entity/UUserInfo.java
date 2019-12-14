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
 * 用户信息
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "UUserInfo对象", description = "用户信息")
public class UUserInfo implements Serializable {

    @Data
    public class SearchParams {

        @ApiModelProperty(value = "用户名|手机号|身份证号")
        private String q;
        @ApiModelProperty(value = "是否被锁定")
        private Integer isLocked;

        @ApiModelProperty(value = "排序字段")
        private String sort = "update_time";

        @ApiModelProperty(value = "排序方式,可选值:ASC/DESC")
        private ESqlOrder order = ESqlOrder.DESC;

        public Qw<UUserInfo> preWrapper(Qw<UUserInfo> qw) {
            if (CheckUtil.objIsEmpty(qw))
                qw = Qw.create();
            if (CheckUtil.objIsNotEmpty(this.getQ()))
                qw.and(wrapper -> {
                    wrapper.or().like(UUserInfo.USER_NAME, this.getQ());
                    wrapper.or().like(UUserInfo.PHONE, this.getQ());
                    wrapper.or().like(UUserInfo.ICNUM, this.getQ());
                });
            if (CheckUtil.objIsNotEmpty(this.getIsLocked()))
                qw.eq(UUserInfo.IS_LOCKED, this.getIsLocked());
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
    private Map
            <String, Object> createUserMap;
    @ApiModelProperty(value = "修改人信息")
    @TableField(exist = false)
    private Map
            <String, Object> updateUserMap;
    @ApiModelProperty(value = "乐观锁")
    @Version
    private Integer version;
    @ApiModelProperty(value = "创建人")
    private String createBy;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新人")
    private String updateBy;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "删除标记")
    @TableLogic
    private Integer deleted = 0;
    @ApiModelProperty(value = "ID")
    @TableId(value = "user_id", type = IdType.INPUT)
    private String userId;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "身份证号")
    private String icnum;
    @ApiModelProperty(value = "头像")
    private String icon;
    @ApiModelProperty(value = "登录id")
    private String loginId;
    @ApiModelProperty(value = "登录密码")
    private String loginPwd;
    @ApiModelProperty(value = "加密盐")
    private String salt;
    @ApiModelProperty(value = "是否被锁定")
    private Integer isLocked;
    @ApiModelProperty(value = "解锁时间")
    private Date unlockTime;

    public static final String VERSION = "version";
    public static final String CREATE_BY = "create_by";
    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE_BY = "update_by";
    public static final String UPDATE_TIME = "update_time";
    public static final String DELETED = "deleted";
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String PHONE = "phone";
    public static final String ICNUM = "icnum";
    public static final String ICON = "icon";
    public static final String LOGIN_ID = "login_id";
    public static final String LOGIN_PWD = "login_pwd";
    public static final String SALT = "salt";
    public static final String IS_LOCKED = "is_locked";
    public static final String UNLOCK_TIME = "unlock_time";

}