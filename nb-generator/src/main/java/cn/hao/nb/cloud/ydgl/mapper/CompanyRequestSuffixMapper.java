package cn.hao.nb.cloud.ydgl.mapper;

import cn.hao.nb.cloud.ydgl.entity.CompanyRequestSuffix;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Map;

/**
 * <p>
 * 公司请求后缀  Mapper 接口
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2020-04-12
 */
public interface CompanyRequestSuffixMapper extends BaseMapper<CompanyRequestSuffix> {

    IPage
            <Map
                    <String, Object>> pageMapData(Page page, CompanyRequestSuffix.SearchParams searchParams);

}
