package net.mhgoi.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.mhgoi.blog.entity.Tag;
import net.mhgoi.blog.mapper.TagMapper;
import net.mhgoi.blog.service.TagService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文章标签表(Tag)表服务实现类
 *
 * @author 少年
 * @since 2020-07-23 19:40:35
 */
@Service("tagService")
@EnableScheduling
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TagServiceImpl.class);

    @Override
    public List<Tag> getGroupedTag() {
        return this.baseMapper.getGroupedTag();
    }

    //从左到右分别代表：秒 分 时 日 月 星期 年份
    //每天早上1点，4点，晚上23点
    @Scheduled(cron = "0 0 1,4,23 * * ?")
    //@Scheduled(cron = "*/2 * * * * ?") 
    public void doTimingPlan() {
        log.debug("doTimingPlan");
        List<String> tagIds = baseMapper.getTagNouse();
        if (tagIds != null && tagIds.size() > 0) {
            this.baseMapper.deleteBatchIds(tagIds);
        }


    }
}