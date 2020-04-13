import com.google.common.collect.Lists;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


/**
 * @Auther: hao
 * @Date: 2020/4/12 17:56
 * @Description:
 */
public class Test {

    public static void main(String[] args) {
        MultiValueMap map1 = new LinkedMultiValueMap<String, Object>();
        System.out.println(MultiValueMap.class.isAssignableFrom(map1.getClass()));
        System.out.println(MultiValueMap.class.isAssignableFrom(Lists.newArrayList().getClass()));
    }

    public static boolean a() {
        System.out.println("a");
        return true;
    }

    public static boolean b() {
        System.out.println("b");
        return false;
    }
}
