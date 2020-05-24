import cn.hao.nb.cloud.common.entity.Qd;
import cn.hao.nb.cloud.common.util.CheckUtil;
import cn.hao.nb.cloud.common.util.ClassUtil;
import cn.hao.nb.cloud.ydglExternalApi.entity.CurrentItem;
import cn.hao.nb.cloud.ydglExternalApi.entity.ElecQualityStatistics;
import com.github.javafaker.Faker;
import com.google.common.collect.Lists;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * @Auther: hao
 * @Date: 2020/4/20 14:54
 * @Description:
 */
public class Test {

    Faker faker = Faker.instance(new Locale("zh", "CN"));

    public static void main(String[] args) {
        String path1 = "https://xqc.ymjt.com.cn/ymapi/";
        String path2 = "https://xqc.ymjt.com.cn/ymapi";
        String suffix1 = "/company/add";
        String suffix2 = "company/add";
        System.out.println(path1.endsWith("/"));
        System.out.println(path2.endsWith("/"));
        System.out.println(suffix1.startsWith("/"));
        System.out.println(suffix2.startsWith("/"));
        System.out.println(path1.substring(0, path1.length() - 1));
    }

    public void test() {
        ElecQualityStatistics data = new ElecQualityStatistics();
        for (Field field : data.getClass().getDeclaredFields()) {
            System.out.println("name--" + field.getName());
            System.out.println("type--" + field.getGenericType());
            if (field.getGenericType().toString().contains(data.getClass().getPackage().getName())) {
                try {
//                    Object item=field.getDeclaringClass().newInstance();
//                    System.out.println(item);
                    System.out.println(field.getType().getDeclaredConstructors().length);
                    if (field.getType().getDeclaredConstructors().length > 0) {
                        Constructor c = field.getType().getDeclaredConstructors()[0];
                        System.out.println(c);
                        c.setAccessible(true);
                        if (c.getParameterCount() == 1)
                            System.out.println(c.newInstance(data.getClass().newInstance()));
                        else System.out.println(c.newInstance());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setEntityData(Object data) {
        if (CheckUtil.objIsEmpty(data))
            return;
        if (HashMap.class.getName().equals(data.getClass().getName()) || Qd.class.getName().equals(data.getClass().getName())) {
            HashMap map = (HashMap) data;
            map.keySet().forEach(item -> {
                this.setEntityData(item);
            });
        } else if (ArrayList.class.getName().equals(data.getClass().getName())) {
            List list = (List) data;
            list.forEach(item -> {
                this.setEntityData(item);
            });
        } else if (CurrentItem.class.getPackage().getName().equals(data.getClass().getPackage().getName())) {
            try {
                List<String> methodNames = Lists.newArrayList();
                for (Method item : data.getClass().getMethods()) {
                    methodNames.add(item.getName());
                }
                Field[] fields = data.getClass().getDeclaredFields(); //获取实体类的所有属性，返回Field数组
                for (Field itemField : fields) {

                    String name = itemField.getName(); //获取属性的名字
                    System.out.println("name--" + name);
                    name = name.substring(0, 1).toUpperCase() + name.substring(1); //将属性的首字符大写，方便构造get，set方法
                    if ("SerialVersionUID".equals(name) || !methodNames.contains("set" + name))
                        continue;
                    String type = itemField.getGenericType().toString(); //获取属性的类型
                    Object o = null;
                    Method setMethod = data.getClass().getMethod("set".concat(name), itemField.getType());
                    if (type.contains(CurrentItem.class.getPackage().getName())) {
                        if (itemField.getType().getDeclaredConstructors().length > 0) {
                            Constructor c = itemField.getType().getDeclaredConstructors()[0];
                            c.setAccessible(true);
                            if (c.getParameterCount() == 1)
                                o = c.newInstance(data.getClass().newInstance());
                            else
                                o = c.newInstance();
                            this.setEntityData(o);
                        }

//                    this.setEntityData();
                    } else if (type.contains("java.lang.String")) {
                        o = faker.name().title();
                    } else if (type.contains("java.lang.Integer")) {
                        o = faker.number().randomDigitNotZero();
                    } else if (type.contains("java.lang.Long")) {
                        o = faker.number().randomNumber();
                    } else if (type.contains("java.lang.Float")) {
                        o = 3.1415F;
                    } else if (type.contains("java.math.BigDecimal")) {
                        o = new BigDecimal("3.1415");
                    } else if (type.contains("java.lang.Double")) {
                        o = 3.1415D;
                    } else if (type.contains("java.util.Date")) {
                        o = faker.date().birthday();
                    }
                    System.out.println(o);
                    setMethod.invoke(data, o);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void printBean() {
        List<Class<?>> classes = ClassUtil.getClasses("cn.hao.nb.cloud.ydglExternalApi.entity");
        classes.forEach(item -> {
            String methodName = "get".concat(item.getSimpleName());
            System.out.println("@GetMapping(\"/".concat(methodName).concat("\")"));
            System.out.println("public ".concat(item.getSimpleName()).concat(" ").concat(methodName).concat("(){return null;}"));
        });
        List<Class<?>> classes1 = ClassUtil.getClasses("cn.hao.nb.cloud.auth.entity");
        classes1.forEach(item -> {
            String methodName = "get".concat(item.getSimpleName());
            System.out.println("@GetMapping(\"/".concat(methodName).concat("\")"));
            System.out.println("public ".concat(item.getSimpleName()).concat(" ").concat(methodName).concat("(){return null;}"));
        });
        List<Class<?>> classes2 = ClassUtil.getClasses("cn.hao.nb.cloud.basic.entity");
        classes2.forEach(item -> {
            String methodName = "get".concat(item.getSimpleName());
            System.out.println("@GetMapping(\"/".concat(methodName).concat("\")"));
            System.out.println("public ".concat(item.getSimpleName()).concat(" ").concat(methodName).concat("(){return null;}"));
        });
        List<Class<?>> classes3 = ClassUtil.getClasses("cn.hao.nb.cloud.ydgl.entity");
        classes3.forEach(item -> {
            String methodName = "get".concat(item.getSimpleName());
            System.out.println("@GetMapping(\"/".concat(methodName).concat("\")"));
            System.out.println("public ".concat(item.getSimpleName()).concat(" ").concat(methodName).concat("(){return null;}"));
        });
    }
}
