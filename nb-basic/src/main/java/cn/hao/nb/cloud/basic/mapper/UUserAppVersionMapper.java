package cn.hao.nb.cloud.basic.mapper;

import cn.hao.nb.cloud.basic.entity.UUserAppVersion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Map;

/**
 * <p>
 * 用户app版本 Mapper 接口
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2020-06-14
 */
public interface UUserAppVersionMapper extends BaseMapper<UUserAppVersion> {

    IPage
            <Map
                    <String, Object>> pageMapData(Page page, UUserAppVersion.SearchParams searchParams);

}
