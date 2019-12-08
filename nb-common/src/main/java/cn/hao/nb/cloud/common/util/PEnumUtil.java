package cn.hao.nb.cloud.common.util;

import cn.hao.nb.cloud.common.entity.PEnum;
import cn.hao.nb.cloud.common.entity.Qd;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取枚举的工具类
 *
 * @Author: scootXin
 * @Date: 2018/12/5 10:42
 */
@Slf4j
@Data
public class PEnumUtil implements InitializingBean {
    /**
     * package
     */
    private final String findEnumPackage = "cn.hao.nb.cloud.common.penum";
    /**
     * 最后更新时间
     */
    private long lastUpTime;
    /**
     * 枚举map
     */
    private Map<String, List<Map<String, Object>>> rtnMap;

    public void afterPropertiesSet() throws Exception {
        rtnMap = new HashMap<String, List<Map<String, Object>>>();

        List<Class> classInPackage = ClassUtil.getAllClassByInterface(PEnum.class, findEnumPackage);

        //循环类
        String enumName;
        for (Class cls : classInPackage) {
            if (!cls.isEnum()) {
                continue;
            }

            enumName = getSingleEnumName(cls);
            List<Map<String, Object>> maps = initialSingleEnumMap(cls);

            rtnMap.put(enumName, maps);
        }

        lastUpTime = System.currentTimeMillis();
    }

    /**
     * 获取枚举名称
     *
     * @param cls
     * @return
     */
    private String getSingleEnumName(Class<?> cls) {
        String name = cls.getSimpleName();

        name = name.replace("Enum", "");
        name = name.substring(0, 1).toLowerCase() + name.substring(1);

        return name;
    }

    /**
     * 获取枚举map
     *
     * @return
     */
    private List<Map<String, Object>> initialSingleEnumMap(Class<?> cls) throws Exception {
        //获取枚举所有实例
        Object[] objs = cls.getEnumConstants();

        List<Map<String, Object>> linkedList = Lists.newLinkedList();
        for (Object obj : objs) {
            Method valMethod = cls.getMethod("getValue");
            Method descMethod = cls.getMethod("toChString");

            linkedList.add(Qd.create().add("val", valMethod.invoke(obj)).add("desc", descMethod.invoke(obj)));
        }

        return linkedList;
    }

    public Map<String, List<Map<String, Object>>> getRtnMap() {
        return rtnMap;
    }

    public long getLastUpTime() {
        return lastUpTime;
    }
}
