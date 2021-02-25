package net.mhgoi.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.mhgoi.blog.entity.Role;

import java.util.List;

/**
 * (Role)表服务接口
 *
 * @author 少年
 * @since 2020-07-19 17:57:14
 */
public interface RoleService extends IService<Role> {

    /**
     * 根据用户ID查询角色Code
     *
     * @param userId 用户ID
     * @return 角色Code列表
     */
    List<String> getRoleCodeByUserId(String userId);

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<Role> getRoleByUserId(String userId);

    /**
     * 用户分配角色
     *
     * @param userId  用户ID
     * @param roleIds 角色ID列表
     */
    void grantRole(String userId, List<String> roleIds);
}