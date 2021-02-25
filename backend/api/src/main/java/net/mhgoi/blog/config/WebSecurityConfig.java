package net.mhgoi.blog.config;

import net.mhgoi.blog.components.DynamicPermission;
import net.mhgoi.blog.filter.JwtAuthenticationTokenFilter;
import net.mhgoi.blog.filter.JwtLoginFilter;
import net.mhgoi.blog.handler.CustomAccessDeniedHandler;
import net.mhgoi.blog.handler.CustomAuthenticationEntryPoint;
import net.mhgoi.blog.handler.MyAuthenticationFailureHandler;
import net.mhgoi.blog.handler.MyAuthenticationSuccessHandler;
import net.mhgoi.blog.service.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author 少年
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private MyUserDetailsService myUserDetailsService;
    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Resource
    private MyAuthenticationSuccessHandler authenticationSuccessHandler;
    @Resource
    private MyAuthenticationFailureHandler authenticationFailureHandler;
    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Resource
    private DynamicPermission dynamicPermission;
    @Resource
    private CustomAccessDeniedHandler customAccessDeniedHandler;
    @Resource
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        //允许X-Frame-Options网页被Frame
        httpSecurity.headers().frameOptions().disable();

        /**
         httpSecurity
         // 由于使用的是JWT，我们这里不需要csrf
         .cors()//新加入,允许跨域
         .and()
         .csrf().disable()
         // 基于token，所以不需要session
         .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
         .authorizeRequests()
         **/

        
        httpSecurity .addFilterAt(jwtLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(
                        "/swagger-ui.html",
                        "/swagger-ui/*",
                        "/swagger-resources/**",
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/webjars/**",
                        "/actuator/**",
                        "/druid/**",
                        "/auth/*"
                ).permitAll()
                .anyRequest().access("@dynamicPermission.hasPermission(request,authentication)")
                .and().exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint).accessDeniedHandler(customAccessDeniedHandler)
                .and().cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.headers().cacheControl();
    }

    /**
     * 配置跨域
     *
     * @return
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
        configuration.applyPermitDefaultValues();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * 配置认证来源
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    /**
     * 手动注册账号、密码拦截器
     *
     * @return
     */
    @Bean
    JwtLoginFilter jwtLoginFilter() throws Exception {
        JwtLoginFilter filter = new JwtLoginFilter();
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(authenticationFailureHandler);
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }
}
