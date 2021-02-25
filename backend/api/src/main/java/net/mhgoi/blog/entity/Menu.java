package net.mhgoi.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.mhgoi.blog.util.Tree;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单表(Menus)表实体类
 *
 * @author 少年
 * @since 2020-07-19 18:16:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu implements Serializable, Tree<Menu> {
    private static final long serialVersionUID = -6166618713603086378L;
    //ID
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    //菜单名称
    private String menuName;
    //前端路由名
    private String routeName;
    //菜单描述
    private String description;
    //菜单地址
    private String menuUrl;
    //菜单图标
    private String menuIcon;
    //菜单父id
    private String pid;
    //菜单排序
    private Integer menuSort;

    @TableField(exist = false)
    private List<Menu> children;

    @JsonIgnore
    @Override
    public String getParentId() {
        return pid;
    }

    @Override
    public List<Menu> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<Menu> children) {
        this.children = children;
    }
}