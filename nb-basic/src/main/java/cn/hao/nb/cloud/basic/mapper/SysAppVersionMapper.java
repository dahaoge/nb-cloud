package cn.hao.nb.cloud.basic.mapper;

import cn.hao.nb.cloud.basic.entity.SysAppVersion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Map;

/**
 * <p>
 * app版本管理  Mapper 接口
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-28
 */
public interface SysAppVersionMapper extends BaseMapper<SysAppVersion> {

    IPage
            <Map
                    <String, Object>> pageMapData(Page page, SysAppVersion.SearchParams searchParams);

}
