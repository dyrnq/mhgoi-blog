package net.mhgoi.blog.controller;


import com.github.pagehelper.Page;
import io.swagger.annotations.ApiOperation;
import net.mhgoi.blog.dto.ArticleDto;
import net.mhgoi.blog.dto.PageResult;
import net.mhgoi.blog.dto.Result;
import net.mhgoi.blog.dto.StatusCode;
import net.mhgoi.blog.entity.Article;
import net.mhgoi.blog.entity.User;
import net.mhgoi.blog.service.ArticleService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文章表(Article)表控制层
 *
 * @author 少年
 * @since 2020-07-23 19:39:04
 */
@RestController
@RequestMapping("article")
@io.swagger.annotations.Api(value = "文章接口", tags = {"文章接口"})
public class ArticleController {
    /**
     * 服务对象
     */
    @Resource
    private ArticleService articleService;

    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @PostMapping("/{currentPage}/{size}")
    @ApiOperation(value = "查询文章", notes = "查询文章")
    public Result selectPage(@PathVariable("currentPage") Integer currentPage, @PathVariable("size") Integer size, @RequestBody(required = false) Map<String, Object> map) {
        Page<ArticleDto> page = this.articleService.queryPage(currentPage, size, map);
        PageResult<ArticleDto> pageResult = new PageResult<>(page.getTotal(), page.getResult());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    /**
     * 所有数据
     *
     * @return 所有数据
     */
    @GetMapping
    public Result selectAll(@RequestParam("tagName") String tagName) {
        return new Result(true, StatusCode.OK, "查询成功", this.articleService.getArticleByTag(tagName));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result selectOne(@PathVariable Serializable id) {
        return new Result(true, StatusCode.OK, "查询成功", articleService.getById(id.toString()));
    }

    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping
    public Result insert(@RequestBody Map<String, Object> map) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Article article = new Article();
        article.setAuthorId(user.getId());
        article.setTitle((String) map.get("title"));
        article.setSlug((String) map.get("slug"));
        article.setTop((Integer) map.get("top"));
        article.setImageUrl((String) map.get("imageUrl"));
        article.setCategoryId((String) map.get("categoryId"));
        article.setSummary((String) map.get("summary"));
        article.setContentMarkdown((String) map.get("contentMarkdown"));
        List<String> tagNames = (List<String>) map.get("tagNames");
        articleService.insertArticle(article, tagNames);

        Map m = new HashMap();
        m.put("id", article.getId());
        return new Result(true, StatusCode.OK, "添加成功", m);
    }

    /**
     * 修改数据
     *
     * @return 修改结果
     */
    @PutMapping
    public Result update(@RequestBody Map<String, Object> map) {
        Article article = new Article();
        article.setId((String) map.get("id"));
        if (map.get("title") != null && !map.get("title").equals("")) {
            article.setTitle((String) map.get("title"));
        }
        if (map.get("top") != null) {
            article.setTop((Integer) map.get("top"));
        }
        if (map.get("categoryId") != null && !map.get("categoryId").equals("")) {
            article.setCategoryId((String) map.get("categoryId"));
        }
        if (map.get("summary") != null && !map.get("summary").equals("")) {
            article.setSummary((String) map.get("summary"));
        }
        if (map.get("contentMarkdown") != null && !map.get("contentMarkdown").equals("")) {
            article.setContentMarkdown((String) map.get("contentMarkdown"));
        }
        article.setImageUrl((String) map.get("imageUrl"));
        article.setSlug((String) map.get("slug"));
        List<String> tagNames = null;
        if (map.get("tagNames") != null) {
            tagNames = (List<String>) map.get("tagNames");
        }
        articleService.updateArticle(article, tagNames);
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
        return new Result(true, StatusCode.OK, "删除成功", this.articleService.removeByIds(idList));
    }

    /**
     * 文章浏览
     *
     * @param articleId 文章ID
     * @return
     */
    @PutMapping("/visit/{articleId}")
    public Result visit(@PathVariable String articleId) {
        articleService.view(articleId);
        return new Result(true, StatusCode.OK, "操作成功");
    }

    @GetMapping("/slug/{slug}")
    public Result selectOneBySlug(@PathVariable Serializable slug) {
        return new Result(true, StatusCode.OK, "查询成功", articleService.getBySlug(slug.toString()));
    }

    /**
     * 文章归档
     *
     * @return 归档结果
     */
    @GetMapping("/archive")
    public Result archive() {
        return new Result(true, StatusCode.OK, "操作成功", this.articleService.getArchiveByMonth());
    }
}