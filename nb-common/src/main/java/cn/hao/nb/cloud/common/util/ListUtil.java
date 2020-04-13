package cn.hao.nb.cloud.common.util;

import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.penum.EMethod;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multiset;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 列表工具
 */
public class ListUtil {

    /**
     * 根据数组获取list
     *
     * @param ts
     * @param <T>
     * @return
     */
    public static <T> List<T> getListByArray(T... ts) {
        if (ts == null || ts.length == 0) {
            return Lists.newArrayList();
        }

        LinkedList<T> l = Lists.newLinkedList();

        for (T t : ts) {
            if (t == null) {
                continue;
            }

            l.add(t);
        }

        return l;
    }

    /**
     * 根据实体获取map，用于进行快捷匹配
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> Map<T, Byte> getMapFormList(List<T> list) {
        if (CheckUtil.collectionIsEmpty(list)) {
            return null;
        }

        Map<T, Byte> lk = Maps.newHashMap();
        for (T t : list) {
            lk.put(t, Byte.MIN_VALUE);
        }
        return lk;
    }

    /**
     * 根据指定字段对list进行分组并返回map
     *
     * @param list
     * @param key
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T, V> MultiValueMap<V, T> getMapFromList(List<T> list, String key) {
        if (CheckUtil.collectionIsEmpty(list) || CheckUtil.strIsEmpty(key)) {
            return null;
        }
        //返回实体
        MultiValueMap<V, T> multiValueMap = new LinkedMultiValueMap<>();

        Class clasz = list.get(0).getClass();

        //如果传入为map
        if (Map.class.isAssignableFrom(clasz)) {
            for (T t : list) {
                V val;
                Map m = (Map) t;
                val = (V) m.get(key);

                multiValueMap.add(val, t);
            }
        } else {
            try {
                Method method = clasz.getMethod(ConvertUtil.makeMethodName(key, EMethod.get));

                for (T t : list) {
                    V val = (V) method.invoke(t);
                    multiValueMap.add(val, t);
                }
            } catch (Exception e) {
                return multiValueMap;
            }
        }

        return multiValueMap;
    }

    /**
     * 获取非重复列表
     *
     * @param listWithDup
     * @param <T>
     * @return
     */
    public static <T> List<T> getListWithOutDup(List<T> listWithDup) {
        if (CheckUtil.collectionIsEmpty(listWithDup)) {
            return listWithDup;
        }

        return new ArrayList<T>(new HashSet<T>(listWithDup));
    }


    /**
     * 获取实体的主键列表
     *
     * @param targetList
     * @return
     */
    public static <T, V> List<V> getPkList(List<T> targetList, String pkName) {
        try {
            List<V> rtn = Lists.newLinkedList();

            if (targetList == null || targetList.size() == 0 || Strings.isNullOrEmpty(pkName)) {
                return rtn;
            }

            Class<?> aClass = targetList.get(0).getClass();
            Method method = aClass.getMethod(ConvertUtil.makeMethodName(pkName, EMethod.get));


            for (T target : targetList) {
                rtn.add((V) method.invoke(target));
            }

            return ListUtil.getListWithOutDup(rtn);

        } catch (Exception e) {
            throw NBException.create(e);
        }
    }

    /**
     * 获取实体的主键列表
     *
     * @param targetList
     * @return
     */
    public static <T, V> List<V> getPkListWithOutEmpty(List<T> targetList, String pkName) {
        try {
            List<V> rtn = Lists.newLinkedList();

            if (targetList == null || targetList.size() == 0 || Strings.isNullOrEmpty(pkName)) {
                return rtn;
            }

            Class<?> aClass = targetList.get(0).getClass();
            Method method = aClass.getMethod(ConvertUtil.makeMethodName(pkName, EMethod.get));


            for (T target : targetList) {
                if (CheckUtil.objIsNotEmpty(method.invoke(target)))
                    rtn.add((V) method.invoke(target));
            }

            return ListUtil.getListWithOutDup(rtn);

        } catch (Exception e) {
            throw NBException.create(e);
        }
    }

    /**
     * 拼接list内容 join([a,b,c], ",") => a,b,c
     *
     * @param <T>
     * @return
     */
    public static <T> String join(List<T> iterator) {
        if (null == iterator) {
            return null;
        } else {
            return Joiner.on(",").skipNulls().join(iterator);
        }
    }

    public static <T> String join(List<T> iterator, String joinOn) {
        if (null == iterator)
            return null;
        joinOn = CheckUtil.strIsEmpty(joinOn) ? "," : joinOn;
        return Joiner.on(joinOn).skipNulls().join(iterator);
    }

    public static List<String> spliteCreate(String source) {
        return spliteCreate(source, ",");
    }

    /**
     * 分割并生成list
     *
     * @return
     */
    public static List<String> spliteCreate(String source, String splite) {
        if (CheckUtil.strIsEmpty(source, splite)) {
            return null;
        }

        return Splitter.on(splite).omitEmptyStrings().trimResults().splitToList(source);
    }

    public static List<Long> spliteCreateLong(String source) {
        return spliteCreateLong(source, ",");
    }

    /**
     * 分割并生成long格式list
     *
     * @return
     */
    public static List<Long> spliteCreateLong(String source, String splite) {
        if (CheckUtil.strIsEmpty(source, splite)) {
            return null;
        }

        String[] xs = source.split(splite);
        List<Long> rtn = Lists.newLinkedList();

        for (String x : xs) {
            rtn.add(ConvertUtil.toLong(x.trim()));
        }

        return rtn;
    }

    /**
     * 删除重复数据 并保持顺序
     *
     * @param list
     */
    public static void removeDuplicateWithOrder(List list) {
        if (CheckUtil.collectionIsNotEmpty(list)) {
            Multiset multiset = HashMultiset.create();
            list.forEach(item -> multiset.add(item));
            list.clear();
            list.addAll(multiset.elementSet());
        }
    }
}
