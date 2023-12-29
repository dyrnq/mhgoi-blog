package net.mhgoi.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.mhgoi.blog.entity.Api;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * (Api)表数据库访问层
 *
 * @author 少年
 * @since 2020-07-13 21:43:52
 */
@Mapper
public interface ApiMapper extends BaseMapper<Api> {
    /**
     * 根据用户名称获得API URL资源鉴权
     *
     * @param username 用户名
     * @return api列表
     */
    List<Api> getApiByUserName(@Param("username") String username);

    /**
     * 分页条件查询api
     *
     * @param pg
     * @param map 查询条件
     * @return api列表
     */
    Page<Api> queryPage(Page<Api> pg, Map<String, Object> map);

    /**
     * 获取所有模块
     *
     * @return
     */
    List<String> getModules();

    /**
     * 根据ID查询api
     *
     * @param id apiID
     * @return api
     */
    Api getById(String id);

    /**
     * 删除角色拥有api
     *
     * @param roleId 角色ID
     * @return 删除结果
     */
    int deleteOwnedApi(@Param("roleId") String roleId);

    /**
     * 添加角色拥有api
     *
     * @param roleId 角色ID
     * @param apiIds apiID列表
     * @return 添加结果
     */
    int addOwnedApi(@Param("roleId") String roleId, @Param("apiIds") List<String> apiIds);

    /**
     * 根据角色ID查询拥有api
     *
     * @param roleId 角色ID
     * @return api列表
     */
    List<Api> getByRoleId(String roleId);

    /**
     * 查询所有api
     *
     * @return api列表
     */
    List<Api> getAll();
}