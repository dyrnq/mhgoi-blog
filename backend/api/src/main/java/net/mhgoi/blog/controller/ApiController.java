package net.mhgoi.blog.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.mhgoi.blog.dto.PageResult;
import net.mhgoi.blog.dto.Result;
import net.mhgoi.blog.dto.StatusCode;
import net.mhgoi.blog.entity.Api;
import net.mhgoi.blog.service.ApiService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * (Api)表控制层
 *
 * @author 少年
 * @since 2020-07-13 21:43:52
 */
@RestController
@RequestMapping("backendApi")
@Tag(name = "blog", description = "the blog API")
public class ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ApiService apiService;

    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @PostMapping("/{currentPage}/{size}")
    public Result selectPage(@PathVariable("currentPage") Integer currentPage, @PathVariable("size") Integer size, @RequestBody(required = false) Map<String, Object> map) {
        Page<Api> page = apiService.queryPage(currentPage, size, map);
        PageResult<Api> pageResult = new PageResult<>(page.getTotal(), page.getRecords());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    @GetMapping
    public Result selectAll() {
        return new Result(true, StatusCode.OK, "查询成功", apiService.list());
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result selectOne(@PathVariable Serializable id) {
        return new Result(true, StatusCode.OK, "查询成功", apiService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param instance 实体对象
     * @return 新增结果
     */
    @PostMapping
    public Result insert(@RequestBody Api instance) {
        return new Result(true, StatusCode.OK, "添加成功", this.apiService.save(instance));
    }

    /**
     * 修改数据
     *
     * @param instance 实体对象
     * @return 修改结果
     */
    @PutMapping
    public Result update(@RequestBody Api instance) {
        return new Result(true, StatusCode.OK, "修改成功", this.apiService.updateById(instance));

    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public Result delete(@RequestBody List<String> idList) {
        return new Result(true, StatusCode.OK, "删除成功", this.apiService.removeByIds(idList));
    }

    /**
     * 通过角色获取API
     *
     * @return
     */
    @GetMapping("/own")
    public Result getApiByRole(@RequestParam(value = "roleId") String roleId) {
        return new Result(true, StatusCode.OK, "查询成功", this.apiService.getByRoleId(roleId));
    }

    /**
     * 角色授权API
     *
     * @param roleId
     * @param apiIds
     * @return
     */
    @PostMapping("/grant/{roleId}")
    public Result grantApi(@PathVariable("roleId") String roleId, @RequestBody List<String> apiIds) {
        this.apiService.grantApi(roleId, apiIds);
        return new Result(true, StatusCode.OK, "操作成功", null);
    }

    /**
     * 分组查询API
     *
     * @return
     */
    @GetMapping("/module")
    public Result getApiGroupByModule() {
        return new Result(true, StatusCode.OK, "查询成功", apiService.getApiGroupByModule());
    }

    @GetMapping("/modules")
    public Result getModules() {
        return new Result(true, StatusCode.OK, "查询成功", apiService.getModules());
    }

}