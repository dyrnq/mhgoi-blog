package net.mhgoi.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 文章标签表(Tag)表实体类
 *
 * @author 少年
 * @since 2020-07-23 19:40:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag implements Serializable {
    private static final long serialVersionUID = -7154971333862238199L;
    //标签ID
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    //标签名
    private String name;
    //标签排序
    private Integer sort;

    //该标签下的文章数
    @TableField(exist = false)
    private Integer counts;
}