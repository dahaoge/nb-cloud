package cn.hao.nb.cloud.common.penum;

import cn.hao.nb.cloud.common.entity.PEnum;
import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * @Auther: hao
 * @Date: 2019-12-17 16:43
 * @Description:
 */
public enum EUserType implements PEnum<String> {
    manager(ImmutableList.of(ELoginChannelScop.manageClient, ELoginChannelScop.BClient, ELoginChannelScop.CClient), "管理员"),
    bUser(ImmutableList.of(ELoginChannelScop.BClient, ELoginChannelScop.CClient), "B端用户"),
    cUser(ImmutableList.of(ELoginChannelScop.CClient), "C端用户");

    private String desc;
    private List<ELoginChannelScop> loginChannelScops;

    EUserType(List<ELoginChannelScop> loginChannelScops, String desc) {
        this.desc = desc;
        this.loginChannelScops = loginChannelScops;
    }

    @Override
    public String getValue() {
        return name();
    }

    @Override
    public String toString() {
        return name();
    }

    @Override
    public String toChString() {
        return desc;
    }

    public List<ELoginChannelScop> getLoginChannelScops() {
        return this.loginChannelScops;
    }
}