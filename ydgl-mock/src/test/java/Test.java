import cn.hao.nb.cloud.common.util.ClassUtil;

import java.util.List;

/**
 * @Auther: hao
 * @Date: 2020/4/20 14:54
 * @Description:
 */
public class Test {

    public static void main(String[] args) {
        new Test().printBean();
    }

    public void printBean() {
        List<Class<?>> classes = ClassUtil.getClasses("cn.hao.nb.cloud.ydglExternalApi.entity");
        classes.forEach(item -> {
            String methodName = "get".concat(item.getSimpleName());
            System.out.println("@GetMapping(\"/".concat(methodName).concat("\")"));
            System.out.println("public ".concat(item.getSimpleName()).concat(" ").concat(methodName).concat("(){return null;}"));
        });
    }
}
