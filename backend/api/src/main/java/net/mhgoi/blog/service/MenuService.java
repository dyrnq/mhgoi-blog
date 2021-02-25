package net.mhgoi.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.mhgoi.blog.entity.Menu;

import java.util.List;

/**
 * 菜单表(Menus)表服务接口
 *
 * @author 少年
 * @since 2020-07-19 18:17:21
 */
public interface MenuService extends IService<Menu> {

    /**
     * 查询用户拥有的菜单
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<Menu> getMenusByUserId(String userId);

    /**
     * 获取所有菜单
     *
     * @return 菜单列表
     */
    List<Menu> getAll();

    /**
     * 查询角色拥有菜单
     *
     * @param roleId 角色ID
     * @return 菜单列表
     */
    List<Menu> getByRoleId(String roleId);

    /**
     * 分配菜单
     *
     * @param roleId  角色ID
     * @param menuIds 菜单ID列表
     */
    void grantMenu(String roleId, List<String> menuIds);
}