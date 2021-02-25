package net.mhgoi.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.mhgoi.blog.entity.Menu;
import net.mhgoi.blog.mapper.MenuMapper;
import net.mhgoi.blog.service.MenuService;
import net.mhgoi.blog.util.TreeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 菜单表(Menus)表服务实现类
 *
 * @author 少年
 * @since 2020-07-19 18:17:24
 */
@Service("menusService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<Menu> getMenusByUserId(String userId) {
        List<Menu> menuList = this.baseMapper.getMenusByUserId(userId);
        return TreeUtil.getTreeList(null, menuList, null);
    }

    @Override
    public List<Menu> getAll() {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("menu_sort");
        List<Menu> menuList = this.baseMapper.selectList(queryWrapper);
        return TreeUtil.getTreeList(null, menuList, null);
    }

    @Override
    public List<Menu> getByRoleId(String roleId) {
        return this.baseMapper.getByRoleId(roleId);
    }

    @Override
    @Transactional
    public void grantMenu(String roleId, List<String> menuIds) {
        this.baseMapper.deleteOwnedMenu(roleId);
        if (menuIds.size() > 0) {
            this.baseMapper.addOwnedMenu(roleId, menuIds);
        }
    }
}