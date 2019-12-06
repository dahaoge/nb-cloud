package cn.hao.nb.cloud.common.penum;

import cn.hao.nb.cloud.common.entity.PEnum;

/**
 * @Auther: hao
 * @Date: 2019-12-06 21:34
 * @Description:
 */
public enum ESourceClient implements PEnum<String> {
    enterpriseApp("企业端APP"), webManageClient("web管理端");

    private String desc;

    ESourceClient(String desc) {
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