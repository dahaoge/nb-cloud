package cn.hao.nb.cloud.common.penum;

import cn.hao.nb.cloud.common.entity.PEnum;

/**
 * @Auther: hao
 * @Date: 2020/4/12 17:18
 * @Description:
 */
public enum ECompanyRequestSuffixKey implements PEnum<String> {
    loadDept("拉取组织架构信息");

    private String desc;

    ECompanyRequestSuffixKey(String desc) {
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