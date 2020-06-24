package cn.hao.nb.cloud.basic.entity;

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
 * 用户app版本
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2020-06-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "UUserAppVersion对象", description = "用户app版本")
public class UUserAppVersion implements Serializable {


    public static SearchParams getSearchParamInstance() {
        return new UUserAppVersion().new SearchParams();
    }

    @Data
    public class SearchParams {


        @ApiModelProperty(value = "排序字段")
        private String sort = UUserAppVersion.UPDATE_TIME;

        @ApiModelProperty(value = "排序方式,可选值:ASC/DESC")
        private ESqlOrder order = ESqlOrder.DESC;

        public Qw<UUserAppVersion> preWrapper(Qw<UUserAppVersion> qw) {
            if (CheckUtil.objIsEmpty(qw))
                qw = Qw.create();

            if (CheckUtil.strIsEmpty(this.getSort()))
                this.setSort(SysDict.UPDATE_TIME);
            if (CheckUtil.objIsEmpty(this.getOrder()))
                this.setOrder(ESqlOrder.DESC);

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
    @TableId(value = "user_app_id", type = IdType.INPUT)
    private Long userAppId;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "app")
    private String app;

    @ApiModelProperty(value = "APP平台")
    private String appPlatform;

    @ApiModelProperty(value = "版本")
    private String appVersion;

    @ApiModelProperty(value = "版本类型")
    private String appVersionType;

    @ApiModelProperty(value = "编号")
    private Integer versionNum;


    public static final String VERSION = "version";

    public static final String CREATE_BY = "create_by";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_BY = "update_by";

    public static final String UPDATE_TIME = "update_time";

    public static final String DELETED = "deleted";

    public static final String USER_APP_ID = "user_app_id";

    public static final String USER_ID = "user_id";

    public static final String APP = "app";

    public static final String APP_PLATFORM = "app_platform";

    public static final String APP_VERSION = "app_version";

    public static final String APP_VERSION_TYPE = "app_version_type";

    public static final String VERSION_NUM = "version_num";


    public static Qw<UUserAppVersion> select(Qw<UUserAppVersion> qw) {
        if (CheckUtil.objIsEmpty(qw))
            qw = Qw.create();
        qw.select(
                UUserAppVersion.USER_ID,
                UUserAppVersion.APP,
                UUserAppVersion.APP_PLATFORM,
                UUserAppVersion.APP_VERSION,
                UUserAppVersion.APP_VERSION_TYPE,
                UUserAppVersion.VERSION_NUM
        );
        return qw;
    }
}