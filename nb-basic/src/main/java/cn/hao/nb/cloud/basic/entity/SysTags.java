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
 * 标签
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "SysTags对象", description = "标签 ")
public class SysTags implements Serializable {

    @Data
    public class SearchParams {


        @ApiModelProperty(value = "排序字段")
        private String sort = "update_time";

        @ApiModelProperty(value = "排序方式,可选值:ASC/DESC")
        private ESqlOrder order = ESqlOrder.DESC;

        public Qw<SysTags> preWrapper(Qw<SysTags> qw) {
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

    @TableId(value = "tag_id", type = IdType.INPUT)
    private String tagId;

    private String tagCode;

    private String tagLabel;

    private String tagTarget;

    private String tagType;

    private String tagIcon;

    private String tagIconActive;

    @ApiModelProperty(value = "排序索引")
    private Integer tagIndex;

    private String tagGroup;

    private String tagGroupName;

    private String appStyleNormal;

    private String appStyleActive;

    private String miniAppStyleNormal;

    private String miniAppStyleActive;

    private String webStyleNormal;

    private String webStyleActive;


    public static final String VERSION = "version";

    public static final String CREATE_BY = "create_by";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_BY = "update_by";

    public static final String UPDATE_TIME = "update_time";

    public static final String DELETED = "deleted";

    public static final String TAG_ID = "tag_id";

    public static final String TAG_CODE = "tag_code";

    public static final String TAG_LABEL = "tag_label";

    public static final String TAG_TARGET = "tag_target";

    public static final String TAG_TYPE = "tag_type";

    public static final String TAG_ICON = "tag_icon";

    public static final String TAG_ICON_ACTIVE = "tag_icon_active";

    public static final String TAG_INDEX = "tag_index";

    public static final String TAG_GROUP = "tag_group";

    public static final String TAG_GROUP_NAME = "tag_group_name";

    public static final String APP_STYLE_NORMAL = "app_style_normal";

    public static final String APP_STYLE_ACTIVE = "app_style_active";

    public static final String MINI_APP_STYLE_NORMAL = "mini_app_style_normal";

    public static final String MINI_APP_STYLE_ACTIVE = "mini_app_style_active";

    public static final String WEB_STYLE_NORMAL = "web_style_normal";

    public static final String WEB_STYLE_ACTIVE = "web_style_active";

}