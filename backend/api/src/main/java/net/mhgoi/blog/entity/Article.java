package net.mhgoi.blog.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章表(Article)表实体类
 *
 * @author 少年
 * @since 2020-07-30 18:59:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article implements Serializable {
    private static final long serialVersionUID = 848902311482853544L;
    //ID
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    //作者ID
    private String authorId;
    //文章标题
    private String title;
    //文章分类ID
    private String categoryId;
    //文章概述
    private String summary;
    //内容(markdown格式)
    private String contentMarkdown;
    //浏览量
    private Integer viewsCount;
    //评论数
    private Integer commentsCount;
    //文章图片
    private String imageUrl;
    //置顶（0:不置顶，1:置顶）
    private Integer top;
    //发表日期
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    //修改日期
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;
    //状态（1:启用，0:删除）
    private String state;

    private String slug;
}