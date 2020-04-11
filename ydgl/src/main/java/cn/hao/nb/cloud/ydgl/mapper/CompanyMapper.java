package cn.hao.nb.cloud.ydgl.mapper;

import cn.hao.nb.cloud.ydgl.entity.Company;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Map;

/**
 * <p>
 * 公司管理  Mapper 接口
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2020-04-10
 */
public interface CompanyMapper extends BaseMapper<Company> {

    IPage
            <Map
                    <String, Object>> pageMapData(Page page, Company.SearchParams searchParams);

}
