package cn.hao.nb.cloud.ydgl.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hao
 * @Date: 2020/4/13 10:44
 * @Description:
 */
@Data
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    String id;
    String name;
    String phone;

}