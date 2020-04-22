package cn.hao.nb.cloud.basic.constant;

/**
 * @Auther: hao
 * @Date: 2020/4/12 17:44
 * @Description:
 */
public interface RedisKey {

    long anHour = 3600L;
    long aDay = 86400L;
    long aWeek = 604800L;

    public static final String REDIS_SYS_DICT_MAP_LOCK = "REDIS_SYS_DICT_MAP_LOCK";

    public static final String REDIS_SYS_DICT = "REDIS_SYS_DICT";

    public static final String DICT_MAP = "DICT_MAP";

    public static final String DICT_TYPE = "DICT_TYPE_";

    public static final String REDIS_SYS_TAG_MAP_LOCK = "REDIS_SYS_TAG_MAP_LOCK";

    public static final String REDIS_SYS_TAG = "REDIS_ALL_TAG";

    public static final String TAG_MAP = "TAG_MAP";
}
