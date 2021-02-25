package net.mhgoi.blog.handler;

import net.mhgoi.blog.components.JwtTokenUtil;
import net.mhgoi.blog.dto.Result;
import net.mhgoi.blog.dto.StatusCode;
import net.mhgoi.blog.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 少年
 */
@Component
public class MyAuthenticationSuccessHandler extends JsonResponseHandler implements AuthenticationSuccessHandler {

    @Resource
    JwtTokenUtil jwtTokenUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        //取得账号信息
        User user = (User) authentication.getPrincipal();

        String token = jwtTokenUtil.generateToken(user);

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        Result result = new Result(true, StatusCode.OK, "登录成功", map);
        writeJSON(response, result);
    }
}
