package cn.hao.nb.cloud.common.component.config;

import cn.hao.nb.cloud.common.util.IDUtil;
import cn.hao.nb.cloud.common.util.PEnumUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: scootXin
 * @Date: 2018/12/4 15:04
 */
@Slf4j
@Configuration
public class CommonAutoWiredConfig {

    @Value("${pang.serviceId}")
    private long serviceId;

    /**
     * 主键生成工具
     *
     * @return
     */
    @Bean("idUtil")
    public IDUtil idUtil() {
        return new IDUtil(serviceId);
    }

    /**
     * 枚举获取工具
     */
    @Bean("pEnumUtil")
    public PEnumUtil pEnumUtil() {
        return new PEnumUtil();
    }
}
