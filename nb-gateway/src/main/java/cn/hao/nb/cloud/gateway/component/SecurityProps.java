package cn.hao.nb.cloud.gateway.component;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: hao
 * @Date: 2019-12-10 09:42
 * @Description:
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "customize.security")
public class SecurityProps extends cn.hao.nb.cloud.common.component.props.SecurityProps {
}
