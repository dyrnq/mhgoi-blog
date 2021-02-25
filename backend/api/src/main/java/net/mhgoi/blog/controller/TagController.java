package net.mhgoi.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.mhgoi.blog.dto.PageResult;
import net.mhgoi.blog.dto.Result;
import net.mhgoi.blog.dto.StatusCode;
import net.mhgoi.blog.entity.Tag;
import net.mhgoi.blog.service.TagService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 文章标签表(Tag)表控制层
 *
 * @author 少年
 * @since 2020-07-23 19:40:35
 */
@RestController
@RequestMapping("tag")
@io.swagger.annotations.Api(value = "标签接口", tags = {"标签接口"})
public class TagController {
    /**
     * 服务对象
     */
    @Resource
    private TagService tagService;

    /**
     * 查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping
    public Result selectAll(@RequestParam(value = "grouped", required = false, defaultValue = "false") Boolean grouped) {
        List<Tag> tagList;
        if (grouped) {
            tagList = this.tagService.getGroupedTag();
        } else {
            QueryWrapper<Tag> wrapper = new QueryWrapper<>();
            wrapper.select("id", "name").orderByAsc("sort");
            tagList = this.tagService.list(wrapper);
        }
        return new Result(true, StatusCode.OK, "查询成功", tagList);
    }

    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @PostMapping("/{currentPage}/{size}")
    public Result selectPage(@PathVariable("currentPage") Integer currentPage, @PathVariable("size") Integer size, @RequestBody(required = false) Map<String, Object> map) {
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort");
        Page<Tag> page = this.tagService.page(new Page<>(currentPage, size), wrapper);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Tag>(page.getTotal(), page.getRecords()));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result selectOne(@PathVariable Serializable id) {
        return new Result(true, StatusCode.OK, "查询成功", tagService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param tag 实体对象
     * @return 新增结果
     */
    @PostMapping
    public Result insert(@RequestBody Tag tag) {
        return new Result(true, StatusCode.OK, "添加成功", this.tagService.save(tag));
    }

    /**
     * 修改数据
     *
     * @param tag 实体对象
     * @return 修改结果
     */
    @PutMapping
    public Result update(@RequestBody Tag tag) {
        return new Result(true, StatusCode.OK, "修改成功", this.tagService.updateById(tag));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public Result delete(@RequestBody List<String> idList) {
        return new Result(true, StatusCode.OK, "删除成功", this.tagService.removeByIds(idList));
    }
}