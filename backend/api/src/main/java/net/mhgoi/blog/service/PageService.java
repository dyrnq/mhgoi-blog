package net.mhgoi.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;


/**
 * 页面表(Page)表服务接口
 *
 * @author 少年
 * @since 2020-07-23 19:39:04
 */
public interface PageService extends IService<net.mhgoi.blog.entity.Page> {
    /**
     * 分页查询页面
     *
     * @param currentPage 当前页
     * @param size        每页大小
     * @param map         查询条件
     * @return 查询页面结果
     */
    Page<net.mhgoi.blog.entity.Page> queryPage(Integer currentPage, Integer size, Map<String, Object> map);

    /**
     * 根据ID查询页面
     *
     * @param id 页面ID
     * @return 页面
     */
    net.mhgoi.blog.entity.Page getById(String id);


    /**
     * 根据slug查询页面
     *
     * @param slug 页面slug
     * @return 页面
     */
    net.mhgoi.blog.entity.Page getBySlug(String slug);

    /**
     * 插入页面
     *
     * @param page   页面
     */
    void insertPage(net.mhgoi.blog.entity.Page page);

    /**
     * 更新页面
     *
     * @param page   页面
     */
    void updatePage(net.mhgoi.blog.entity.Page page);


}