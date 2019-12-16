package cn.hao.nb.cloud.auth.mapper;

import cn.hao.nb.cloud.auth.entity.SysPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 权限表  Mapper 接口
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-08
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    IPage
            <Map
                    <String, Object>> pageMapData(Page page, SysPermission.SearchParams searchParams);

    @Select("select * from sys_permission where deleted=0 and permission_code int " +
            "(select permission_code from sys_role_permission where deleted=0 and role_code=#{roleCode}) t1")
    List<SysPermission> listByRoleCode(@Param("roleCode") String roleCode);

}
