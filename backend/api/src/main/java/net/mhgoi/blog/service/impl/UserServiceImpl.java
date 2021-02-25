package net.mhgoi.blog.service.impl;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import net.mhgoi.blog.components.JwtTokenUtil;
import net.mhgoi.blog.entity.User;
import net.mhgoi.blog.exception.TokenExpiredException;
import net.mhgoi.blog.mapper.UserMapper;
import net.mhgoi.blog.service.MenuService;
import net.mhgoi.blog.service.RoleService;
import net.mhgoi.blog.service.UserService;

/**
 * 用户表(User)表服务实现类
 *
 * @author 少年
 * @since 2020-07-19 18:19:00
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    RoleService roleService;

    @Resource
    MenuService menuService;

    @Resource
    BCryptPasswordEncoder passwordEncoder;

    @Resource
    JwtTokenUtil jwtTokenUtil;

    @PostAuthorize("(returnObject != null && returnObject.username == authentication.name) || hasAnyAuthority('SUPER_ADMIN')")
    @Override
    public User getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public User findById(String id) {
        return super.getById(id);
    }

    @PreAuthorize("(#entity != null && #entity.username == authentication.name) || hasAnyAuthority('SUPER_ADMIN')")
    @Override
    public boolean updateById(User entity) {
        return super.updateById(entity);
    }

    @Override
    public Page<User> queryPage(Integer currentPage, Integer size, Map<String, Object> map) {
        Page<User> page = PageHelper.startPage(currentPage, size);
        this.baseMapper.queryPage(map);
        return page;
    }

    @Override
    public boolean saveUser(User user) {
        if (user.getPassword() != null && !user.getPassword().equals("")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return this.save(user);
    }

    @Override
    public User getByMobile(String mobile) {
        return this.baseMapper.getByMobile(mobile);
    }

    @Override
    public String refreshToken(String token) {
        return jwtTokenUtil.refreshToken(token);
    }

    @Override
    public User getUserInfo(String token) throws TokenExpiredException {
        String userId = jwtTokenUtil.getUserIdFromToken(token);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("username", "avatar").eq("id", userId);
        User user = this.baseMapper.selectOne(wrapper);
        user.setMenus(menuService.getMenusByUserId(userId));
        user.setRoles(roleService.getRoleCodeByUserId(userId));
        return user;
    }
}