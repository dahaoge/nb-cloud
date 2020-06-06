package cn.hao.nb.cloud.ydgl.constant;

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
     * 公司请求后缀
     */
    public static final String REDIS_COMPANY_REQUEST_SUFFIX = "REDIS_COMPANY_REQUEST_SUFFIX";

    public static final String REDIS_REQUEST_RESULT = "REDIS_REQUEST_RESULT_";
}
