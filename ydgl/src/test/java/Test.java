import java.util.Date;


/**
 * @Auther: hao
 * @Date: 2020/4/12 17:56
 * @Description:
 */
public class Test {

    public static void main(String[] args) {
        System.out.println(null instanceof Date);
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
