package net.mhgoi.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 少年
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto implements Serializable {
    private static final long serialVersionUID = 1L;
    //ID
    private String id;
    //作者ID
    private String authorId;
    //文章标题
    private String title;
    //文章分类ID
    private String categoryId;
    //文章概述
    private String summary;
    //内容(html格式)
    private String contentHtml;
    //内容(markdown格式)
    private String contentMarkdown;
    //浏览量
    private Integer viewsCount;
    //评论数
    private Integer commentsCount;
    //置顶（0:不置顶，1:置顶）
    private Integer top;
    //文章图片
    private String imageUrl;
    //发表日期
    private Date createTime;
    //修改日期
    private Date modifiedTime;
    //状态（1:启用，0:删除）
    private String state;

    private String slug;
    private String username;
    private String fullname;
    private String avatar;
    private String categoryName;
    // private List<Tag> tags;
    private List<String> tagNames;
}
