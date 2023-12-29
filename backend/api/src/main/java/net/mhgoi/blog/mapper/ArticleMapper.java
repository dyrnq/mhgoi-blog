package net.mhgoi.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.mhgoi.blog.dto.Archive;
import net.mhgoi.blog.dto.ArticleDto;
import net.mhgoi.blog.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 文章表(Article)表数据库访问层
 *
 * @author 少年
 * @since 2020-07-23 19:39:04
 */
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 条件分页查询文章
     *
     * @param pg
     * @param map 查询条件
     * @return 文章列表
     */
    Page<ArticleDto> queryPage(Page<ArticleDto> pg, Map<String, Object> map);

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
     * 添加文章和文章标签的关联
     *
     * @param articleId 文章ID
     * @param tagIds    文章标签ID列表
     * @return 添加结果
     */
    int addArticleTags(@Param("articleId") String articleId, @Param("tagIds") List<String> tagIds);

    /**
     * 删除文章和文章标签关联
     *
     * @param articleId 文章ID
     * @return 删除结果
     */
    int deleteArticleTags(@Param("articleId") String articleId);

    /**
     * 增加文章评论数
     *
     * @param articleId 文章ID
     * @param counts    评论数
     * @return 增加结果
     */
    int incCommentsCount(@Param("articleId") String articleId, @Param("counts") int counts);

    /**
     * 增加文章浏览数
     *
     * @param articleId 文章ID
     * @param counts    浏览数
     * @return 增加结果
     */
    int incViewsCount(@Param("articleId") String articleId, @Param("counts") int counts);

    /**
     * 文章归档
     *
     * @return 归档列表
     */
    List<Archive> getArchive();

    /**
     * 根据标签查询文章
     *
     * @param tagId 标签ID
     * @return 文章列表
     */
    List<Article> getArticleByTag(@Param("tagId") String tagId);
}