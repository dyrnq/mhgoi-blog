package net.mhgoi.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.mhgoi.blog.entity.Tag;

import java.util.List;

/**
 * 文章标签表(Tag)表服务接口
 *
 * @author 少年
 * @since 2020-07-23 19:40:35
 */
public interface TagService extends IService<Tag> {
    /**
     * 查询标签以及各个标签对应的文章数
     *
     * @return 结果
     */
    List<Tag> getGroupedTag();

    void doTimingPlan();
}