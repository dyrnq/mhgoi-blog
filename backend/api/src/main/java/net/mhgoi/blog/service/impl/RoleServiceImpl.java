package net.mhgoi.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.mhgoi.blog.entity.Role;
import net.mhgoi.blog.mapper.RoleMapper;
import net.mhgoi.blog.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * (Role)表服务实现类
 *
 * @author 少年
 * @since 2020-07-19 17:57:14
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Override
    public List<String> getRoleCodeByUserId(String userId) {
        return this.baseMapper.getRoleCodeByUserId(userId);
    }

    @Override
    public List<Role> getRoleByUserId(String userId) {
        return this.baseMapper.getRoleByUserId(userId);
    }

    @Override
    @Transactional
    public void grantRole(String userId, List<String> roleIds) {
        this.baseMapper.deleteOwnedRole(userId);
        if (roleIds != null && roleIds.size() > 0) {
            this.baseMapper.addOwnedRole(userId, roleIds);
        }
    }
}