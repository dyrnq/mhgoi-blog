package net.mhgoi.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.mhgoi.blog.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;


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
     * @param pg
     * @param map 查询条件
     * @return 查询结果
     */
    Page<User> queryPage(Page<User> pg, Map<String, Object> map);
    // https://github.com/baomidou/mybatis-plus-samples/blob/master/mybatis-plus-sample-pagination/src/main/java/com/baomidou/mybatisplus/samples/pagination/mapper/UserMapper.java


}