package cn.hao.nb.cloud.common.component.config.security;

import cn.hao.nb.cloud.common.entity.TokenUser;
import cn.hao.nb.cloud.common.penum.ESourceClient;
import cn.hao.nb.cloud.common.util.CheckUtil;
import cn.hao.nb.cloud.common.util.RedisUtil;
import cn.hao.nb.cloud.common.util.UserUtil;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: scootXin
 * @Date: 2019/3/13 10:39
 */
@Slf4j
@Component
public class JwtTokenUtil implements Serializable {
    /**
     * 密钥
     */
    private static final String secret = "m87xjdy12xd";
    private final String ACCESS_KEY = "tokenAccessKey";
    private final String USER_TOKEN_KEY = "tokenUserKey";
    //    @Autowired
//    private RedisTemplate redisTemplate;
    @Autowired
    RedisUtil redisUtil;

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public static TokenUser getUserFromToken(String token) {
        TokenUser tokenUser;
        try {
            Claims claims = getClaimsFromToken(token);

            tokenUser = JSON.parseObject((String) claims.get("userInfo"), TokenUser.class);
        } catch (Exception e) {
            tokenUser = null;
        }
        return tokenUser;
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String generateToken(Map<String, Object> claims, ESourceClient client) {
        long expireMillTimes = 604800L * 1000 * 52;

        Date expirationDate = new Date(System.currentTimeMillis() + expireMillTimes);
//        Date expirationDate = DateTime.now().plusDays(30).toDate();
        String token = Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, secret).compact();

        String userId = (String) claims.get("sub");
//        redisTemplate.opsForHash().put(ACCESS_KEY, token, Byte.MIN_VALUE);
        redisUtil.hset(ACCESS_KEY, token, Byte.MIN_VALUE);
//        redisTemplate.opsForHash().put(USER_TOKEN_KEY, client.getValue() + "_" + userId, token);
        redisUtil.hset(USER_TOKEN_KEY, client.getValue() + "_" + userId, token);
        log.info("\n\033[1;93;32m【标记】\033[m");
        log.info("\n端: {} 用户: {} 刷新token: {}", client.getValue(), userId, token.substring(token.length() - 20));
        return token;
    }

    /**
     * 生成令牌
     *
     * @return 令牌
     */
    public String generateToken(TokenUser user) {
        return this.generateToken(user, UserUtil.getAndValidRequestClient());
    }

    public String generateToken(TokenUser user, ESourceClient client) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put("sub", user.getUserId());
        claims.put("created", new Date());

        //进行脱敏
        user.setLoginPwd(null);
        user.setSalt(null);

        Map<String, Object> map = JSON.parseObject(JSON.toJSONString(user));

        map.remove("menuList");

        claims.put("userInfo", JSON.toJSONString(map));
        return generateToken(claims, client);
    }

    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
            //TODO token过期时间
//            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 刷新令牌
     *
     * @param token 原令牌
     * @return 新令牌
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put("created", new Date());
            refreshedToken = generateToken(claims, UserUtil.getAndValidRequestClient());
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 删除token
     *
     * @param token
     * @return
     */
    public boolean delToken(String token, ESourceClient client) {
        TokenUser user = getUserFromToken(token);

//        redisTemplate.opsForHash().delete(ACCESS_KEY, token);
        redisUtil.hdel(ACCESS_KEY, token);
//        redisTemplate.opsForHash().delete(USER_TOKEN_KEY, UserUtil.getAndValidRequestClient().getValue() + "_" + user.getUserId());
        redisUtil.hdel(USER_TOKEN_KEY, client.getValue() + "_" + user.getUserId());
        return true;
    }

    public boolean delAllToken(String userId) {
        if (CheckUtil.strIsEmpty(userId))
            return true;
        for (ESourceClient client : ESourceClient.values()) {
            String actUserToken = (String) redisUtil.hget(USER_TOKEN_KEY, client.getValue() + "_" + userId);
            if (CheckUtil.strIsNotEmpty(actUserToken)) {
                redisUtil.hdel(ACCESS_KEY, actUserToken);
                redisUtil.hdel(USER_TOKEN_KEY, client.getValue() + "_" + userId);
            }
        }
        return true;
    }

    /**
     * 验证令牌
     *
     * @param token 令牌
     * @return 是否有效
     */
    public Boolean validateToken(String token, TokenUser tokenUser, HttpServletRequest request) {

        if (CheckUtil.objIsEmpty(token, tokenUser)) {
            return false;
        }

        String tokenMark = token.length() > 20 ? token.substring(token.length() - 20) : token;

        if (isTokenExpired(token)) {
//            redisTemplate.opsForHash().delete(ACCESS_KEY, token);
            log.info("\n\033[1;93;32m【标记】\033[m");
            log.error("\n用户:{}\ntoken过期:{}\n请求地址:{}", tokenUser.getUserId(), tokenMark, request.getRequestURI());
            redisUtil.hdel(ACCESS_KEY, token);
            return false;
        }

        Object userExistToken = redisUtil.hget(USER_TOKEN_KEY, UserUtil.getAndValidRequestClient(request).getValue() + "_" + tokenUser.getUserId());


        //token无效
//        if (!redisTemplate.opsForHash().hasKey(ACCESS_KEY, token)) {
//        if (!redisUtil.hHasKey(ACCESS_KEY, token)) {
//            return false;
//        }


        // 临时处理缓存丢失重新发送短信的问题
        if (!redisUtil.hHasKey(ACCESS_KEY, token)) {
            log.info("\n\033[1;93;32m【标记】\033[m");
            log.error("\ntoken丢失,登录用户id:{}\n请求:{}\n激活的token:{}\n登录的token:{}", tokenUser.getUserId(), request.getRequestURI(),
                    CheckUtil.objIsEmpty(userExistToken) ? null : userExistToken.toString().substring(userExistToken.toString().length() - 20)
                    , tokenMark);
            if (CheckUtil.objIsNotEmpty(userExistToken) && token.equals(userExistToken)) {
                redisUtil.hset(ACCESS_KEY, token, Byte.MIN_VALUE);
                redisUtil.hset(USER_TOKEN_KEY, UserUtil.getAndValidRequestClient(request) + "_" + tokenUser.getUserId(), token);
            } else {
                return false;
            }
        }

        //获取用户激活状态token
//        Object userExistToken = redisTemplate.opsForHash().get(USER_TOKEN_KEY, UserUtil.getAndValidRequestClient().getValue() + "_" + tokenUser.getUserId());
//        Object userExistToken = redisUtil.hget(USER_TOKEN_KEY, UserUtil.getAndValidRequestClient(request).getValue() + "_" + tokenUser.getUserId());
        //无激活状态token，该token失效
        if (userExistToken == null) {
//            redisTemplate.opsForHash().delete(ACCESS_KEY, token);
//            redisUtil.hdel(ACCESS_KEY,token);
            log.info("\n\033[1;93;32m【标记】\033[m");
            log.error("\n激活token丢失,登录用户id:{}\n请求:{}\n激活的token:{}\n登录的token:{}", tokenUser.getUserId(), request.getRequestURI(), null,
                    tokenMark);
            this.delToken(token, UserUtil.getAndValidRequestClient(request));

            return false;
        } else {
            String existToken = (String) userExistToken;

            //激活状态token不匹配， 该token失效
            if (!token.equals(existToken)) {
                log.info("\n\033[1;93;32m【标记】\033[m");
                log.error("\n激活token和token不匹配,登录用户id:{}\n请求:{}\n激活的token:{}\n登录的token:{}", tokenUser.getUserId(), request.getRequestURI(),
                        CheckUtil.objIsEmpty(userExistToken) ? null : existToken.substring(existToken.length() - 20)
                        , tokenMark);
//                redisTemplate.opsForHash().delete(ACCESS_KEY, token);
//                redisUtil.hdel(ACCESS_KEY,token);
                this.delToken(token, UserUtil.getAndValidRequestClient(request));
                return false;
            }
        }

        return true;
    }

    public String getRequestToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String tokenHead = "bearer ";
        if (authHeader != null && authHeader.startsWith(tokenHead)) {
            final String authToken = authHeader.substring(tokenHead.length());
            return authToken;
        } else
            return null;
    }
}