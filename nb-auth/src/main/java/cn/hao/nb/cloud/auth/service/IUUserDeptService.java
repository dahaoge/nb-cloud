package cn.hao.nb.cloud.auth.service;

import cn.hao.nb.cloud.auth.entity.UUserDept;
import cn.hao.nb.cloud.common.entity.Pg;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户所属组织机构 服务类11
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-08
 */
public interface IUUserDeptService extends IService<UUserDept> {
    /**
     * 添加数据
     *
     * @param data
     * @return
     */
    UUserDept addData(UUserDept data);

    UUserDept addData(String userId, String deptId);

    boolean addUser2Depts(String userId, String deptIds);

    /**
     * 修改数据
     *
     * @param data
     * @return
     */
    boolean modifyData(UUserDept data);

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
    UUserDept getDetail(String id);

    List<UUserDept> listByUserId(String userId);

    /**
     * 分页查询数据
     *
     * @param pg
     * @param searchParams
     * @return
     */
    IPage<UUserDept> pageData(Pg pg, UUserDept.SearchParams searchParams);

    /**
     * 列表查询数据
     *
     * @param searchParams
     * @return
     */
    List<UUserDept> listData(UUserDept.SearchParams searchParams);

    /**
     * 分页查询Map数据
     *
     * @param pg
     * @param searchParams
     * @return
     */
    IPage
            <Map
                    <String, Object>> pageMapData(Pg pg, UUserDept.SearchParams searchParams);

    /**
     * 处理返回值
     *
     * @param data
     * @return
     */
    UUserDept prepareReturnModel(UUserDept data);

    /**
     * 处理返回值
     *
     * @param page
     * @return
     */
    IPage<UUserDept> prepareReturnModel(IPage<UUserDept> page);

    /**
     * 处理返回值
     *
     * @param list
     * @return
     */
    List<UUserDept> prepareReturnModel(List<UUserDept> list);

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
    void validData(UUserDept data);

}
/*

import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.Rv;
import com.fgzy.mc.core.entity.UUserDept;
import com.fgzy.mc.core.service.IUUserDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@Autowired
IUUserDeptService iUUserDeptService;

@ApiOperation(value = "添加用户所属组织机构", notes = "添加用户所属组织机构")
@PostMapping(value = "/uUserDept/add")
public Rv addUUserDept(UUserDept data) {
return Rv.getInstance(iUUserDeptService.addData(data));
}

@ApiOperation(value = "修改用户所属组织机构", notes = "修改用户所属组织机构")
@PostMapping(value = "/uUserDept/modify")
public Rv modifyUUserDept(UUserDept data) {
return Rv.getInstance(iUUserDeptService.modifyData(data));
}

@ApiOperation(value = "删除用户所属组织机构", notes = "删除用户所属组织机构")
@PostMapping(value = "/uUserDept/del/{id}")
public Rv delUUserDept(@ApiParam(name = "id", value = "用户所属组织机构id") @PathVariable String id) {
return Rv.getInstance(iUUserDeptService.delData(id));
}

@ApiOperation(value = "查询用户所属组织机构", notes = "查询用户所属组织机构")
@GetMapping(value = "/uUserDept/getById/{id}")
public Rv getUUserDeptById(@ApiParam(name = "id", value = "用户所属组织机构id") @PathVariable String id) {
return Rv.getInstance(iUUserDeptService.getDetail(id));
}

@ApiOperation(value = "分页查询用户所属组织机构", notes = "分页查询用户所属组织机构")
@GetMapping(value = "/uUserDept/page")
public Rv pageUUserDept(Pg pg,UUserDept.SearchParams searchParams) {
return Rv.getInstance(iUUserDeptService.pageData(pg,searchParams));
}

@ApiOperation(value = "列表查询用户所属组织机构", notes = "列表查询用户所属组织机构")
@GetMapping(value = "/uUserDept/list")
public Rv listUUserDept(UUserDept.SearchParams searchParams) {
return Rv.getInstance(iUUserDeptService.listData(searchParams));
}

@ApiOperation(value = "分页查询用户所属组织机构(map数据)", notes = "列表查询用户所属组织机构(map数据)")
@GetMapping(value = "/uUserDept/pageMap")
public Rv pageMapUUserDept(Pg pg,UUserDept.SearchParams searchParams) {
return Rv.getInstance(iUUserDeptService.pageMapData(pg,searchParams));
}


*/