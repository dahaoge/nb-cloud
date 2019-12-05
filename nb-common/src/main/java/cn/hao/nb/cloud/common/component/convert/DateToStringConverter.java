package cn.hao.nb.cloud.common.component.convert;

import cn.hao.nb.cloud.common.util.CheckUtil;
import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间转换工具
 *
 * @Author: scootXin
 * @Date: 2018/12/5 11:26
 */
public class DateToStringConverter implements Converter<Date, String> {

    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public String convert(Date value) {

        if (CheckUtil.objIsEmpty(value)) {
            return null;
        }

        return DATE_FORMAT.format(value);
    }
}
