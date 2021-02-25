package net.mhgoi.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.mhgoi.blog.entity.Page;

import java.util.List;
import java.util.Map;

/**
 * 页面表(Page)表数据库访问层
 *
 * @author 少年
 * @since 2020-07-23 19:39:04
 */
public interface PageMapper extends BaseMapper<Page> {

    /**
     * 条件分页查询文章
     *
     * @param map 查询条件
     * @return 文章列表
     */
    List<Page> queryPage(Map<String, Object> map);

    /**
     * 根据ID查询文章
     *
     * @param id 文章ID
     * @return 文章
     */
    Page getById(String id);

    /**
     * 根据slug查询文章
     *
     * @param slug 文章slug
     * @return 文章
     */
    Page getBySlug(String slug);


}