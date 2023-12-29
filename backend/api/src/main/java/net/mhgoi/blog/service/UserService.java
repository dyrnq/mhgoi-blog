package net.mhgoi.blog.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import net.mhgoi.blog.entity.User;
import net.mhgoi.blog.exception.TokenExpiredException;

/**
 * 用户表(User)表服务接口
 *
 * @author 少年
 * @since 2020-07-02 20:10:29
 */
public interface UserService extends IService<User> {
    /**
     * 分页条件查询用户信息
     *
     * @param currentPage 当前页
     * @param size        每页大小
     * @param map         查询条件
     * @return 用户信息
     */
    Page<User> queryPage(Integer currentPage, Integer size, Map<String, Object> map);

    /**
     * 保存用户
     *
     * @param user 用户
     * @return 保存状态
     */
    boolean saveUser(User user);

    /**
     * 通过手机号查询用户
     *
     * @param mobile 手机号
     * @return 用户
     */
    User getByMobile(String mobile);

    /**
     * 刷新令牌
     *
     * @param token 旧令牌
     * @return 新令牌
     */
    String refreshToken(String token);

    /**
     * 获取用户信息
     *
     * @param token 获取信息所需的token
     * @return 用户信息
     * @throws TokenExpiredException 令牌过期
     */
    User getUserInfo(String token) throws TokenExpiredException;

    /**
     * 通过ID查询用户
     *
     * @param id 用户ID
     * @return 用户
     */
    User findById(String id);
}