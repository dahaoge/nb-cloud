package cn.hao.nb.cloud.auth.service;

import cn.hao.nb.cloud.auth.entity.SysDept;
import cn.hao.nb.cloud.common.entity.Pg;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 组织机构 服务类11
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-08
 */
public interface ISysDeptService extends IService<SysDept> {
    /**
     * 添加数据
     *
     * @param data
     * @return
     */
    SysDept addData(SysDept data);

    SysDept addData(String deptName, String pId);

    SysDept addData(String deptName);

    /**
     * 增量更新数据
     * @param data
     * @return
     */
    boolean incrementModifyData(SysDept data);

    /**
     * 全量更新数据
     *
     * @param data
     * @return
     */
    boolean totalAmountModifyData(SysDept data);

    /**
     * 删除数据
     *
     * @param id
     * @return
     */
    boolean delData(String id);

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    SysDept getDetail(String id);

    List<SysDept> listAllDisDeptByUserId(String userId);

    List<SysDept> listAllDisDeptByParent(SysDept parent);

    List<SysDept> listAllDisDeptByParentId(String pId);

    List<SysDept> listDisDeptByParent(SysDept parent);

    List<SysDept> listDisDeptByParentId(String pId);

    List<SysDept> deptTree(String deptId);

    List<SysDept> userDeptTree(String userId);

    List<SysDept> listByUserId(String userId);

    /**
     * 分页查询数据
     *
     * @param pg
     * @param searchParams
     * @return
     */
    IPage<SysDept> pageData(Pg pg, SysDept.SearchParams searchParams);

    /**
     * 列表查询数据
     *
     * @param searchParams
     * @return
     */
    List<SysDept> listData(SysDept.SearchParams searchParams);

    /**
     * 分页查询Map数据
     *
     * @param pg
     * @param searchParams
     * @return
     */
    IPage
            <Map
                    <String, Object>> pageMapData(Pg pg, SysDept.SearchParams searchParams);

    /**
     * 处理返回值
     *
     * @param data
     * @return
     */
    SysDept prepareReturnModel(SysDept data);

    /**
     * 处理返回值
     *
     * @param page
     * @return
     */
    IPage<SysDept> prepareReturnModel(IPage<SysDept> page);

    /**
     * 处理返回值
     *
     * @param list
     * @return
     */
    List<SysDept> prepareReturnModel(List<SysDept> list);

    /**
     * 处理返回值
     *
     * @param page
     * @return
     */
    IPage
            <Map
                    <String, Object>> prepareReturnMapModel(IPage
                                                                    <Map
                                                                            <String, Object>> page);

    /**
     * 添加/修改数据前校验数据有效性(强制抛出异常)
     * 如果不需要抛出异常请不用调用该服务
     *
     * @param data
     */
    void validData(SysDept data);

}
/*

import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.Rv;
import com.fgzy.mc.core.entity.SysDept;
import com.fgzy.mc.core.service.ISysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@Autowired
ISysDeptService iSysDeptService;

@ApiOperation(value = "添加组织机构", notes = "添加组织机构")
@PostMapping(value = "/sysDept/add")
public Rv addSysDept(SysDept data) {
return Rv.getInstance(iSysDeptService.addData(data));
}

@ApiOperation(value = "修改组织机构", notes = "修改组织机构")
@PostMapping(value = "/sysDept/modify")
public Rv modifySysDept(SysDept data) {
return Rv.getInstance(iSysDeptService.modifyData(data));
}

@ApiOperation(value = "删除组织机构", notes = "删除组织机构")
@PostMapping(value = "/sysDept/del/{id}")
public Rv delSysDept(@ApiParam(name = "id", value = "组织机构id") @PathVariable String id) {
return Rv.getInstance(iSysDeptService.delData(id));
}

@ApiOperation(value = "查询组织机构", notes = "查询组织机构")
@GetMapping(value = "/sysDept/getById/{id}")
public Rv getSysDeptById(@ApiParam(name = "id", value = "组织机构id") @PathVariable String id) {
return Rv.getInstance(iSysDeptService.getDetail(id));
}

@ApiOperation(value = "分页查询组织机构", notes = "分页查询组织机构")
@GetMapping(value = "/sysDept/page")
public Rv pageSysDept(Pg pg,SysDept.SearchParams searchParams) {
return Rv.getInstance(iSysDeptService.pageData(pg,searchParams));
}

@ApiOperation(value = "列表查询组织机构", notes = "列表查询组织机构")
@GetMapping(value = "/sysDept/list")
public Rv listSysDept(SysDept.SearchParams searchParams) {
return Rv.getInstance(iSysDeptService.listData(searchParams));
}

@ApiOperation(value = "分页查询组织机构(map数据)", notes = "列表查询组织机构(map数据)")
@GetMapping(value = "/sysDept/pageMap")
public Rv pageMapSysDept(Pg pg,SysDept.SearchParams searchParams) {
return Rv.getInstance(iSysDeptService.pageMapData(pg,searchParams));
}


*/