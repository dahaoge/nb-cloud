package cn.hao.nb.cloud.basic.service.impl;

import cn.hao.nb.cloud.basic.constant.RedisKey;
import cn.hao.nb.cloud.basic.entity.SysDict;
import cn.hao.nb.cloud.basic.mapper.SysDictMapper;
import cn.hao.nb.cloud.basic.service.ISysDictService;
import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.Qw;
import cn.hao.nb.cloud.common.entity.TokenUser;
import cn.hao.nb.cloud.common.penum.EErrorCode;
import cn.hao.nb.cloud.common.util.CheckUtil;
import cn.hao.nb.cloud.common.util.IDUtil;
import cn.hao.nb.cloud.common.util.RedisUtil;
import cn.hao.nb.cloud.common.util.UserUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据字典  服务实现类
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-28
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    @Autowired
    IDUtil idUtil;
    @Autowired
    SysDictMapper mapper;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public Multimap<String, SysDict> dictMap() {
        Multimap<String, SysDict> result = (Multimap<String, SysDict>) redisUtil.hget(RedisKey.REDIS_SYS_DICT, RedisKey.DICT_MAP);
        if (CheckUtil.objIsEmpty(result)) {
            // 防止内存击穿,只有争抢到锁才去数据库取数据
            if (redisUtil.lock(RedisKey.REDIS_SYS_DICT_MAP_LOCK, RedisKey.DICT_MAP, 60L)) {
                try {
                    result = ArrayListMultimap.create();
                    Qw qw = Qw.create();
                    qw.orderByDesc(SysDict.DICT_INDEX);
                    List<SysDict> list = this.list(qw.select(SysDict.DICT_TYPE, SysDict.DICT_CODE, SysDict.DICT_LABEL));
                    for (SysDict item : list) {
                        result.put(item.getDictType(), item);
                    }
                    redisUtil.hset(RedisKey.REDIS_SYS_DICT, RedisKey.DICT_MAP, result);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw NBException.create(e);
                } finally {
                    redisUtil.releaseLock(RedisKey.REDIS_SYS_DICT_MAP_LOCK);
                }
            } else {
                try {
                    Thread.sleep(500);
                    return dictMap();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return dictMap();
                }
            }
        }
        return result;
    }

    @Override
    public boolean refreshRedisDictMap() {
        redisUtil.del(RedisKey.REDIS_SYS_DICT);
        return true;
    }

    /**
     * 添加数据
     *
     * @param data
     * @return
     */
    @Override
    public SysDict addData(SysDict data) {
        this.validData(data);
        data.setDictId(idUtil.nextId());
        TokenUser tokenUser = UserUtil.getTokenUser(true);
        data.setCreateBy(tokenUser.getUserId());
        data.setUpdateBy(tokenUser.getUserId());
        data.setVersion(null);
        data.setDeleted(null);
        data.setUpdateTime(null);
        data.setCreateTime(null);
        this.save(data);
        this.refreshRedisDictMap();
        return data;
    }

    /**
     * 增量更新数据
     *
     * @param data
     * @return
     */
    @Override
    public boolean incrementModifyData(SysDict data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(
                data.getDictId()
        ))
            throw NBException.create(EErrorCode.missingArg);
        data.setUpdateBy(UserUtil.getTokenUser(true).getUserId());
        data.setVersion(null);
        data.setDeleted(null);
        data.setUpdateTime(null);
        data.setCreateTime(null);
        return this.updateById(data) && this.refreshRedisDictMap();
    }

    /**
     * 全量更新数据
     *
     * @param data
     * @return
     */
    @Override
    public boolean totalAmountModifyData(SysDict data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(
                data.getDictId()
        ))
            throw NBException.create(EErrorCode.missingArg);
        data.setUpdateBy(UserUtil.getTokenUser(true).getUserId());
        data.setVersion(null);
        data.setDeleted(null);
        data.setUpdateTime(null);
        data.setCreateTime(null);
        return this.update(data, Wrappers.<SysDict>lambdaUpdate()
                .set(SysDict::getUpdateBy, data.getUpdateBy())
                .set(SysDict::getDictType, data.getDictType())
                .set(SysDict::getDictCode, data.getDictCode())
                .set(SysDict::getDictLabel, data.getDictLabel())
                .set(SysDict::getDictDesc, data.getDictDesc())
                .set(SysDict::getDictIndex, data.getDictIndex())
                .eq(SysDict::getDictId, data.getDictId())
        ) && this.refreshRedisDictMap();
    }

    /**
     * 删除数据
     *
     * @param id
     * @return
     */
    @Override
    public boolean delData(Long id) {
        if (CheckUtil.objIsEmpty(id))
            throw NBException.create(EErrorCode.missingArg);
        return this.removeById(id) && this.refreshRedisDictMap();
    }

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    @Override
    public SysDict getDetail(Long id) {
        if (CheckUtil.objIsEmpty(id))
            throw NBException.create(EErrorCode.missingArg);
        return this.prepareReturnModel(this.getById(id));
    }

    @Override
    public List<SysDict> getByType(String dictType) {
        if (CheckUtil.strIsEmpty(dictType))
            throw NBException.create(EErrorCode.missingArg).plusMsg("dictType");
        List<SysDict> result = (List<SysDict>) redisUtil.hget(RedisKey.REDIS_SYS_DICT, RedisKey.DICT_TYPE.concat(dictType));
        if (CheckUtil.collectionIsEmpty(result)) {
            result = Lists.newArrayList(this.dictMap().get(dictType));
            redisUtil.hset(RedisKey.REDIS_SYS_DICT, RedisKey.DICT_TYPE.concat(dictType), result);
        }
        return result;
    }

    /**
     * 分页查询
     *
     * @param pg
     * @param searchParams
     * @return
     */
    @Override
    public IPage<SysDict> pageData(Pg pg, SysDict.SearchParams searchParams) {
        return this.prepareReturnModel(this.page(pg.page(), searchParams.preWrapper(pg.wrapper())));
    }

    /**
     * 列表查询
     *
     * @param searchParams
     * @return
     */
    @Override
    public List<SysDict> listData(SysDict.SearchParams searchParams) {
        return this.prepareReturnModel(this.list(searchParams.preWrapper(null)));
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
                    <String, Object>> pageMapData(Pg pg, SysDict.SearchParams searchParams) {
        return mapper.pageMapData(pg.page(), searchParams);
    }

    /**
     * 处理返回值
     *
     * @param data
     * @return
     */
    @Override
    public SysDict prepareReturnModel(SysDict data) {
        return data;
    }

    /**
     * 处理返回值
     *
     * @param page
     * @return
     */
    @Override
    public IPage<SysDict> prepareReturnModel(IPage<SysDict> page) {
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
    public List<SysDict> prepareReturnModel(List<SysDict> list) {
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
    public void validData(SysDict data) {
        if (CheckUtil.objIsEmpty(data))
            throw NBException.create(EErrorCode.missingArg);
    }
}
