package cn.hao.nb.cloud.common.penum;

import cn.hao.nb.cloud.common.entity.PEnum;

/**
 * @Auther: hao
 * @Date: 2020/4/14 14:41
 * @Description:
 */
public enum EModuleRequestPrefix implements PEnum<String> {
    auth("http://nb-auth:", "组织权限管理"),
    basic("http://nb-basic:", "基础信息管理"),
    ydgl("http://ydgl:", "用电能效管理");

    private String desc;
    private String prefix;

    EModuleRequestPrefix(String prefix, String desc) {
        this.desc = desc;
        this.prefix = prefix;
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

    public String getPrefix() {
        return prefix;
    }
}
