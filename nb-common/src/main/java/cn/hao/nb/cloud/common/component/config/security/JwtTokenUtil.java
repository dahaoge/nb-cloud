package cn.hao.nb.cloud.common.component.config.security;

import cn.hao.nb.cloud.common.entity.TokenUser;
import cn.hao.nb.cloud.common.util.CheckUtil;
import cn.hao.nb.cloud.common.util.UserUtil;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: scootXin
 * @Date: 2019/3/13 10:39
 */
@Component
public class JwtTokenUtil implements Serializable {
    /**
     * 密钥
     */
    private static final String secret = "m87xjdy12xd";
    private final String ACCESS_KEY = "tokenAccessKey";
    private final String USER_TOKEN_KEY = "tokenUserKey";
    @Autowired
    private RedisTemplate redisTemplate;

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
    private String generateToken(Map<String, Object> claims) {
        // token过期时间
        long expireMillTimes = 604800L * 1000 * 52;

        Date expirationDate = new Date(System.currentTimeMillis() + expireMillTimes);
        String token = Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, secret).compact();

        String userId = (String) claims.get("sub");

        // token放入缓存
        redisTemplate.opsForHash().put(ACCESS_KEY, token, Byte.MIN_VALUE);

        // 存放token的激活状态,适配对于每个端的单点登录
        redisTemplate.opsForHash().put(USER_TOKEN_KEY, UserUtil.getAndValidRequestClient().getValue() + "_" + userId, token);

        return token;
    }

    /**
     * 生成令牌
     *
     * @return 令牌
     */
    public String generateToken(TokenUser user) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put("sub", user.getUserId());
        claims.put("created", new Date());

        //进行脱敏
        user.setLoginPwd(null);
        user.setSalt(null);

        Map<String, Object> map = JSON.parseObject(JSON.toJSONString(user));

        // 鉴权的时候一般token中不需要包含menu,为了避免token过长,生成token前去掉menu
        map.remove("menuList");

        claims.put("userInfo", JSON.toJSONString(map));
        return generateToken(claims);
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
            refreshedToken = generateToken(claims);
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
    public boolean delToken(String token) {
        TokenUser user = getUserFromToken(token);

        redisTemplate.opsForHash().delete(ACCESS_KEY, token);
        redisTemplate.opsForHash().delete(USER_TOKEN_KEY, user.getUserId());

        return true;
    }

    /**
     * 验证令牌
     *
     * @param token 令牌
     * @return 是否有效
     */
    public Boolean validateToken(String token, TokenUser tokenUser) {

        if (CheckUtil.objIsEmpty(token, tokenUser)) {
            return false;
        }


        if (isTokenExpired(token)) {
            redisTemplate.opsForHash().delete(ACCESS_KEY, token);

            return false;
        }

        //token无效
//        if (!redisTemplate.opsForHash().hasKey(ACCESS_KEY, token)) {
//            return false;
//        }

        // 临时处理缓存丢失重新发送短信的问题
        if (!redisTemplate.opsForHash().hasKey(ACCESS_KEY, token)) {
            redisTemplate.opsForHash().put(ACCESS_KEY, token, Byte.MIN_VALUE);
            redisTemplate.opsForHash().put(USER_TOKEN_KEY, tokenUser.getUserId(), token);
        }

        //获取用户激活状态token
        Object userExistToken = redisTemplate.opsForHash().get(USER_TOKEN_KEY, UserUtil.getAndValidRequestClient().getValue() + "_" + tokenUser.getUserId());
        //无激活状态token，该token失效
        if (userExistToken == null) {
            redisTemplate.opsForHash().delete(ACCESS_KEY, token);

            return false;
        } else {
            String existToken = (String) userExistToken;

            //激活状态token不匹配， 该token失效
            if (!token.equals(existToken)) {
                redisTemplate.opsForHash().delete(ACCESS_KEY, token);

                return false;
            }
        }

        return true;
    }
}