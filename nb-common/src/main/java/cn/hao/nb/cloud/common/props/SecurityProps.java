package cn.hao.nb.cloud.common.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * @Auther: hao
 * @Date: 2019-12-06 20:56
 * @Description:
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "customize.security")
public class SecurityProps {

    List<String> ignoreUrls;

    Map<String, Map<String, String>> sourceClients;


}
