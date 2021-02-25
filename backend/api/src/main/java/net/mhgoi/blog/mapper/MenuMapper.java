package net.mhgoi.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.mhgoi.blog.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单表(Menus)表数据库访问层
 *
 * @author 少年
 * @since 2020-07-14 10:15:55
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 查询用户拥有的菜单
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<Menu> getMenusByUserId(@Param("userId") String userId);

    /**
     * 查询角色拥有菜单
     *
     * @param roleId 角色ID
     * @return 菜单列表
     */
    List<Menu> getByRoleId(@Param("roleId") String roleId);

    /**
     * 角色删除菜单
     *
     * @param roleId 角色ID
     * @return 删除结果
     */
    int deleteOwnedMenu(@Param("roleId") String roleId);

    /**
     * 删除子菜单
     *
     * @param pid
     * @return 删除结果
     */
    int deleteByPid(@Param("pid") String pid);

    /**
     * 用户添加菜单
     *
     * @param userId  用户ID
     * @param menuIds 菜单ID列表
     * @return 添加结果
     */
    int addOwnedMenu(@Param("roleId") String userId, @Param("menuIds") List<String> menuIds);
}