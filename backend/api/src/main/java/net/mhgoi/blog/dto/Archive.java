package net.mhgoi.blog.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 少年
 */
@Data
public class Archive implements Serializable {
    private static final long serialVersionUID = -9140759168854743395L;
    //日期
    private String archiveTime;
    //归档文章列表
    private List<ArchiveArticle> archiveArticles;
}
