package cn.hao.nb.cloud.auth.mapper;

import cn.hao.nb.cloud.auth.entity.SysDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 组织机构 Mapper 接口
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-08
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {

    IPage
            <Map
                    <String, Object>> pageMapData(Page page, SysDept.SearchParams searchParams);

    @Select("select * from sys_dept where deleted=0 and" +
            " dept_id in (select dept_id from u_user_dept where deleted=0 and user_id=#{userId}) order by p_id asc")
    List<SysDept> listByUserId(@Param("userId") Long userId);

}
