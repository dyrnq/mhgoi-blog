package net.mhgoi.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.mhgoi.blog.entity.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章标签表(Tag)表数据库访问层
 *
 * @author 少年
 * @since 2020-07-23 19:40:35
 */
public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 查询标签以及各个标签对应的文章数
     *
     * @return 结果
     */
    List<Tag> getGroupedTag();


//    List<String> getTagIdsByTagNames(@Param("tagNames") List<String> tagNames);


    Tag getTagByTagName(@Param("tagName") String tagName);


    List<String> getTagNouse();
}