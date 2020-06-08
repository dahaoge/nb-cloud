import java.net.URLEncoder;


/**
 * @Auther: hao
 * @Date: 2020/4/12 17:56
 * @Description:
 */
public class Test {

    public static void main(String[] args) throws Exception {
        String url = "deptId=1&time=2020-01-01 01:01:01";
        System.out.println(URLEncoder.encode(url, "UTF-8"));
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
