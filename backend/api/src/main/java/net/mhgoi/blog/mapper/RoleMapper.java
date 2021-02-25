package net.mhgoi.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.mhgoi.blog.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Role)表数据库访问层
 *
 * @author 少年
 * @since 2020-07-19 17:57:14
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 根据用户ID查询角色Code
     *
     * @param userId 用户名
     * @return 角色Code列表
     */
    List<String> getRoleCodeByUserId(@Param("userId") String userId);

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<Role> getRoleByUserId(@Param("userId") String userId);

    /**
     * 用户添加角色
     *
     * @param userId  用户ID
     * @param roleIds 角色ID列表
     * @return 添加结果
     */
    int addOwnedRole(@Param("userId") String userId, @Param("roleIds") List<String> roleIds);

    /**
     * 用户删除角色
     *
     * @param userId 用户ID
     * @return 删除结果
     */
    int deleteOwnedRole(@Param("userId") String userId);
}