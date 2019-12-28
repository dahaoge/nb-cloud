package cn.hao.nb.cloud.basic.mapper;

import cn.hao.nb.cloud.basic.entity.SysTags;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Map;

/**
 * <p>
 * 标签  Mapper 接口
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-28
 */
public interface SysTagsMapper extends BaseMapper<SysTags> {

    IPage
            <Map
                    <String, Object>> pageMapData(Page page, SysTags.SearchParams searchParams);

}
