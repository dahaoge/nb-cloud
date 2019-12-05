package cn.hao.nb.cloud.common.entity;


/**
 * @Author: scootXin
 * @Date: 2018/12/4 18:26
 */

import com.baomidou.mybatisplus.core.enums.IEnum;

import java.io.Serializable;

public interface PEnum<T extends Serializable> extends IEnum<T> {
    /**
     * 重写toString方法用于json输出
     *
     * @return
     */
    @Override
    public String toString();

    /**
     * 新增toShowString方法用于获取中文显示内容
     *
     * @return
     */
    public String toChString();
}