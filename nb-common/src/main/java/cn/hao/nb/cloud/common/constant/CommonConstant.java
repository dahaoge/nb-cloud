package cn.hao.nb.cloud.common.constant;

/**
 * @Auther: hao
 * @Date: 2019-12-07 15:55
 * @Description:
 */
public interface CommonConstant {

    /**
     * 编码
     */
    String UTF8 = "UTF-8";

    /**
     * JSON 资源
     */
    String CONTENT_TYPE = "application/json; charset=utf-8";

    /**
     * 缓存用户信息
     */
    String REDIS_USER_KEY = "REDIS_USER_INFO_KEY";

    /**
     * 缓存用户信息过期时间
     */
    long REDIS_USER_EXPIRE_TIME = 86400;

    /**
     * 菜单树缓存
     */
    String REDIS_MENU_TREE = "REDIS_MENU_TREE_KEY";

    /**
     * 菜单树缓存时间
     */
    long REDIS_MENU_TREE_EXPIRE_TIME = 3600;
}
