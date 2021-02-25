package net.mhgoi.blog.service.impl;

import net.mhgoi.blog.entity.User;
import net.mhgoi.blog.mapper.UserMapper;
import net.mhgoi.blog.service.MyUserDetailsService;
import net.mhgoi.blog.service.RoleService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 少年
 */
@Service
public class MyUserDetailsServiceImpl implements MyUserDetailsService {

    @Resource
    UserMapper userMapper;

    @Resource
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.getByUsername(username);
        user = initUser(user);
        return user;
    }

    @Override
    public UserDetails loadUserByUserId(String userId) {
        User user = userMapper.selectById(userId);
        user = initUser(user);
        return user;
    }

    private User initUser(User user) {
        if (user == null) throw new UsernameNotFoundException("用户不存在");
        //查找角色
        List<String> roles = roleService.getRoleCodeByUserId(user.getId());
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        user.setAuthorities(authorities);
        return user;
    }
}
