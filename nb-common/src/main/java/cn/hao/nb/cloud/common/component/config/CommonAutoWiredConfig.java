package cn.hao.nb.cloud.common.component.config;

import cn.hao.nb.cloud.common.util.PEnumUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: scootXin
 * @Date: 2018/12/4 15:04
 */
@Slf4j
@Configuration
public class CommonAutoWiredConfig {

    /**
     * 枚举获取工具
     */
    @Bean("pEnumUtil")
    public PEnumUtil pEnumUtil() {
        return new PEnumUtil();
    }
}
