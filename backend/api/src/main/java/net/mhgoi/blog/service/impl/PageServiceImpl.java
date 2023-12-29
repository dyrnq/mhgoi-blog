package net.mhgoi.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.mhgoi.blog.entity.Page;
import net.mhgoi.blog.mapper.PageMapper;
import net.mhgoi.blog.service.PageService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("pageService")
public class PageServiceImpl extends ServiceImpl<PageMapper, Page> implements PageService {

    @Override
    public com.baomidou.mybatisplus.extension.plugins.pagination.Page<Page> queryPage(Integer currentPage, Integer size, Map<String, Object> map) {
        return this.baseMapper.queryPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(currentPage, size), map);
    }

    @Override
    public Page getById(String id) {
        return this.baseMapper.getById(id.toString());
    }

    @Override
    public Page getBySlug(String slug) {
        return this.baseMapper.getBySlug(slug);
    }

    @Override
    public void insertPage(Page page) {
        this.baseMapper.insert(page);

    }

    @Override
    public void updatePage(Page page) {
        this.baseMapper.updateById(page);
    }

}
