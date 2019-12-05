package cn.hao.nb.cloud.common.entity;

import cn.hao.nb.cloud.common.penum.ESqlOrder;
import cn.hao.nb.cloud.common.util.ConvertUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Auther: sunhao
 * @Date: 2019/3/15 17:07
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "分页对象", description = "分页对象 ")
public class Pg implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "当前页", required = true)
    private Integer current = 1;
    @ApiModelProperty(value = "每页size", required = true)
    private Integer size = 10;
    @ApiModelProperty(value = "总数")
    private Long total = 0L;
    @ApiModelProperty(value = "排序字段")
    private String sort = "update_time";
    @ApiModelProperty(value = "排序方式,可选值:ASC/DESC")
    private ESqlOrder order = ESqlOrder.DESC;

    public Pg(Integer current, Integer size) {
        this.current = current;
        this.size = size;
    }

    public static Page page(Integer current, Integer size) {
        Page page = new Page();
        page.setCurrent(current).setSize(size);
        return page;
    }

    public String getSort() {
        return ConvertUtil.toUnderLine(this.sort);
    }

    public Page page() {
        Page page = new Page();
        page.setCurrent(this.getCurrent()).setSize(this.getSize()).setTotal(this.getTotal());
        return page;
    }

    public Qw wrapper() {
        if (ESqlOrder.DESC == this.getOrder()) {
            return Qw.create().orderByDesc(this.getSort().split(","));
        } else {
            return Qw.create().orderByAsc(this.getSort().split(","));
        }
    }

    public Qw wrapper(Qw qw) {
        if (ESqlOrder.DESC == this.getOrder()) {
            return qw.orderByDesc(this.getSort().split(","));
        } else {
            return qw.orderByAsc(this.getSort().split(","));
        }
    }
}
