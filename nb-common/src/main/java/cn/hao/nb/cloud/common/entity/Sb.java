package cn.hao.nb.cloud.common.entity;

import cn.hao.nb.cloud.common.util.CheckUtil;
import com.google.common.base.Strings;

/**
 * @Description: 文本构建器
 * @Author: scootXin
 * @Date: 2018/12/11 10:46
 */
public class Sb {
    /**
     * 拼接器
     */
    private StringBuilder builder;

    private Sb() {
    }

    public static Sb create() {
        Sb sb = new Sb();
        sb.builder = new StringBuilder();

        return sb;
    }

    public Sb add(Object o) {
        builder.append(o);

        return this;
    }

    public static String format(String base, Object... args) {
        if (CheckUtil.strIsNotEmpty(base)) {
            base = base.replaceAll("\\{\\}", "%s");
        }

        return Strings.lenientFormat(base, args);
    }

    @Override
    public String toString() {
        return builder.toString();
    }

}
