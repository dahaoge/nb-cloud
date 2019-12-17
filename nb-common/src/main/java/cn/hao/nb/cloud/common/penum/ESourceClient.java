package cn.hao.nb.cloud.common.penum;

import cn.hao.nb.cloud.common.entity.PEnum;

/**
 * @Auther: hao
 * @Date: 2019-12-06 21:34
 * @Description:
 */
public enum ESourceClient implements PEnum<String> {
    app2c("C端App"), web2c("C端Web"), app2b("C端App"), web2b("C端Web"), web2Manager("管理端Web"), app2Manager("管理端App");

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