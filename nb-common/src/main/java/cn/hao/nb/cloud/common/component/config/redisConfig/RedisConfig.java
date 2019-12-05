package cn.hao.nb.cloud.common.component.config.redisConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Description:
 * @Author: scootXin
 * @Date: 2018/12/21 10:14
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Autowired
    private NBRedisStringSerializer nbRedisStringSerializer;
    @Autowired
    private RedisTemplate redisTemplate;

    @Bean
    public Boolean redisTemplateInit() {
        // key序列化
        redisTemplate.setKeySerializer(nbRedisStringSerializer);

        // value序列化
//        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return true;
    }
}
