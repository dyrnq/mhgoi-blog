package net.mhgoi.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.mhgoi.blog.dto.PageResult;
import net.mhgoi.blog.dto.Result;
import net.mhgoi.blog.dto.StatusCode;
import net.mhgoi.blog.entity.Category;
import net.mhgoi.blog.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 文章分类表(Category)表控制层
 *
 * @author 少年
 * @since 2020-07-23 19:32:03
 */
@RestController
@RequestMapping("category")
@io.swagger.annotations.Api(value = "文章类别接口", tags = {"文章类别接口"})
public class CategoryController {
    /**
     * 服务对象
     */
    @Resource
    private CategoryService categoryService;

    /**
     * 查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping
    public Result selectAll(@RequestParam(value = "grouped", required = false, defaultValue = "false") Boolean grouped) {
        List<Category> categoryList;
        if (grouped) {
            categoryList = this.categoryService.getGroupedCategory();
        } else {
            categoryList = categoryService.getAllCategory();
        }
        return new Result(true, StatusCode.OK, "查询成功", categoryList);
    }


    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @PostMapping("/{currentPage}/{size}")
    public Result selectPage(@PathVariable("currentPage") Integer currentPage, @PathVariable("size") Integer size, @RequestBody(required = false) Map<String, Object> map) {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort");
        Page<Category> page = this.categoryService.page(new Page<>(currentPage, size), wrapper);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Category>(page.getTotal(), page.getRecords()));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result selectOne(@PathVariable Serializable id) {
        return new Result(true, StatusCode.OK, "查询成功", categoryService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param category 实体对象
     * @return 新增结果
     */
    @PostMapping
    public Result insert(@RequestBody Category category) {
        return new Result(true, StatusCode.OK, "添加成功", this.categoryService.save(category));
    }

    /**
     * 修改数据
     *
     * @param category 实体对象
     * @return 修改结果
     */
    @PutMapping
    public Result update(@RequestBody Category category) {
        return new Result(true, StatusCode.OK, "修改成功", this.categoryService.updateById(category));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public Result delete(@RequestBody List<String> idList) {
        return new Result(true, StatusCode.OK, "删除成功", this.categoryService.removeByIds(idList));
    }
}