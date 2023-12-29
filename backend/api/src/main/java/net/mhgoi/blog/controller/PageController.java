package net.mhgoi.blog.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.mhgoi.blog.dto.PageResult;
import net.mhgoi.blog.dto.Result;
import net.mhgoi.blog.dto.StatusCode;
import net.mhgoi.blog.service.PageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文章表(Page)表控制层
 *
 * @author 少年
 * @since 2020-07-23 19:39:04
 */
@RestController
@RequestMapping("page")
@io.swagger.annotations.Api(value = "页面接口", tags = {"页面接口"})
public class PageController {
    /**
     * 服务对象
     */
    @Resource
    private PageService pageService;

    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @PostMapping("/{currentPage}/{size}")
    public Result selectPage(@PathVariable("currentPage") Integer currentPage, @PathVariable("size") Integer size, @RequestBody(required = false) Map<String, Object> map) {
        Page<net.mhgoi.blog.entity.Page> page = this.pageService.queryPage(currentPage, size, map);
        PageResult<net.mhgoi.blog.entity.Page> pageResult = new PageResult<>(page.getTotal(), page.getRecords());
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
        return new Result(true, StatusCode.OK, "查询成功", pageService.getById(id.toString()));
    }

    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping
    public Result insert(@RequestBody Map<String, Object> map) {
        net.mhgoi.blog.entity.Page page = new net.mhgoi.blog.entity.Page();
        page.setTitle((String) map.get("title"));
        page.setSlug((String) map.get("slug"));
        page.setContentMarkdown((String) map.get("contentMarkdown"));
        page.setModifiedTime(new java.util.Date());
        page.setCreateTime(new java.util.Date());
        pageService.insertPage(page);

        Map m = new HashMap();
        m.put("id", page.getId());
        return new Result(true, StatusCode.OK, "添加成功", m);
    }

    /**
     * 修改数据
     *
     * @return 修改结果
     */
    @PutMapping
    public Result update(@RequestBody Map<String, Object> map) {
        net.mhgoi.blog.entity.Page page = new net.mhgoi.blog.entity.Page();
        page.setId((String) map.get("id"));
        page.setTitle((String) map.get("title"));
        page.setSlug((String) map.get("slug"));
        page.setContentMarkdown((String) map.get("contentMarkdown"));
        page.setModifiedTime(new java.util.Date());
        pageService.updatePage(page);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public Result delete(@RequestBody List<String> idList) {
        return new Result(true, StatusCode.OK, "删除成功", this.pageService.removeByIds(idList));
    }

    @GetMapping("/slug/{slug}")
    public Result selectOneBySlug(@PathVariable Serializable slug) {
        return new Result(true, StatusCode.OK, "查询成功", pageService.getBySlug(slug.toString()));
    }


}