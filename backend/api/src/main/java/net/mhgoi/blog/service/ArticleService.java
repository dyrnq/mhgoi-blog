package net.mhgoi.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.mhgoi.blog.dto.Archive;
import net.mhgoi.blog.dto.ArticleDto;
import net.mhgoi.blog.entity.Article;

import java.util.List;
import java.util.Map;

/**
 * 文章表(Article)表服务接口
 *
 * @author 少年
 * @since 2020-07-23 19:39:04
 */
public interface ArticleService extends IService<Article> {
    /**
     * 分页查询文章
     *
     * @param currentPage 当前页
     * @param size        每页大小
     * @param map         查询条件
     * @return 查询文章结果
     */
    Page<ArticleDto> queryPage(Integer currentPage, Integer size, Map<String, Object> map);

    /**
     * 根据ID查询文章
     *
     * @param id 文章ID
     * @return 文章
     */
    ArticleDto getById(String id);


    /**
     * 根据slug查询文章
     *
     * @param slug 文章slug
     * @return 文章
     */
    ArticleDto getBySlug(String slug);

    /**
     * 插入文章
     *
     * @param article  文章
     * @param tagNames 文章标签ID列表
     */
    void insertArticle(Article article, List<String> tagNames);

    /**
     * 更新文章
     *
     * @param article  文章
     * @param tagNames 文章标签ID列表
     */
    void updateArticle(Article article, List<String> tagNames);

    /**
     * 增加文章评论数
     *
     * @param articleId 文章ID
     * @param counts    评论数
     */
    void incCommentsCount(String articleId, int counts);

    /**
     * 浏览文章（暂存Redis）
     *
     * @param articleId 文章ID
     */
    void view(String articleId);

    /**
     * 更新文章浏览数（从Redis到MySQL）
     */
    void doUpdateArticleViews();

    /**
     * 获取每月文章归档
     *
     * @return 归档结果
     */
    List<Archive> getArchiveByMonth();

    /**
     * 根据标签查询文章
     *
     * @param tagId 标签ID
     * @return 文章列表
     */
    List<Article> getArticleByTag(String tagId);
}