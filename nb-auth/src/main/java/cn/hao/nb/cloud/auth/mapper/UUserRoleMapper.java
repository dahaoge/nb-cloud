package cn.hao.nb.cloud.auth.mapper;

import cn.hao.nb.cloud.auth.entity.UUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Map;

/**
 * <p>
 * 用户角色  Mapper 接口
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-08
 */
public interface UUserRoleMapper extends BaseMapper<UUserRole> {

    IPage
            <Map
                    <String, Object>> pageMapData(Page page, UUserRole.SearchParams searchParams);

}
