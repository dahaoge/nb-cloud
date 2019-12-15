package cn.hao.nb.cloud.common.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hao
 * @Date: 2019-12-15 14:23
 * @Description:
 */
@Data
public class RedisUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
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
}
