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
 * (Friend)表实体类
 *
 * @author 少年
 * @since 2020-08-02 20:10:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Friend implements Serializable {
    private static final long serialVersionUID = 253735747678841160L;
    //ID
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    //站名
    private String siteName;
    //网站URL
    private String path;
    //头像
    private String avatar;
    //概述
    private String description;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}