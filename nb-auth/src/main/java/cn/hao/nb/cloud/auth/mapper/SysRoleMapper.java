package cn.hao.nb.cloud.auth.mapper;

import cn.hao.nb.cloud.auth.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色  Mapper 接口
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-08
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    IPage
            <Map
                    <String, Object>> pageMapData(Page page, SysRole.SearchParams searchParams);

    @Select("select * from sys_role where deleted=0 and role_code in (select role_code from u_user_role where deleted=0 and userId=#{userId}) t1")
    List<SysRole> listByUserId(@Param("userId") Long userId);

}
