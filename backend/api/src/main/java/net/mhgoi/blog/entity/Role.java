package net.mhgoi.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (Role)表实体类
 *
 * @author 少年
 * @since 2020-07-19 17:57:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {
    private static final long serialVersionUID = -4898172326791315366L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String roleCode;
    //角色名
    private String roleName;
    //角色描述
    private String description;
}