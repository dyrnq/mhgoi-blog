package net.mhgoi.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.mhgoi.blog.entity.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章分类表(Category)表数据库访问层
 *
 * @author 少年
 * @since 2020-07-23 19:32:03
 */
public interface CategoryMapper extends BaseMapper<Category> {
    /**
     * 查询文章分类及分类对应的文章数
     *
     * @return 查询结果
     */
    List<Category> getGroupedCategory();

    List<Category> getAllCategory();

    String getIdBySlug(@Param("slug") String slug);
}