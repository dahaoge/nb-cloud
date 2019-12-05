package cn.hao.nb.cloud.common.component.convert;

import cn.hao.nb.cloud.common.util.CheckUtil;
import org.springframework.core.convert.converter.Converter;

/**
 * 时间转换工具
 *
 * @Author: scootXin
 * @Date: 2018/12/5 11:26
 */
public class LongToStringConverter implements Converter<Long, String> {

    @Override
    public String convert(Long value) {

        if (CheckUtil.objIsEmpty(value)) {
            return null;
        }

        return Long.toString(value);
    }
}
