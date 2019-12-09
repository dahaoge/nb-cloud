package cn.hao.nb.cloud.auth.entity;

import cn.hao.nb.cloud.common.entity.Qw;
import cn.hao.nb.cloud.common.penum.EMenuType;
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
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "SysMenu对象", description = "菜单表 ")
public class SysMenu implements Serializable {

    @Data
    public class SearchParams {


        @ApiModelProperty(value = "排序字段")
        private String sort = "update_time";

        @ApiModelProperty(value = "排序方式,可选值:ASC/DESC")
        private ESqlOrder order = ESqlOrder.DESC;

        public Qw<SysMenu> preWrapper(Qw<SysMenu> qw) {
            if (CheckUtil.objIsEmpty(qw))
                qw = Qw.create();
            return qw;
        }
    }

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "创建人信息")
    @TableField(exist = false)
    private Map
            <String, Object> createUserMap;

    @ApiModelProperty(value = "修改人信息")
    @TableField(exist = false)
    private Map
            <String, Object> updateUserMap;

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

    @TableId(value = "menu_id", type = IdType.INPUT)
    private String menuId;

    private String parentMenuCode;

    private String menuCode;

    private String menuName;

    private String menuPath;

    @ApiModelProperty(value = "菜单排序号 100为间隔")
    private Integer menuIndex;

    private String menuIcon;

    @ApiModelProperty(value = "菜单类型")
    private EMenuType menuType;
    @TableField(exist = false)
    private List<SysMenu> children;


    public static final String VERSION = "version";

    public static final String CREATE_BY = "create_by";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_BY = "update_by";

    public static final String UPDATE_TIME = "update_time";

    public static final String DELETED = "deleted";

    public static final String MENU_ID = "menu_id";

    public static final String PARENT_MENU_CODE = "parent_menu_code";

    public static final String MENU_CODE = "menu_code";

    public static final String MENU_NAME = "menu_name";

    public static final String MENU_PATH = "menu_path";

    public static final String MENU_INDEX = "menu_index";

    public static final String MENU_ICON = "menu_icon";

    public static final String MENU_TYPE = "menu_type";

}