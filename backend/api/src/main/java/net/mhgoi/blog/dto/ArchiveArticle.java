package net.mhgoi.blog.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 少年
 */
@Data
public class ArchiveArticle implements Serializable {
    private static final long serialVersionUID = 7672901854410644437L;
    //ID
    private String id;
    //文章标题
    private String title;
    //文章发表日
    private String day;

    private String slug;
}
