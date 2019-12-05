package cn.hao.nb.cloud.common.entity;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 查询封装
 *
 * @author 辛鑫
 */
public class Qd extends HashMap<String, Object> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2038985333128360376L;

    /**
     * 初始化方法
     */
    private Qd() {
        super();
    }

    /**
     * 同初始化方法
     *
     * @return
     */
    public static Qd create() {
        return new Qd();
    }

    /**
     * 新增参数
     *
     * @param key
     * @param val
     * @return
     */
    public Qd add(String key, Object val) {

        //枚举类型匹配需要明确的指定取值如 EErrorCode.repet.name()  或者 EErrorCode.repet.getVal()
        if (val != null && val.getClass().isEnum() && PEnum.class.isAssignableFrom(val.getClass())) {
            put(key, ((PEnum) val).toString());
        } else {
            put(key, val);
        }

        return this;
    }
}
