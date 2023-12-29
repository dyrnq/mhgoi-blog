package net.mhgoi.blog.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import net.mhgoi.blog.dto.Result;
import net.mhgoi.blog.dto.StatusCode;
import net.mhgoi.blog.entity.Menu;
import net.mhgoi.blog.service.MenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 菜单表(Menus)表控制层
 *
 * @author 少年
 * @since 2020-07-19 18:17:24
 */
@RestController
@RequestMapping("menu")
@Tag(name = "blog", description = "the blog API")
public class MenuController {
    /**
     * 服务对象
     */
    @Resource
    private MenuService menuService;

    /**
     * 查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping
    public Result selectAll() {
        return new Result(true, StatusCode.OK, "查询成功", this.menuService.getAll());
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result selectOne(@PathVariable Serializable id) {
        return new Result(true, StatusCode.OK, "查询成功", menuService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param menu 实体对象
     * @return 新增结果
     */
    @PostMapping
    public Result insert(@RequestBody Menu menu) {
        return new Result(true, StatusCode.OK, "添加成功", this.menuService.save(menu));
    }

    /**
     * 修改数据
     *
     * @param menu 实体对象
     * @return 修改结果
     */
    @PutMapping
    public Result update(@RequestBody Menu menu) {
        return new Result(true, StatusCode.OK, "修改成功", this.menuService.updateById(menu));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public Result delete(@RequestBody List<String> idList) {
        return new Result(true, StatusCode.OK, "删除成功", this.menuService.removeByIds(idList));
    }

    /**
     * 通过角色获取菜单
     *
     * @return
     */
    @GetMapping("/own")
    public Result getMenusByRole(@RequestParam(value = "roleId") String roleId) {
        return new Result(true, StatusCode.OK, "查询成功", this.menuService.getByRoleId(roleId));
    }

    /**
     * 角色授权菜单
     *
     * @param roleId
     * @param menuIds
     * @return
     */
    @PostMapping("/grant/{roleId}")
    public Result grantMenu(@PathVariable("roleId") String roleId, @RequestBody List<String> menuIds) {
        this.menuService.grantMenu(roleId, menuIds);
        return new Result(true, StatusCode.OK, "操作成功", null);
    }
}