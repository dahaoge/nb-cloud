package cn.hao.nb.cloud.ydgl.service;

import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.ydgl.entity.DeptRelationship;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用电管理组织机构对照表  服务类11
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2020-04-10
 */
public interface IDeptRelationshipService extends IService<DeptRelationship> {
    /**
     * 添加数据
     *
     * @param data
     * @return
     */
    DeptRelationship addData(DeptRelationship data);

    /**
     * 增量更新数据
     *
     * @param data
     * @return
     */
    boolean incrementModifyData(DeptRelationship data);

    /**
     * 全量更新数据
     *
     * @param data
     * @return
     */
    boolean totalAmountModifyData(DeptRelationship data);

    /**
     * 删除数据
     *
     * @param id
     * @return
     */
    boolean delData(Long id);

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    DeptRelationship getDetail(Long id);

    /**
     * 分页查询数据
     *
     * @param pg
     * @param searchParams
     * @return
     */
    IPage<DeptRelationship> pageData(Pg pg, DeptRelationship.SearchParams searchParams);

    /**
     * 列表查询数据
     *
     * @param searchParams
     * @return
     */
    List<DeptRelationship> listData(DeptRelationship.SearchParams searchParams);

    /**
     * 分页查询Map数据
     *
     * @param pg
     * @param searchParams
     * @return
     */
    IPage
            <Map
                    <String, Object>> pageMapData(Pg pg, DeptRelationship.SearchParams searchParams);

    /**
     * 处理返回值
     *
     * @param data
     * @return
     */
    DeptRelationship prepareReturnModel(DeptRelationship data);

    /**
     * 处理返回值
     *
     * @param page
     * @return
     */
    IPage<DeptRelationship> prepareReturnModel(IPage<DeptRelationship> page);

    /**
     * 处理返回值
     *
     * @param list
     * @return
     */
    List<DeptRelationship> prepareReturnModel(List<DeptRelationship> list);

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
    void validData(DeptRelationship data);

}
/*

import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.Rv;
import com.fgzy.mc.core.entity.DeptRelationship;
import com.fgzy.mc.core.service.IDeptRelationshipService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@Autowired
IDeptRelationshipService iDeptRelationshipService;

@ApiOperation(value = "添加用电管理组织机构对照表 ", notes = "添加用电管理组织机构对照表 ")
@PostMapping(value = "/deptRelationship/add")
public Rv addDeptRelationship(DeptRelationship data) {
return Rv.getInstance(iDeptRelationshipService.addData(data));
}

@ApiOperation(value = "修改用电管理组织机构对照表 ", notes = "修改用电管理组织机构对照表 ")
@PostMapping(value = "/deptRelationship/modify")
public Rv modifyDeptRelationship(DeptRelationship data) {
return Rv.getInstance(iDeptRelationshipService.modifyData(data));
}

@ApiOperation(value = "删除用电管理组织机构对照表 ", notes = "删除用电管理组织机构对照表 ")
@PostMapping(value = "/deptRelationship/del/{id}")
public Rv delDeptRelationship(@ApiParam(name = "id", value = "用电管理组织机构对照表 id") @PathVariable Long id) {
return Rv.getInstance(iDeptRelationshipService.delData(id));
}

@ApiOperation(value = "查询用电管理组织机构对照表 ", notes = "查询用电管理组织机构对照表 ")
@GetMapping(value = "/deptRelationship/getById/{id}")
public Rv getDeptRelationshipById(@ApiParam(name = "id", value = "用电管理组织机构对照表 id") @PathVariable Long id) {
return Rv.getInstance(iDeptRelationshipService.getDetail(id));
}

@ApiOperation(value = "分页查询用电管理组织机构对照表 ", notes = "分页查询用电管理组织机构对照表 ")
@GetMapping(value = "/deptRelationship/page")
public Rv pageDeptRelationship(Pg pg,DeptRelationship.SearchParams searchParams) {
return Rv.getInstance(iDeptRelationshipService.pageData(pg,searchParams));
}

@ApiOperation(value = "列表查询用电管理组织机构对照表 ", notes = "列表查询用电管理组织机构对照表 ")
@GetMapping(value = "/deptRelationship/list")
public Rv listDeptRelationship(DeptRelationship.SearchParams searchParams) {
return Rv.getInstance(iDeptRelationshipService.listData(searchParams));
}

@ApiOperation(value = "分页查询用电管理组织机构对照表 (map数据)", notes = "列表查询用电管理组织机构对照表 (map数据)")
@GetMapping(value = "/deptRelationship/pageMap")
public Rv pageMapDeptRelationship(Pg pg,DeptRelationship.SearchParams searchParams) {
return Rv.getInstance(iDeptRelationshipService.pageMapData(pg,searchParams));
}


*/