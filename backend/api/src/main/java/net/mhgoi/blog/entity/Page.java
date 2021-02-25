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
 * 页面表(Page)表实体类
 *
 * @author 少年
 * @since 2020-07-30 18:59:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page implements Serializable {

    private static final long serialVersionUID = -4474003486060738516L;
    //ID
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    //文章标题
    private String title;
    //内容(markdown格式)
    private String contentMarkdown;
    //发表日期
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    //修改日期
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    private String slug;
}