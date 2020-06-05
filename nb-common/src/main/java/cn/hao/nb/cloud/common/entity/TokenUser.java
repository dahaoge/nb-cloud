package cn.hao.nb.cloud.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author scootxin@163.com
 * @since 2019-03-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Token中存放的用户对象", description = " ")
public class TokenUser implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "user_id", type = IdType.INPUT)
    private Long userId;
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
    @ApiModelProperty("公司id")
    private Long companyId;


    @TableField(exist = false)
    private List<String> permissionList;
    @TableField(exist = false)
    private List<String> roleList;
    @TableField(exist = false)
    private List<String> menuList;
    @TableField(exist = false)
    private List<Map<String, Object>> authDeptList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (permissionList == null) {
            return Lists.newArrayList();
        }

        List<SimpleGrantedAuthority> list = Lists.newLinkedList();
        for (String permission : permissionList) {
            list.add(new SimpleGrantedAuthority(permission));
        }

        return list;
    }

    @Override
    public String getPassword() {
        return this.loginPwd;
    }

    @Override
    public String getUsername() {
        return this.loginId;
    }

    public String getUserName() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isLocked == null ? true : (isLocked.intValue() == 0 ? true : false);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isLocked == null ? true : (isLocked.intValue() == 0 ? true : false);
    }
}