package net.mhgoi.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.mhgoi.blog.entity.Category;

import java.util.List;

/**
 * 文章分类表(Category)表服务接口
 *
 * @author 少年
 * @since 2020-07-23 19:32:03
 */
public interface CategoryService extends IService<Category> {
    /**
     * 查询文章分类及分类对应的文章数
     *
     * @return 查询结果
     */
    List<Category> getGroupedCategory();

    List<Category> getAllCategory();
}