package net.mhgoi.blog.components;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import net.mhgoi.blog.entity.User;
import net.mhgoi.blog.exception.TokenExpiredException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 少年
 * JWT生成令牌、验证令牌、获取令牌
 */
@ConfigurationProperties(prefix = "jwt")
@Component
@Data
public class JwtTokenUtil {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(JwtTokenUtil.class);
    //私钥
    private String secretKey;

    // 过期时间 毫秒,设置默认1周的时间过期
    private Long expirationTime;

    private String header;

    /**
     * 生成令牌
     *
     * @param user 用户
     * @return 令牌
     */
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put(Claims.SUBJECT, user.getUsername());
        claims.put(Claims.ISSUED_AT, new Date());
        claims.put(Claims.ID, user.getId());
        claims.put("avatar", user.getAvatar());
        return generateToken(claims);
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) throws TokenExpiredException {
        String username = null;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (ExpiredJwtException e) {
            log.error(e.getMessage(), e);
            throw new TokenExpiredException("令牌过期");
        }
        return username;
    }

    /**
     * 从令牌中获取用户ID
     *
     * @param token 令牌
     * @return 用户ID
     */
    public String getUserIdFromToken(String token) throws TokenExpiredException {
        String userId = null;
        try {
            Claims claims = getClaimsFromToken(token);
            userId = claims.getId();
        } catch (ExpiredJwtException e) {
            log.error(e.getMessage(), e);
            throw new TokenExpiredException("令牌过期");
        }
        return userId;
    }

    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public Boolean isTokenExpired(String token) throws Exception {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            new Throwable(e);
        }
        return true;
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
            claims.put(Claims.ISSUED_AT, new Date());
            refreshedToken = generateToken(claims);
        } catch (ExpiredJwtException e) {
            log.error(e.getMessage(), e);
            refreshedToken = generateToken(e.getClaims());
        }
        return refreshedToken;
    }

    /**
     * 验证令牌
     *
     * @param token       令牌
     * @param userDetails 用户
     * @return 是否有效
     */
    public Boolean validateToken(String token, UserDetails userDetails) throws Exception {
        String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String generateToken(Map<String, Object> claims) {
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);
        return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims getClaimsFromToken(String token) throws ExpiredJwtException {
        return Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }
}