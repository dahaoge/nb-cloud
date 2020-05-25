package cn.hao.nb.cloud.ydglMock.Aspect;

import cn.hao.nb.cloud.common.entity.Qd;
import cn.hao.nb.cloud.common.entity.Rv;
import cn.hao.nb.cloud.common.util.CheckUtil;
import cn.hao.nb.cloud.ydglExternalApi.entity.CurrentItem;
import com.alibaba.fastjson.JSON;
import com.github.javafaker.Faker;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.Annotation;
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
 * @Date: 2020/5/22 16:22
 * @Description:
 */
@Configuration
@Aspect
@Slf4j
public class RespAspect {

    private final String ExpGetResultDataPonit = "execution(* cn.hao.nb.cloud.ydglMock.controller..*.*(..))";

    Faker faker = Faker.instance(new Locale("zh", "CN"));

    /**
     * 后置返回通知
     * 这里需要注意的是:
     * 如果参数中的第一个参数为JoinPoint，则第二个参数为返回值的信息
     * 如果参数中的第一个参数不为JoinPoint，则第一个参数为returning中对应的参数
     * returning 限定了只有目标方法返回值与通知方法相应参数类型时才能执行后置返回通知，否则不执行，对于returning对应的通知方法参数为Object类型将匹配任何目标返回值
     */
    @AfterReturning(value = ExpGetResultDataPonit, returning = "keys")
    public void doAfterReturningAdvice(JoinPoint joinPoint, Object keys) {
        if (keys instanceof Rv && CheckUtil.objIsNotEmpty(((Rv) keys).getData())) {
            Rv rv = (Rv) keys;
            this.setEntityData(rv.getData());
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
                    String desc = "";
                    for (Annotation annotation : itemField.getAnnotations()) {
                        if (annotation.annotationType().getName().contains("ApiModelProperty"))
                            desc = JSON.parseObject(JSON.toJSONString(annotation)).getString("value");
                    }
                    String name = itemField.getName(); //获取属性的名字
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
                        o = desc;
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
                    setMethod.invoke(data, o);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
