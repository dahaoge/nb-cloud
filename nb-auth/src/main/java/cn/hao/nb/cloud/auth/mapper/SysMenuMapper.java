package cn.hao.nb.cloud.auth.mapper;

import cn.hao.nb.cloud.auth.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单表  Mapper 接口
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-08
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    IPage
            <Map
                    <String, Object>> pageMapData(Page page, SysMenu.SearchParams searchParams);

    @Select("select * from sys_menu where deleted=0 and menu_code in (select menu_code from sys_role_menu where deleted=0 and role_code=#{roleCode}) t1")
    List<SysMenu> listByRoleCode(@Param("roleCode") String roleCode);

}
