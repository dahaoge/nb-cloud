package cn.hao.nb.cloud.auth.mapper;

import cn.hao.nb.cloud.auth.entity.SysRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Map;

/**
 * <p>
 * 角色菜单  Mapper 接口
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-08
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    IPage
            <Map
                    <String, Object>> pageMapData(Page page, SysRoleMenu.SearchParams searchParams);

}
