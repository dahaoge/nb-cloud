package cn.hao.nb.cloud.auth.service.impl;

import cn.hao.nb.cloud.auth.entity.SysMenu;
import cn.hao.nb.cloud.auth.entity.SysRoleMenu;
import cn.hao.nb.cloud.auth.mapper.SysMenuMapper;
import cn.hao.nb.cloud.auth.service.ISysMenuService;
import cn.hao.nb.cloud.auth.service.ISysRoleMenuService;
import cn.hao.nb.cloud.common.constant.CommonConstant;
import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.Qw;
import cn.hao.nb.cloud.common.penum.EErrorCode;
import cn.hao.nb.cloud.common.util.CheckUtil;
import cn.hao.nb.cloud.common.util.IDUtil;
import cn.hao.nb.cloud.common.util.RedisUtil;
import cn.hao.nb.cloud.common.util.UserUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单表  服务实现类
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-08
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Autowired
    IDUtil idUtil;
    @Autowired
    SysMenuMapper mapper;
    @Autowired
    ISysRoleMenuService roleMenuService;
    @Autowired
    RedisUtil redisUtil;

    /**
     * 添加数据
     *
     * @param data
     * @return
     */
    @Override
    public SysMenu addData(SysMenu data) {
        this.validData(data);
        data.setMenuId(idUtil.nextId());
        data.setCreateBy(UserUtil.getTokenUser(true).getUserId());
        data.setUpdateBy(UserUtil.getTokenUser(true).getUserId());
        data.setVersion(null);
        data.setDeleted(null);
        data.setUpdateTime(null);
        data.setCreateTime(null);
        this.save(data);
        return data;
    }

    /**
     * 增量更新数据
     *
     * @param data
     * @return
     */
    @Override
    public boolean incrementModifyData(SysMenu data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(
                data.getMenuId()
        ))
            throw NBException.create(EErrorCode.missingArg);
        if (CheckUtil.objIsNotEmpty(data.getMenuCode()) || CheckUtil.objIsNotEmpty(data.getParentMenuCode()))
            this.validData(data);
        data.setUpdateBy(UserUtil.getTokenUser(true).getUserId());
        data.setVersion(null);
        data.setDeleted(null);
        data.setUpdateTime(null);
        data.setCreateTime(null);
        return this.updateById(data);
    }

    /**
     * 全量更新数据
     *
     * @param data
     * @return
     */
    @Override
    public boolean totalAmountModifyData(SysMenu data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(
                data.getMenuId()
        ))
            throw NBException.create(EErrorCode.missingArg);
        if (CheckUtil.objIsNotEmpty(data.getMenuCode()) || CheckUtil.objIsNotEmpty(data.getParentMenuCode()))
            this.validData(data);
        data.setUpdateBy(UserUtil.getTokenUser(true).getUserId());
        data.setVersion(null);
        data.setDeleted(null);
        data.setUpdateTime(null);
        data.setCreateTime(null);
        return this.update(data, Wrappers.<SysMenu>lambdaUpdate()
                .set(SysMenu::getUpdateBy, data.getUpdateBy())
                .set(SysMenu::getParentMenuCode, data.getParentMenuCode())
                .set(SysMenu::getMenuCode, data.getMenuCode())
                .set(SysMenu::getMenuName, data.getMenuName())
                .set(SysMenu::getMenuPath, data.getMenuPath())
                .set(SysMenu::getMenuIndex, data.getMenuIndex())
                .set(SysMenu::getMenuIcon, data.getMenuIcon())
                .set(SysMenu::getMenuType, data.getMenuType())
                .eq(SysMenu::getMenuId, data.getMenuId())
        );
    }

    /**
     * 删除数据
     *
     * @param id
     * @return
     */
    @Override
    public boolean delData(String id) {
        if (CheckUtil.strIsEmpty(id))
            throw NBException.create(EErrorCode.missingArg);
        SysMenu data = this.getById(id);
        if (CheckUtil.objIsEmpty(data))
            throw NBException.create(EErrorCode.noData);
        this.beUsedCheck(data);
        return this.removeById(id);
    }

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    @Override
    public SysMenu getDetail(String id) {
        if (CheckUtil.strIsEmpty(id))
            throw NBException.create(EErrorCode.missingArg);
        return this.prepareReturnModel(this.getById(id));
    }

    @Override
    public SysMenu getByMenuCode(String menuCode) {
        if (CheckUtil.objIsEmpty(menuCode))
            throw NBException.create(EErrorCode.missingArg);
        return this.getOne(Qw.create().eq(SysMenu.MENU_CODE, menuCode));
    }

    @Override
    public SysMenu getParent(SysMenu menu) {
        if (CheckUtil.objIsEmpty(menu))
            throw NBException.create(EErrorCode.missingArg);
        if (CheckUtil.objIsNotEmpty(menu.getParentMenuCode()))
            return this.getByMenuCode(menu.getParentMenuCode());
        return null;
    }

    @Override
    public SysMenu getParent(String menuCode) {
        SysMenu menu = this.getByMenuCode(menuCode);
        if (CheckUtil.objIsNotEmpty(menu))
            return this.getParent(menu);
        return null;
    }

    @Override
    public List<SysMenu> getDisMenu(String menuCode) {
        if (CheckUtil.objIsEmpty(menuCode))
            throw NBException.create(EErrorCode.missingArg);
        return this.list(Qw.create().eq(SysMenu.PARENT_MENU_CODE, menuCode));
    }

    @Override
    public List<SysMenu> menuTree() {
        List<SysMenu> result = (List<SysMenu>) (Object) redisUtil.get(CommonConstant.REDIS_MENU_TREE);
        if (CheckUtil.objIsNotEmpty(result))
            return result;
        result = this.list(Qw.create().isNull(SysMenu.PARENT_MENU_CODE));
        if (CheckUtil.collectionIsNotEmpty(result)) {
            result.forEach(item -> this.recursiveDisMenu(item));
        }
        redisUtil.set(CommonConstant.REDIS_MENU_TREE, result, CommonConstant.REDIS_MENU_TREE_EXPIRE_TIME);
        return result;
    }

    private void recursiveDisMenu(SysMenu menu) {
        if (CheckUtil.objIsNotEmpty(menu)) {
            menu.setChildren(this.getDisMenu(menu.getMenuCode()));
            if (CheckUtil.collectionIsNotEmpty(menu.getChildren()))
                menu.getChildren().forEach(item -> this.recursiveDisMenu(item));
        }
    }

    /**
     * 分页查询
     *
     * @param pg
     * @param searchParams
     * @return
     */
    @Override
    public IPage<SysMenu> pageData(Pg pg, SysMenu.SearchParams searchParams) {
        return this.prepareReturnModel(this.page(pg.page(), searchParams.preWrapper(pg.wrapper())));
    }

    /**
     * 列表查询
     *
     * @param searchParams
     * @return
     */
    @Override
    public List<SysMenu> listData(SysMenu.SearchParams searchParams) {
        return this.prepareReturnModel(this.list(searchParams.preWrapper(null)));
    }

    @Override
    public List<SysMenu> listByRoleCode(String roleCode) {
        if (CheckUtil.objIsEmpty(roleCode))
            throw NBException.create(EErrorCode.missingArg);
        return mapper.listByRoleCode(roleCode);
    }

    /**
     * 连表分页查询map数据
     *
     * @param pg
     * @param searchParams
     * @return
     */
    @Override
    public IPage
            <Map
                    <String, Object>> pageMapData(Pg pg, SysMenu.SearchParams searchParams) {
        return mapper.pageMapData(pg.page(), searchParams);
    }

    /**
     * 处理返回值
     *
     * @param data
     * @return
     */
    @Override
    public SysMenu prepareReturnModel(SysMenu data) {
        return data;
    }

    /**
     * 处理返回值
     *
     * @param page
     * @return
     */
    @Override
    public IPage<SysMenu> prepareReturnModel(IPage<SysMenu> page) {
        if (CheckUtil.objIsNotEmpty(page) && CheckUtil.collectionIsNotEmpty(page.getRecords()))
            this.prepareReturnModel(page.getRecords());
        return page;
    }

    /**
     * 处理返回值
     *
     * @param list
     * @return
     */
    @Override
    public List<SysMenu> prepareReturnModel(List<SysMenu> list) {
        if (CheckUtil.collectionIsNotEmpty(list))
            list.forEach(item -> {
                this.prepareReturnModel(item);
            });
        return list;
    }

    /**
     * 处理返回值
     *
     * @param page
     * @return
     */
    @Override
    public IPage
            <Map
                    <String, Object>> prepareReturnMapModel(IPage
                                                                    <Map
                                                                            <String, Object>> page) {
        return page;
    }

    /**
     * 添加/修改数据前校验数据有效性(强制抛出异常)
     * 如果不需要抛出异常请不用调用该服务
     *
     * @param data
     */
    @Override
    public void validData(SysMenu data) {
        if (CheckUtil.objIsEmpty(data))
            throw NBException.create(EErrorCode.missingArg);
        if (CheckUtil.objIsEmpty(data.getMenuId()) && CheckUtil.objIsEmpty(data.getMenuCode()))
            throw NBException.create(EErrorCode.missingArg);

        this.beUsedCheck(data);

        if (CheckUtil.objIsNotEmpty(data.getParentMenuCode())) {
            SysMenu p = this.getByMenuCode(data.getParentMenuCode());
            if (CheckUtil.objIsEmpty(p))
                throw NBException.create(EErrorCode.noData, "请先添加编码为[" + data.getParentMenuCode() + "]的父级菜单");
        }
    }

    private void beUsedCheck(SysMenu data) {
        if (CheckUtil.objIsEmpty(data))
            throw NBException.create(EErrorCode.missingArg);
        if (CheckUtil.objIsNotEmpty(data.getMenuCode())) {
            Qw qw = Qw.create().eq(SysMenu.MENU_CODE, data.getMenuCode());
            if (CheckUtil.objIsNotEmpty(data.getMenuId())) {
                qw.ne(SysMenu.MENU_ID, data.getMenuId());
                SysMenu dbData = this.getById(data.getMenuId());
                if (roleMenuService.count(Qw.create().eq(SysRoleMenu.MENU_CODE, dbData.getMenuCode())) > 0)
                    throw NBException.create(EErrorCode.beUsed, "无法修改使用中菜单编码");
            }
            if (this.count(qw) > 0)
                throw NBException.create(EErrorCode.beUsed);
        }
    }

}
