package cn.hao.nb.cloud.common.constant;

/**
 * @Auther: hao
 * @Date: 2020/4/12 17:44
 * @Description:
 */
public interface RedisKey {

    long anHour = 3600L;
    long aDay = 86400L;
    long aWeek = 604800L;

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

    /**
     * 公司请求后缀
     */
    public static final String REDIS_COMPANY_REQUEST_SUFFIX = "REDIS_COMPANY_REQUEST_SUFFIX";
}
