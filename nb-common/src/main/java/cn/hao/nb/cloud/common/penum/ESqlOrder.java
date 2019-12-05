package cn.hao.nb.cloud.common.penum;

import cn.hao.nb.cloud.common.entity.PEnum;

/**
 * @Auther: hao
 * @Date: 2019-12-05 16:20
 * @Description:
 */
public enum ESqlOrder implements PEnum<String> {
    ASC("升序"), DESC("降序");

    private String desc;

    ESqlOrder(String desc) {
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