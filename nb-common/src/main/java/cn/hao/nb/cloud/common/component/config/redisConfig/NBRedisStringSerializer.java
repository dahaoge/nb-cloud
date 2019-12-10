package cn.hao.nb.cloud.common.component.config.redisConfig;

import cn.hao.nb.cloud.common.util.CheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

/**
 * @Auther: hao
 * @Date: 2019-12-03 16:25
 * @Description:
 */
@Slf4j
@Component
public class NBRedisStringSerializer implements RedisSerializer<String> {

    private final Charset charset;
    @Value("${spring.application.name}")
    private String applicationName;

    private String keyPrefix() {
        return applicationName + "_";
    }

    public NBRedisStringSerializer() {
        this(Charset.forName("UTF8"));
    }

    public NBRedisStringSerializer(Charset charset) {
        this.charset = charset;
    }

    @Override
    public String deserialize(byte[] bytes) {
        String saveKey = new String(bytes, charset);
        int indexOf = saveKey.indexOf(this.keyPrefix());
        if (indexOf > 0) {
            log.info("key缺少前缀" + saveKey);
        } else {
            saveKey = saveKey.substring(indexOf);
        }

        return (saveKey.getBytes() == null ? null : saveKey);
    }

    @Override
    public byte[] serialize(String string) {
        if (CheckUtil.strIsEmpty(string)) {
            return null;
        }

        if (string.startsWith(this.keyPrefix())) {
            return string.getBytes(charset);
        } else {
            String key = this.keyPrefix() + string;
            return (key == null ? null : key.getBytes(charset));
        }
    }

}