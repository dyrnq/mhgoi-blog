package net.mhgoi.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import net.mhgoi.blog.entity.Api;
import net.mhgoi.blog.mapper.ApiMapper;
import net.mhgoi.blog.service.ApiService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * (Api)表服务实现类
 *
 * @author 少年
 * @since 2020-07-19 18:18:27
 */
@Service("apiService")
public class ApiServiceImpl extends ServiceImpl<ApiMapper, Api> implements ApiService {
    @Override
    public List<Api> getApiByUsername(String username) {
        return this.baseMapper.getApiByUserName(username);
    }

    @Override
    public Page<Api> queryPage(Integer currentPage, Integer size, Map<String, Object> map) {
        Page<Api> page = PageHelper.startPage(currentPage, size);
        this.baseMapper.queryPage(map);
        return page;
    }

    @Override
    public Api getById(Serializable id) {
        return this.baseMapper.getById(id.toString());
    }

    @Override
    @Transactional
    public void grantApi(String roleId, List<String> apiIds) {
        this.baseMapper.deleteOwnedApi(roleId);
        if (apiIds.size() > 0) {
            this.baseMapper.addOwnedApi(roleId, apiIds);
        }
    }

    @Override
    public List<Api> getByRoleId(String roleId) {
        return this.baseMapper.getByRoleId(roleId);
    }

    @Override
    public List<Map<String, Object>> getApiGroupByModule() {
        List<Api> apiList = this.baseMapper.getAll();
        Map<String, List<Api>> collect = apiList.stream().collect(Collectors.groupingBy(Api::getModuleName));
        List<Map<String, Object>> mapList = new ArrayList<>();
        collect.keySet().forEach(key -> {
            Map<String, Object> map = new HashMap<>();
            map.put("apiName", key);
            map.put("children", collect.get(key));
            mapList.add(map);
        });
        return mapList;
    }

    @Override
    public List<Api> getOpenApi() {
        QueryWrapper<Api> wrapper = new QueryWrapper<>();
        wrapper.select("api_url", "api_method").eq("open", 1);
        return this.baseMapper.selectList(wrapper);
    }

    @Override
    public List<String> getModules() {
        return baseMapper.getModules();
    }
}