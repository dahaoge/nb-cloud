package cn.hao.nb.cloud.auth.mapper;

import cn.hao.nb.cloud.auth.entity.SysMenu;
import cn.hao.nb.cloud.auth.entity.SysPermission;
import cn.hao.nb.cloud.auth.entity.SysRole;
import cn.hao.nb.cloud.common.entity.TokenUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: hao
 * @Date: 2019-12-08 13:38
 * @Description:
 */
@Repository
public interface AuthMapper extends BaseMapper<TokenUser> {

    /**
     * 根据用户id获取用户
     *
     * @param userId
     * @return
     */
    public TokenUser getTokenUserById(Long userId);

    public TokenUser getTokenUserByLoginId(String loginId);

    public List<SysPermission> getUserPermission(Long userId);

    public List<SysRole> getUserRoles(Long userId);

    public List<SysMenu> getUserMenus(Long userId);
}