package net.mhgoi.blog.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.MalformedJwtException;
import net.mhgoi.blog.components.JwtTokenUtil;
import net.mhgoi.blog.dto.Result;
import net.mhgoi.blog.dto.StatusCode;
import net.mhgoi.blog.exception.TokenExpiredException;
import net.mhgoi.blog.service.MyUserDetailsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 少年
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);
    @Resource
    MyUserDetailsService myUserDetailsService;

    @Resource
    JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(jwtTokenUtil.getHeader());
        if (StringUtils.isNotBlank(token)) {
            String userId = null;
            try {
                userId = jwtTokenUtil.getUserIdFromToken(token);
            } catch (TokenExpiredException e) {
                writeFailedJSON(response, StatusCode.TOKEN_EXPIRED, "令牌过期");
                return;
            } catch (MalformedJwtException e) {
                writeFailedJSON(response, StatusCode.NO_USER, "操作失败");
                return;
            }
            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails;
                try {
                    userDetails = myUserDetailsService.loadUserByUserId(userId);
                } catch (UsernameNotFoundException e) {
                    writeFailedJSON(response, StatusCode.NO_USER, "操作失败");
                    return;
                }
                if (!userDetails.isEnabled()) {
                    writeFailedJSON(response, StatusCode.USER_FAILURE, "用户失效");
                    return;
                }
                try {
                    if (jwtTokenUtil.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                } catch (Exception e) {
                    new Throwable("验证token无效:" + e.getMessage());
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private void writeFailedJSON(HttpServletResponse response, Integer code, String msg) throws IOException, ServletException {
        Result result = new Result(false, code, msg);
        response.setContentType("application/json;charset=UTF-8");
        //输出JSON
        PrintWriter out = response.getWriter();
        out.write(new ObjectMapper().writeValueAsString(result));
        out.flush();
        out.close();
    }
}
