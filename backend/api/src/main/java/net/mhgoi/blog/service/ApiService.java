package net.mhgoi.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.Page;
import net.mhgoi.blog.entity.Api;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;

/**
 * (Api)表服务接口
 *
 * @author 少年
 * @since 2020-07-19 18:18:26
 */
public interface ApiService extends IService<Api> {
    /**
     * 根据用户名称获得API URL资源鉴权
     *
     * @param username 用户名
     * @return api列表
     */
    @Cacheable(cacheNames = "getApiByUsername")
    List<Api> getApiByUsername(String username);

    /**
     * 分页条件查询api
     *
     * @param map 查询条件
     * @return api列表
     */
    Page<Api> queryPage(Integer currentPage, Integer size, Map<String, Object> map);

    /**
     * 角色分配api
     *
     * @param roleId 角色ID
     * @param apiIds apiID列表
     */
    void grantApi(String roleId, List<String> apiIds);

    /**
     * 根据角色ID查询拥有api
     *
     * @param roleId 角色ID
     * @return api列表
     */
    List<Api> getByRoleId(String roleId);

    /**
     * 分组查询api
     *
     * @return 分组api结果
     */
    List<Map<String, Object>> getApiGroupByModule();

    List<String> getModules();

    /**
     * 查询开放api
     *
     * @return 开放api列表
     */
    @Cacheable(cacheNames = "getOpenApi")
    List<Api> getOpenApi();
}