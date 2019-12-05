package cn.hao.nb.cloud.common.util;

import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.entity.Qd;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * map操作工具
 */
public class MapUtil {
    /**
     * 根据entity获取map
     *
     * @param c
     * @return
     */
    public static Qd getMapFromEntity(Object c) {
        Qd dataMap = Qd.create();

        if (c == null) {
            return dataMap;
        }

        try {
            Class<?> aClass = c.getClass();

            Method[] methodArr = aClass.getMethods();
            for (Method method : methodArr) {
                String methodName = method.getName();

                if (methodName.startsWith("get")) {
                    Object value = method.invoke(c);

                    // 从第三位开始截取
                    String key = methodName.substring(3);
                    // 经典
                    key = key.substring(0, 1).toLowerCase() + key.substring(1);

                    dataMap.put(key, value);
                }
            }

        } catch (Exception e) {
            throw new NBException("", e);
        }

        return dataMap;
    }

    /**
     * key 下划线转驼峰
     */
    public static void camelTheKey(Map resultMap) {
        camelTheKey(resultMap, true);
    }

    private static void camelTheKey(Map resultMap, Boolean isFirst) {
        Map newEntryMap = new HashMap();
        Iterator mapIterator = resultMap.entrySet().iterator();

        while (mapIterator.hasNext()) {
            Map.Entry me = (Map.Entry) mapIterator.next();

            if (isFirst && me.getValue() != null && Map.class.isAssignableFrom(me.getValue().getClass())) {
                camelTheKey((Map) me.getValue(), true);
            } else if (isFirst && me.getValue() != null && List.class.isAssignableFrom(me.getValue().getClass())) {
                List list = (List) me.getValue();

                for (Object o : list) {
                    if (Map.class.isAssignableFrom(o.getClass())) {
                        MapUtil.camelTheKey((Map) o, true);
                    }
                }
            } else if (isFirst && me.getValue() != null && Page.class.isAssignableFrom(me.getValue().getClass())) {
                Page pd = (Page) me.getValue();

                for (Object o : pd.getRecords()) {
                    if (Map.class.isAssignableFrom(o.getClass())) {
                        MapUtil.camelTheKey((Map) o, true);
                    }
                }
            }

            //转换后key
            String newKey = me.getKey().toString();
            if (newKey.indexOf("_") > 0) {
                newKey = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, me.getKey().toString());
            }

            if (resultMap.containsKey(newKey)) {
                continue;
            }

            newEntryMap.put(newKey, me.getValue());
            mapIterator.remove();
        }

        //拼接map
        resultMap.putAll(newEntryMap);
    }
}
