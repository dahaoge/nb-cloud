package cn.hao.nb.cloud.basic.entity;

import cn.hao.nb.cloud.common.entity.Qw;
import cn.hao.nb.cloud.common.penum.ESqlOrder;
import cn.hao.nb.cloud.common.util.CheckUtil;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 数据字典
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "SysDict对象", description = "数据字典 ")
public class SysDict implements Serializable {

    @Data
    public class SearchParams {


        @ApiModelProperty(value = "排序字段")
        private String sort = "update_time";

        @ApiModelProperty(value = "排序方式,可选值:ASC/DESC")
        private ESqlOrder order = ESqlOrder.DESC;

        public Qw<SysDict> preWrapper(Qw<SysDict> qw) {
            if (CheckUtil.objIsEmpty(qw))
                qw = Qw.create();
            if (ESqlOrder.DESC.equals(this.getOrder()))
                qw.orderByDesc(this.getSort());
            else
                qw.orderByAsc(this.getSort());
            return qw;
        }
    }

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "创建人信息")
    @TableField(exist = false)
    private RedisUser createUserMap;

    @ApiModelProperty(value = "修改人信息")
    @TableField(exist = false)
    private RedisUser updateUserMap;

    @ApiModelProperty(value = "乐观锁")
    @Version
    private Integer version;

    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "删除标记")
    @TableLogic
    private Integer deleted = 0;

    @TableId(value = "dict_id", type = IdType.INPUT)
    private String dictId;

    private String dictType;

    private String dictCode;

    private String dictLabel;

    private String dictDesc;

    @ApiModelProperty(value = "字典排序")
    private Integer dictIndex;


    public static final String VERSION = "version";

    public static final String CREATE_BY = "create_by";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_BY = "update_by";

    public static final String UPDATE_TIME = "update_time";

    public static final String DELETED = "deleted";

    public static final String DICT_ID = "dict_id";

    public static final String DICT_TYPE = "dict_type";

    public static final String DICT_CODE = "dict_code";

    public static final String DICT_LABEL = "dict_label";

    public static final String DICT_DESC = "dict_desc";

    public static final String DICT_INDEX = "dict_index";

}