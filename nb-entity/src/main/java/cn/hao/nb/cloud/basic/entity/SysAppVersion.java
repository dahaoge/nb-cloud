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
 * app版本管理
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "SysAppVersion对象", description = "app版本管理 ")
public class SysAppVersion implements Serializable {

    @Data
    public class SearchParams {


        @ApiModelProperty(value = "排序字段")
        private String sort = "update_time";

        @ApiModelProperty(value = "排序方式,可选值:ASC/DESC")
        private ESqlOrder order = ESqlOrder.DESC;

        public Qw<SysAppVersion> preWrapper(Qw<SysAppVersion> qw) {
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

    private Long createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    private Long updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "删除标记")
    @TableLogic
    private Integer deleted = 0;

    @TableId(value = "app_version_id", type = IdType.INPUT)
    private Long appVersionId;

    private String app;

    private String appPlatform;

    private String appDownloadChannel;

    private String appVersionType;

    private String appVersion;

    @ApiModelProperty(value = "是否强制更新")
    private Integer isMust;

    private String appVersionName;

    private String versionDesc;

    private String updateDesc;

    @ApiModelProperty(value = "生效时间")
    private Date versionStartTime;

    @ApiModelProperty(value = "支付功能是否关闭")
    private Integer payLock;

    private Integer appSize;

    private String downloadUrlHash;

    @TableField(exist = false)
    String downLoadUrl;

    private Integer hasPublished;

    @ApiModelProperty(value = "版本编号")
    private Integer versionNum;


    public static final String VERSION = "version";

    public static final String CREATE_BY = "create_by";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_BY = "update_by";

    public static final String UPDATE_TIME = "update_time";

    public static final String DELETED = "deleted";

    public static final String APP_VERSION_ID = "app_version_id";

    public static final String APP = "app";

    public static final String APP_PLATFORM = "app_platform";

    public static final String APP_DOWNLOAD_CHANNEL = "app_download_channel";

    public static final String APP_VERSION_TYPE = "app_version_type";

    public static final String APP_VERSION = "app_version";

    public static final String IS_MUST = "is_must";

    public static final String APP_VERSION_NAME = "app_version_name";

    public static final String VERSION_DESC = "version_desc";

    public static final String UPDATE_DESC = "update_desc";

    public static final String VERSION_START_TIME = "version_start_time";

    public static final String PAY_LOCK = "pay_lock";

    public static final String APP_SIZE = "app_size";

    public static final String DOWNLOAD_URL_HASH = "download_url_hash";

    public static final String HAS_PUBLISHED = "has_published";

    public static final String VERSION_NUM = "version_num";

    public static Qw<SysAppVersion> select(Qw<SysAppVersion> qw) {
        if (CheckUtil.objIsEmpty(qw))
            qw = Qw.create();
        qw.select(
                SysAppVersion.APP,
                SysAppVersion.APP_PLATFORM,
                SysAppVersion.APP_DOWNLOAD_CHANNEL,
                SysAppVersion.APP_VERSION_TYPE,
                SysAppVersion.APP_VERSION,
                SysAppVersion.IS_MUST,
                SysAppVersion.APP_VERSION_NAME,
                SysAppVersion.VERSION_DESC,
                SysAppVersion.UPDATE_DESC,
                SysAppVersion.VERSION_START_TIME,
                SysAppVersion.PAY_LOCK,
                SysAppVersion.APP_SIZE,
                SysAppVersion.DOWNLOAD_URL_HASH,
                SysAppVersion.HAS_PUBLISHED,
                SysAppVersion.VERSION_NUM
        );
        return qw;
    }

}