package cn.hao.nb.cloud.gateway.component.filter;

import cn.hao.nb.cloud.common.constant.SecurityConstants;
import cn.hao.nb.cloud.common.util.SecurityUtil;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Auther: hao
 * @Date: 2019-12-06 20:37
 * @Description:
 */
@Component
public class SourceVerificationFilter implements GatewayFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String sourceAk = exchange.getRequest().getHeaders().getFirst(SecurityConstants.HEADER_SOURCE_AK_KEY);
        String sourceSign = exchange.getRequest().getHeaders().getFirst(SecurityConstants.HEADER_SOURCE_SIGN_KEY);
        SecurityUtil.getAndValidRequestClient(sourceAk, sourceSign);
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
