package cn.hao.nb.cloud.common.penum;

import cn.hao.nb.cloud.common.entity.PEnum;

/**
 * @Auther: hao
 * @Date: 2019-12-08 15:20
 * @Description:
 */
public enum ELoginChannelScop implements PEnum<String> {
    CClient("C端客户端"), manageClient("管理端");

    private String desc;

    ELoginChannelScop(String desc) {
        this.desc = desc;
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
}