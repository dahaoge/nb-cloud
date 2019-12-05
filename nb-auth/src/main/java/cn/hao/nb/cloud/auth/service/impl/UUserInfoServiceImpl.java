package cn.hao.nb.cloud.auth.service.impl;

import cn.hao.nb.cloud.auth.entity.UUserInfo;
import cn.hao.nb.cloud.auth.mapper.UUserInfoMapper;
import cn.hao.nb.cloud.auth.service.IUUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表  服务实现类
 * </p>
 *
 * @author scootxin@163.com
 * @since 2019-11-28
 */
@Service
public class UUserInfoServiceImpl extends ServiceImpl<UUserInfoMapper, UUserInfo> implements IUUserInfoService {

}
