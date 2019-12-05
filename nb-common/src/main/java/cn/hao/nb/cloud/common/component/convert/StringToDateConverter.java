package cn.hao.nb.cloud.common.component.convert;

import cn.hao.nb.common.entity.NBException;
import cn.hao.nb.common.penum.EErrorCode;
import cn.hao.nb.common.util.CheckUtil;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * 时间转换工具
 *
 * @Author: scootXin
 * @Date: 2018/12/5 11:26
 */
public class StringToDateConverter implements Converter<String, Date> {

    DateTimeFormatter dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    DateTimeFormatter shortDateFormat = DateTimeFormat.forPattern("yyyy-MM-dd");

    @Override
    public Date convert(String value) {

        if (CheckUtil.strIsEmpty(value)) {
            return null;
        }

        value = value.trim();

        try {
            if (value.contains("-")) {
                Date dtDate;

                if (value.contains(":")) {
                    dtDate = dateFormat.parseDateTime(value).toDate();
                } else {
                    dtDate = shortDateFormat.parseDateTime(value).toDate();
                }

                return dtDate;
            } else if (value.matches("^\\d+$")) {
                Long lDate = new Long(value);
                return new Date(lDate);
            }
        } catch (Exception e) {
            throw new NBException(EErrorCode.dataParseError, String.format("parser %s to Date fail", value));
        }
        throw new NBException(EErrorCode.dataParseError, String.format("parser %s to Date fail", value));
    }
}
