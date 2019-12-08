package cn.hao.nb.cloud.common.penum;

import cn.hao.nb.cloud.common.entity.PEnum;

/**
 * @Auther: sunhao
 * @Date: 2019-07-19 14:46
 * @Description:
 */
public enum EMenuType implements PEnum<String> {
    module("模块"), func_group("功能组"), func("功能");

    private String desc;

    EMenuType(String desc) {
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