package cn.hao.nb.cloud.common.entity;


import cn.hao.nb.cloud.common.penum.EErrorCode;
import cn.hao.nb.cloud.common.util.CheckUtil;
import cn.hao.nb.cloud.common.util.MapUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 接口返回样式
 */
@Data
@ApiModel(value = "返回实体", description = "返回实体")
public class Rv<T> implements Serializable {

    //错误码
    @ApiModelProperty(value = "错误码，SUCCESS ： 成功 OTHER： 错误")
    private int code;

    //返回msg
    @ApiModelProperty(value = "状态描述")
    private String msg;

    //返回数据
    @ApiModelProperty(value = "返回数据")
    private T data;

    //返回数据
    @ApiModelProperty(value = "其他信息")
    private Qd relatedData;

    private Rv() {
    }

    /**
     * 获取实例
     *
     * @param data
     * @return
     */
    public static <T> Rv getInstance(T data) {
        Rv rv = new Rv();

        rv.setCode(EErrorCode.success.getValue());
        rv.setMsg(EErrorCode.success.toChString());
        rv.setData(data);
        return rv;
    }

    public <T> Rv add(String key, Object value) {
        if (CheckUtil.objIsEmpty(this.getRelatedData())) {
            this.setRelatedData(Qd.create());
        }
        this.getRelatedData().add(key, value);
        return this;
    }

    /**
     * 获取实例
     *
     * @param msg
     * @param data
     * @return
     */
    public static <T> Rv getInstance(EErrorCode code, String msg, T data) {
        Rv rv = new Rv();

        rv.setCode(code.getValue());
        rv.setMsg(msg != null ? msg : code.getDesc());
        rv.setData(data);
        return rv;
    }

    /**
     * 获取实例
     *
     * @param data
     * @return
     */
    public static <T> Rv getInstance(EErrorCode code, T data) {
        Rv rv = new Rv();

        rv.setCode(code.getValue());
        rv.setMsg(code.getDesc());
        rv.setData(data);
        return rv;
    }

    public boolean isSuc() {
        return this.getCode() == EErrorCode.success.getValue().intValue();
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public void setData(T data) {
        Class classz = Map.class;
        if (data != null && classz.isAssignableFrom(data.getClass())) {
            MapUtil.camelTheKey((Map) data);
        } else {
            Iterator var4;
            Object o;
            if (data != null && IPage.class.isAssignableFrom(data.getClass())) {
                IPage pd = (IPage) data;
                var4 = pd.getRecords().iterator();

                while (var4.hasNext()) {
                    o = var4.next();
                    if (classz.isAssignableFrom(o.getClass())) {
                        MapUtil.camelTheKey((Map) o);
                    }
                }
            } else if (data != null && List.class.isAssignableFrom(data.getClass())) {
                List list = (List) data;
                var4 = list.iterator();

                while (var4.hasNext()) {
                    o = var4.next();
                    if (classz.isAssignableFrom(o.getClass())) {
                        MapUtil.camelTheKey((Map) o);
                    }
                }
            }
        }

        this.data = data;
    }
}
