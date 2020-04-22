import com.alibaba.fastjson.JSON;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * @Auther: hao
 * @Date: 2020/4/22 16:29
 * @Description:
 */
public class Test {

    public static void main(String[] args) {
        Multimap map = ArrayListMultimap.create();
        map.put("a", 1);
        map.put("a", 2);
        map.put("a", 3);
        map.put("b", 1);
        map.put("b", 2);
        map.put("b", 3);
        System.out.println(JSON.toJSON(map));
        System.out.println(JSON.toJSON(map.entries()));
        System.out.println(JSON.toJSON(map.values()));
        System.out.println(JSON.toJSON(map.keys()));
        System.out.println(JSON.toJSON(map.keySet()));
        System.out.println(JSON.toJSON(map.asMap()));
    }
}
