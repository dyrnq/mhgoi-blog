package net.mhgoi.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import net.mhgoi.blog.dto.Archive;
import net.mhgoi.blog.dto.ArticleDto;
import net.mhgoi.blog.entity.Article;
import net.mhgoi.blog.entity.Tag;
import net.mhgoi.blog.mapper.ArticleMapper;
import net.mhgoi.blog.mapper.CategoryMapper;
import net.mhgoi.blog.mapper.TagMapper;
import net.mhgoi.blog.service.ArticleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 文章表(Article)表服务实现类
 *
 * @author 少年
 * @since 2020-07-23 19:39:04
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    TagMapper tagMapper;

    @Resource
    CategoryMapper categoryMapper;

    @Override
    public Page<ArticleDto> queryPage(Integer currentPage, Integer size, Map<String, Object> map) {

        if (map.containsKey("categorySlug")) {
            String categorySlug = (String) map.get("categorySlug");
            if (StringUtils.isNoneBlank(categorySlug)) {
                String categoryId = categoryMapper.getIdBySlug(categorySlug);
                if (StringUtils.isNoneBlank(categoryId)) {
                    map.put("categoryId", categoryId);
                }
            }
        }
        Page<ArticleDto> page = PageHelper.startPage(currentPage, size);
        this.baseMapper.queryPage(map);
        return page;
    }

    @Override
    public ArticleDto getById(String id) {
        return this.baseMapper.getById(id);
    }

    @Override
    @Transactional
    public void insertArticle(Article article, List<String> tagNames) {
        this.baseMapper.insert(article);


        if (tagNames != null && tagNames.size() > 0) {
            List<String> tagIds = new ArrayList<String>();
            for (String tagName : tagNames) {
                tagName = StringUtils.lowerCase(tagName);
                Tag tag = tagMapper.getTagByTagName(tagName);
                if (tag == null) {
                    Tag newTag = new Tag();
                    newTag.setName(tagName);
                    tagMapper.insert(newTag);
                    tagIds.add(newTag.getId());
                } else {
                    tagIds.add(tag.getId());
                }
            }
            this.baseMapper.addArticleTags(article.getId(), tagIds);
        }
    }

    @Override
    @Transactional
    public void updateArticle(Article article, List<String> tagNames) {
        this.baseMapper.updateById(article);
        this.baseMapper.deleteArticleTags(article.getId());
        if (tagNames != null && tagNames.size() > 0) {
            List<String> tagIds = new ArrayList<String>();
            for (String tagName : tagNames) {
                tagName = StringUtils.lowerCase(tagName);
                Tag tag = tagMapper.getTagByTagName(tagName);
                if (tag == null) {
                    Tag newTag = new Tag();
                    newTag.setName(tagName);
                    tagMapper.insert(newTag);
                    tagIds.add(newTag.getId());
                } else {
                    tagIds.add(tag.getId());
                }
            }
            this.baseMapper.addArticleTags(article.getId(), tagIds);
        }
    }

    @Override
    public void incCommentsCount(String articleId, int counts) {
        this.baseMapper.incCommentsCount(articleId, counts);
    }

    //Redis实现文章浏览
    @Override
    public void view(String articleId) {
        redisTemplate.opsForSet().add("view_article_set", articleId);
        redisTemplate.opsForValue().increment("view_article_" + articleId, 1);
        redisTemplate.expire("view_article_" + articleId, 1, TimeUnit.HOURS);
    }

    /**
     * 定时将Redis中的浏览数存到MySQL
     */
    @Override
    @Scheduled(fixedDelay = 120000)
    public void doUpdateArticleViews() {
        String articleId;
        int counts;
        while ((articleId = (String) redisTemplate.opsForSet().pop("view_article_set")) != null && !"".equals(articleId)) {
            counts = (int) redisTemplate.opsForValue().get("view_article_" + articleId);
            redisTemplate.opsForValue().decrement("view_article_" + articleId, counts);
            redisTemplate.expire("view_article_" + articleId, 1, TimeUnit.HOURS);
            this.baseMapper.incViewsCount(articleId, counts);
        }
    }

    @Override
    public List<Archive> getArchiveByMonth() {
        return this.baseMapper.getArchive();
    }

    @Override
    public List<Article> getArticleByTag(String tagName) {
        Tag tag = tagMapper.getTagByTagName(tagName);
        if (tag == null) return new ArrayList<Article>();
        return this.baseMapper.getArticleByTag(tag.getId());
    }

    @Override
    public ArticleDto getBySlug(String slug) {
        return this.baseMapper.getBySlug(slug);
    }
}