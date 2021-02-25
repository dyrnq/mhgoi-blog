package net.mhgoi.blog.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author 少年
 */
public interface MyUserDetailsService extends UserDetailsService {
    /**
     * 重写SpringSecurity loadUserByUserId()方法
     *
     * @param userId 用户ID
     * @return UserDetails
     */
    UserDetails loadUserByUserId(String userId);
}
