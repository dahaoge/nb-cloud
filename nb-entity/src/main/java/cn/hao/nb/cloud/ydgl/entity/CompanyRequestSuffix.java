package cn.hao.nb.cloud.ydgl.entity;

import cn.hao.nb.cloud.common.entity.Qw;
import cn.hao.nb.cloud.common.entity.RedisUser;
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
 * 公司请求后缀
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2020-04-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ydgl_company_request_suffix")
@ApiModel(value = "CompanyRequestSuffix对象", description = "公司请求后缀 ")
public class CompanyRequestSuffix implements Serializable {


    public static SearchParams getSearchParamInstance() {
        return new CompanyRequestSuffix().new SearchParams();
    }

    @Data
    public class SearchParams {


        @ApiModelProperty(value = "排序字段")
        private String sort = CompanyRequestSuffix.UPDATE_TIME;

        @ApiModelProperty(value = "排序方式,可选值:ASC/DESC")
        private ESqlOrder order = ESqlOrder.DESC;

        public Qw<CompanyRequestSuffix> preWrapper(Qw<CompanyRequestSuffix> qw) {
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

    @ApiModelProperty(value = "创建人")
    private Long createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private Long updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "删除标记")
    @TableLogic
    private Integer deleted = 0;

    @ApiModelProperty(value = "ID")
    @TableId(value = "t_id", type = IdType.INPUT)
    private Long tId;

    @ApiModelProperty(value = "公司id")
    private Long comId;

    @ApiModelProperty(value = "请求枚举")
    private String enumKey;

    @ApiModelProperty(value = "请求后缀")
    private String requestSuffix;


    public static final String VERSION = "version";

    public static final String CREATE_BY = "create_by";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_BY = "update_by";

    public static final String UPDATE_TIME = "update_time";

    public static final String DELETED = "deleted";

    public static final String T_ID = "t_id";

    public static final String COM_ID = "com_id";

    public static final String ENUM_KEY = "enum_key";

    public static final String REQUEST_SUFFIX = "request_suffix";

}