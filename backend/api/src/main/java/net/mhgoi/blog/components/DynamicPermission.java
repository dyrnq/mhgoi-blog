package net.mhgoi.blog.components;

import net.mhgoi.blog.entity.Api;
import net.mhgoi.blog.service.ApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.util.UrlPathHelper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.util.List;

/**
 * @author 少年
 */
@Component
public class DynamicPermission {
    private static final Logger log = LoggerFactory.getLogger(DynamicPermission.class);

    @Resource
    ApiService apiService;

    /**
     * 判断有访问API的权限
     */
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) throws AccessDeniedException {

        AntPathMatcher antPathMatcher = new AntPathMatcher();
        String requestURI = request.getRequestURI();
        //How to get request URI without context path? 
        //https://stackoverflow.com/questions/4278083/how-to-get-request-uri-without-context-path
        String path = new UrlPathHelper().getPathWithinApplication(request);
        log.debug(requestURI + "###############" + path);


        String urlMethod = request.getMethod();
        //获取访问白名单
        List<Api> openApiList = apiService.getOpenApi();
        boolean match = openApiList.stream().anyMatch(api -> antPathMatcher.match(api.getApiUrl(), path) && api.getApiMethod().contains(urlMethod));
        if (match) return true;

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            String username = userDetails.getUsername();
            //通过账号获取资源鉴权
            List<Api> apis = apiService.getApiByUsername(username);
            //当前访问路径
            //提交类型
            boolean check = apis.stream().anyMatch(api -> {
                //判断URL是否匹配
                boolean matchPath = antPathMatcher.match(api.getApiUrl(), path);
                //判断请求方式是否匹配
                boolean matchMethod = api.getApiMethod().contains(urlMethod);
                //两者都成立，返回真，否则返回假
                return matchPath && matchMethod;
            });
            return check;
        }
        return false;
    }
}

























