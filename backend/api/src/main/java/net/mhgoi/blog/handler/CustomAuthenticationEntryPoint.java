package net.mhgoi.blog.handler;

import net.mhgoi.blog.dto.Result;
import net.mhgoi.blog.dto.StatusCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 少年
 */
@Component
public class CustomAuthenticationEntryPoint extends JsonResponseHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        Result result = new Result(false, StatusCode.ACCESSERROR, "权限不足", null);
        writeJSON(response, result);
    }
}
