package cn.hao.nb.cloud.auth.entity;

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
 * 用户信息表
 * </p>
 *
 * @author scootxin@163.com
 * @since 2019-11-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "UUserInfo对象", description = "用户信息表 ")
public class UUserInfo implements Serializable {


    public static final String VERSION = "version";
    public static final String CREATE_BY = "create_by";
    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE_BY = "update_by";
    public static final String UPDATE_TIME = "update_time";
    public static final String DELETED = "deleted";
    public static final String USER_ID = "user_id";
    public static final String WECHAT_UNIONID = "wechat_unionid";
    public static final String WECHAT_OPENID = "wechat_openid";
    public static final String PHONE = "phone";
    public static final String WECHAT_NICK_NAME = "wechat_nick_name";
    public static final String USER_NAME = "user_name";
    public static final String USER_ICNUM = "user_icnum";
    public static final String USER_ICON = "user_icon";
    public static final String LOGIN_ID = "login_id";
    public static final String LOGIN_PWD = "login_pwd";
    public static final String USER_SEX = "user_sex";
    public static final String EMAIL = "email";
    public static final String SALT = "salt";
    public static final String HAS_NETEASE_ACC = "has_netease_acc";
    public static final String NETEASE_TOKEN = "netease_token";
    public static final String LOCK_STATE = "lock_state";
    public static final String UNLOCK_TIME = "unlock_time";
    public static final String LOCK_CNT = "lock_cnt";
    public static final String INVITED_CODE = "invited_code";
    public static final String LAST_LOGIN_TIME = "last_login_time";
    public static final String SHOW_PHONE = "show_phone";
    public static final String USER_TYPE = "user_type";
    public static final String COMMISSION_USER_LEVEL = "commission_user_level";
    public static final String NICK_NAME = "nick_name";
    public static final String REAL_NAME = "real_name";
    public static final String IS_ANONYMOUS = "is_anonymous";
    public static final String WECHAT_PN_OPENID = "wechat_pn_openid";
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
    @TableId(value = "user_id", type = IdType.INPUT)
    private String userId;
    private String wechatUnionid;
    private String wechatOpenid;
    private String phone;
    private String wechatNickName;
    private String userName;
    private String userIcnum;
    private String userIcon;
    private String loginId;
    private String loginPwd;
    @ApiModelProperty(value = "性别")
    private Integer userSex;
    private String email;
    private String salt;
    @ApiModelProperty(value = "是否创建了网易云信账号")
    private Integer hasNeteaseAcc;
    private String neteaseToken;
    @ApiModelProperty(value = "锁定状态")
    private Integer lockState;
    @ApiModelProperty(value = "解锁时间")
    private Date unlockTime;
    @ApiModelProperty(value = "锁定计数")
    private Integer lockCnt;
    private String invitedCode;
    private Date lastLoginTime;
    private String showPhone;
    private String userType;
    private String commissionUserLevel;
    @ApiModelProperty(value = "用户昵称")
    private String nickName;
    @ApiModelProperty(value = "真实姓名")
    private String realName;
    @ApiModelProperty(value = "是否匿名")
    private Integer isAnonymous;
    @ApiModelProperty(value = "微信公众号openid")
    private String wechatPnOpenid;

}