package net.mhgoi.blog.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.mhgoi.blog.dto.PageResult;
import net.mhgoi.blog.dto.Result;
import net.mhgoi.blog.dto.StatusCode;
import net.mhgoi.blog.entity.Role;
import net.mhgoi.blog.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Role)表控制层
 *
 * @author 少年
 * @since 2020-07-19 17:57:18
 */
@RestController
@RequestMapping("role")
@io.swagger.annotations.Api(value = "角色接口", tags = {"角色接口"})
public class RoleController {
    /**
     * 服务对象
     */
    @Resource
    private RoleService roleService;

    /**
     * 查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping
    public Result selectAll() {
        return new Result(true, StatusCode.OK, "查询成功", roleService.list());
    }


    /**
     * 分页查询数据
     *
     * @return 分页数据
     */
    @PostMapping("/{currentPage}/{size}")
    public Result selectPage(@PathVariable("currentPage") Integer currentPage, @PathVariable("size") Integer size) {
        Page<Role> page = roleService.page(new Page<>(currentPage, size));
        PageResult<Role> pageResult = new PageResult<>(page.getTotal(), page.getRecords());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result selectOne(@PathVariable Serializable id) {
        return new Result(true, StatusCode.OK, "查询成功", roleService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param role 实体对象
     * @return 新增结果
     */
    @PostMapping
    public Result insert(@RequestBody Role role) {
        return new Result(true, StatusCode.OK, "添加成功", this.roleService.save(role));
    }

    /**
     * 修改数据
     *
     * @param role 实体对象
     * @return 修改结果
     */
    @PutMapping
    public Result update(@RequestBody Role role) {
        return new Result(true, StatusCode.OK, "修改成功", this.roleService.updateById(role));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public Result delete(@RequestBody List<String> idList) {
        return new Result(true, StatusCode.OK, "删除成功", this.roleService.removeByIds(idList));
    }

    /**
     * 查询用户拥有角色
     *
     * @param userId
     * @return
     */
    @GetMapping("/own")
    public Result getByUsername(@RequestParam(value = "userId") String userId) {
        return new Result(true, StatusCode.OK, "查询成功", this.roleService.getRoleByUserId(userId));
    }

    @PostMapping("/grant/{userId}")
    public Result grantRole(@PathVariable("userId") String userId, @RequestBody List<String> roleIds) {
        this.roleService.grantRole(userId, roleIds);
        return new Result(true, StatusCode.OK, "操作成功", null);
    }
}