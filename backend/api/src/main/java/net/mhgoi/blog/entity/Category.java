package net.mhgoi.blog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 文章分类表(Category)表实体类
 *
 * @author 少年
 * @since 2020-08-04 21:22:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {
    private static final long serialVersionUID = 610461421273906576L;
    //分类ID
    private String id;
    //分类名
    private String name;
    //分类图标URL
    private String icon;
    //分类排序
    private Integer sort;
    private String slug;

    //该分类下的文章数
    @TableField(exist = false)
    private Integer counts;
}