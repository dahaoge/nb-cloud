package cn.hao.nb.cloud.gateway.component.filter;

import cn.hao.nb.cloud.common.constant.SecurityConstants;
import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.penum.EErrorCode;
import cn.hao.nb.cloud.common.util.CheckUtil;
import cn.hao.nb.cloud.common.util.UserUtil;
import cn.hao.nb.cloud.gateway.component.SecurityProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Auther: hao
 * @Date: 2019-12-06 20:37
 * @Description:
 */
@Component
public class SourceVerificationFilter implements GlobalFilter, Ordered {

    public static final String ROUTE_DEFINITION_ID_PREFIX = "CompositeDiscoveryClient_";

    @Autowired
    SecurityProps securityProps;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        AntPathMatcher matcher = new AntPathMatcher();
        Route route = (Route) exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        if ("ydgl-mock".endsWith(route.getUri().getHost()))
            return chain.filter(exchange);
        if (exchange.getRequest().getPath().value().indexOf(route.getId().substring(ROUTE_DEFINITION_ID_PREFIX.length())) == 1) {
            String path = exchange.getRequest().getPath().value().substring(route.getId().substring(ROUTE_DEFINITION_ID_PREFIX.length()).length() + 1);
            for (String ignoreUrl : securityProps.getIgnoreUrls()) {
                if (matcher.match(ignoreUrl, path))
                    return chain.filter(exchange);
            }
        }

        String sourceAk = exchange.getRequest().getHeaders().getFirst(SecurityConstants.HEADER_SOURCE_AK_KEY);
        String sourceSign = exchange.getRequest().getHeaders().getFirst(SecurityConstants.HEADER_SOURCE_SIGN_KEY);
        if (CheckUtil.objIsEmpty(sourceAk, sourceSign))
            throw NBException.create(EErrorCode.missingAuthArgs);
        String sk = securityProps.getSourceClients().get(sourceAk).get(SecurityConstants.SOURCE_VERIFICATION_ENTITY_SK_KEY);
        UserUtil.validSourceSign(sk, sourceSign);
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
