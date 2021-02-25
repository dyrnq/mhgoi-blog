package net.mhgoi.blog.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import net.mhgoi.blog.entity.User;

/**
 * 用户表(User)表数据库访问层
 *
 * @author 少年
 * @since 2020-07-02 20:10:29
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户
     */
    User getByUsername(@Param("username") String username);

    /**
     * 更具手机号查询用户
     *
     * @param mobile 手机号
     * @return 用户
     */
    User getByMobile(@Param("mobile") String mobile);

    /**
     * 分页条件查询用户
     *
     * @param queryMap 查询条件
     * @return 查询结果
     */
    List<User> queryPage(Map<String, Object> queryMap);

  
}