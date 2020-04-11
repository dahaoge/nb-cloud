package cn.hao.nb.cloud.ydgl.mapper;

import cn.hao.nb.cloud.ydgl.entity.DeptRelationship;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Map;

/**
 * <p>
 * 用电管理组织机构对照表  Mapper 接口
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2020-04-10
 */
public interface DeptRelationshipMapper extends BaseMapper<DeptRelationship> {

    IPage
            <Map
                    <String, Object>> pageMapData(Page page, DeptRelationship.SearchParams searchParams);

}
