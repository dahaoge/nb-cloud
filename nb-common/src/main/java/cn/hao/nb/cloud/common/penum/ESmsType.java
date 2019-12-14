package cn.hao.nb.cloud.common.penum;

import cn.hao.nb.cloud.common.entity.PEnum;

/**
 * @Auther: hao
 * @Date: 2019-12-13 21:57
 * @Description:
 */
public enum ESmsType implements PEnum<String> {
    phoneCheck("验证码", "SMS_126575535");
    private String desc;
    private String modelCode;

    ESmsType(String desc, String modelCode) {
        this.desc = desc;
        this.modelCode = modelCode;
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

    public String getModelCode() {
        return modelCode;
    }
}
