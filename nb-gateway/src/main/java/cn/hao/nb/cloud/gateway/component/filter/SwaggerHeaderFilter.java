//package cn.hao.nb.cloud.gateway.component.filter;
//
//import cn.hao.nb.cloud.gateway.component.SwaggerProvider;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
///**
// * @Auther: hao
// * @Date: 2019-12-10 10:04
// * @Description:
// */
//@Component
//public class SwaggerHeaderFilter implements GlobalFilter, Ordered {
//
//    private static final String HEADER_NAME = "X-Forwarded-Prefix";
//
//    private static final String HOST_NAME = "X-Forwarded-Host";
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//
//        String path = request.getURI().getPath();
//
//        if (!StringUtils.endsWithIgnoreCase(path, SwaggerProvider.API_URI)) {
//
//            return chain.filter(exchange);
//
//        }
//
//        String basePath = path.substring(0, path.lastIndexOf(SwaggerProvider.API_URI));
//
//        ServerHttpRequest newRequest = request.mutate().header(HEADER_NAME, basePath).build();
//
//        ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
//
//        return chain.filter(newExchange);
//    }
//
//    @Override
//    public int getOrder() {
//        return -200;
//    }
//}
