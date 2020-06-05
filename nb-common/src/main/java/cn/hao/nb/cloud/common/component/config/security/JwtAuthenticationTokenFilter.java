package cn.hao.nb.cloud.common.component.config.security;

import cn.hao.nb.cloud.common.component.props.SecurityProps;
import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.entity.TokenUser;
import cn.hao.nb.cloud.common.util.CheckUtil;
import cn.hao.nb.cloud.common.util.HttpUtil;
import cn.hao.nb.cloud.common.util.UserUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @Description:
 * @Author: scootXin
 * @Date: 2019/3/13 10:38
 */
@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    SecurityProps securityProps;

    @Value("${spring.application.name}")
    private String appName;


    @Autowired
    public JwtAuthenticationTokenFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public JwtAuthenticationTokenFilter() {
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException,
            IOException {
        log.info(appName.concat(":jwt-filter"));

        try {
            // 防止生产环境心跳刷日志
            if ("/".equals(request.getRequestURI()) || "".equals(request.getRequestURI())) {
                response.sendRedirect("/error/basePathDefaultResponse");
                return;
            }
            if (request.getRequestURL().indexOf("actuator/health") < 0) {
                StringBuilder msg = new StringBuilder();
                msg.append("received request >> ").append(" url:").append(request.getRequestURL().toString()).append(" || httpMethod:").append(request
                        .getMethod()).append(" || ip:").append(HttpUtil.getIpAddress(request)).append(JSON.toJSONString(request.getParameterMap()));
                log.info(msg.toString());
            }

            String authHeader = request.getHeader("Authorization");
            String tokenHead = "bearer ";
            if (CheckUtil.strIsNotEmpty(authHeader) && authHeader.length() > 20) {
                log.info("\n\033[1;93;32m【标记】\033[m");
                log.info("\n端:{}\n请求:{}\n使用token:{}", UserUtil.getAndValidRequestClient(request), request.getRequestURI(), authHeader.substring(authHeader.length() - 20));
            }

            AntPathMatcher antPathMatcher = new AntPathMatcher();

            if (securityProps.getIgnoreUrls().stream().filter(url -> {
                return antPathMatcher.match(url, request.getRequestURI());
            }).count() < 1) {
                UserUtil.getAndValidRequestClient(request);
            }

            if (authHeader != null && authHeader.startsWith(tokenHead)) {
                final String authToken = authHeader.substring(tokenHead.length());

                TokenUser tokenUser = jwtTokenUtil.getUserFromToken(authToken);

                if (CheckUtil.objIsNotEmpty(tokenUser)) {
                    log.info("\n\033[1;93;32m【标记】\033[m");
                    log.info("\n用户: {}\n请求:{}\n使用token: {}", tokenUser.getUserId(), request.getRequestURI(), authToken.substring(authToken.length() - 20));
                }

                if (tokenUser != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    if (jwtTokenUtil.validateToken(authToken, tokenUser, request)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(tokenUser, null, tokenUser
                                .getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }

                }
            }
            chain.doFilter(request, response);
        } catch (NBException e) {
            String url = "/error/nb?errorCode=" + e.getErrorCode() + "&msg=" + URLEncoder.encode(e.getMeMessage(), "UTF-8");
            System.out.println(url);
            response.sendRedirect(url);
            return;
        }

//        String authHeader = request.getHeader("Authorization");
//        String tokenHead = "bearer ";
//        if (authHeader != null && authHeader.startsWith(tokenHead)) {
//            final String authToken = authHeader.substring(tokenHead.length());
//
//            TokenUser tokenUser = jwtTokenUtil.getUserFromToken(authToken);
//
//            if (tokenUser != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//                if (jwtTokenUtil.validateToken(authToken, tokenUser, request)) {
//                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(tokenUser, null, tokenUser
//                            .getAuthorities());
//                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                }
//            }
//        }
//        chain.doFilter(request, response);
    }
}
