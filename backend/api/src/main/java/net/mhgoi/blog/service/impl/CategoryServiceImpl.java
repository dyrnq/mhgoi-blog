package net.mhgoi.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.mhgoi.blog.entity.Category;
import net.mhgoi.blog.mapper.CategoryMapper;
import net.mhgoi.blog.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文章分类表(Category)表服务实现类
 *
 * @author 少年
 * @since 2020-07-23 19:32:03
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Override
    public List<Category> getGroupedCategory() {
        return this.baseMapper.getGroupedCategory();
    }

    @Override
    public List<Category> getAllCategory() {
        return baseMapper.getAllCategory();
    }
}