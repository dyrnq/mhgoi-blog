package net.mhgoi.blog.handler;

import net.mhgoi.blog.dto.Result;
import net.mhgoi.blog.dto.StatusCode;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 少年
 */
@Component
public class MyAuthenticationFailureHandler extends JsonResponseHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {
        Result result = null;
        if (e instanceof BadCredentialsException) {
            result = new Result(false, StatusCode.LOGIN_ERROR, "账号或密码错误");
        } else if (e instanceof DisabledException) {
            result = new Result(false, StatusCode.USER_FAILURE, "用户失效");
        }
        writeJSON(response, result);
    }
}
