package cn.hao.nb.cloud.common.component.config.security;

import cn.hao.nb.cloud.common.entity.TokenUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    private IgnoreProperties ignoreProperties;

    @Autowired
    public JwtAuthenticationTokenFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public JwtAuthenticationTokenFilter() {
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException,
            IOException {
        String authHeader = request.getHeader("Authorization");
        String tokenHead = "bearer ";
        if (authHeader != null && authHeader.startsWith(tokenHead)) {
            final String authToken = authHeader.substring(tokenHead.length());

            TokenUser tokenUser = jwtTokenUtil.getUserFromToken(authToken);

            if (tokenUser != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                if (jwtTokenUtil.validateToken(authToken, tokenUser)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(tokenUser, null, tokenUser
                            .getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }
}
