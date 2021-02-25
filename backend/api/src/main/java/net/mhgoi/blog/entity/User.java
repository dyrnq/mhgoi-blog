package net.mhgoi.blog.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户表(User)表实体类
 *
 * @author 少年
 * @since 2020-07-02 20:10:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable, UserDetails {
    private static final long serialVersionUID = -5173376861303477886L;
    //ID
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    //用户名
    private String username;
    //全名
    private String fullname;
    //密码
    private String password;
    //手机号
    private String mobile;
    //邮箱
    private String email;
    //性别
    private String sex;
    //出生年月日
    private Date birthday;
    //头像
    private String avatar;
    //个人简介
    private String description;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    //修改时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;
    //用户状态
    private String state;
    @TableField(exist = false)
    private List<Menu> menus;

    @TableField(exist = false)
    private List<String> roles;

    @JsonIgnore
    @TableField(exist = false)
    private List<GrantedAuthority> authorities;

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return "1".equals(state);
    }
}