//package cn.hao.nb.cloud.gateway.component.filter;
//
//import cn.hao.nb.cloud.common.component.config.security.IgnoreProperties;
//import cn.hao.nb.cloud.common.component.config.security.JwtTokenUtil;
//import cn.hao.nb.cloud.common.entity.TokenUser;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.core.Ordered;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
///**
// * @Auther: hao
// * @Date: 2019-12-05 21:46
// * @Description:
// */
//@Component
//public class JwtAuthenticationTokenFilter implements GatewayFilter, Ordered {
//    private JwtTokenUtil jwtTokenUtil;
//
//    @Autowired
//    private IgnoreProperties ignoreProperties;
//
//    @Autowired
//    public JwtAuthenticationTokenFilter(JwtTokenUtil jwtTokenUtil) {
//        this.jwtTokenUtil = jwtTokenUtil;
//    }
//
//    public JwtAuthenticationTokenFilter() {
//    }
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
////        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
////        String tokenHead = "bearer ";
////        if (authHeader != null && authHeader.startsWith(tokenHead)) {
////            final String authToken = authHeader.substring(tokenHead.length());
////
////            TokenUser tokenUser = jwtTokenUtil.getUserFromToken(authToken);
////
////            if (tokenUser != null && SecurityContextHolder.getContext().getAuthentication() == null) {
////
////                if (jwtTokenUtil.validateToken(authToken, tokenUser)) {
////                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(tokenUser, null, tokenUser
////                            .getAuthorities());
////                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(exchange.getRequest()));
////                    SecurityContextHolder.getContext().setAuthentication(authentication);
////                }
////            }
////        }
//        chain.filter(exchange);
//        return null;
//    }
//
//    @Override
//    public int getOrder() {
//        return -1;
//    }
//}
