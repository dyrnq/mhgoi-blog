package net.mhgoi.blog.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.mhgoi.blog.entity.User;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 少年
 */
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(JwtLoginFilter.class);

    private static final String APPLICATION_JSON_UTF8_VALUE_LOWER = "application/json;charset=utf-8";
    private static final String APPLICATION_JSON_UTF8_VALUE_UPPER = "application/json;charset=UTF-8";

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String contentType = request.getContentType();
        if (request.getContentType().equals(APPLICATION_JSON_UTF8_VALUE_LOWER)
                || contentType.equals(APPLICATION_JSON_UTF8_VALUE_UPPER)
                || contentType.equals(MediaType.APPLICATION_JSON_VALUE)) {
            try {
                User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
            return null;
        } else {
            return super.attemptAuthentication(request, response);
        }
    }
}
