package net.mhgoi.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * API白名单(SysWhiteList)表实体类
 *
 * @author 少年
 * @since 2020-08-03 06:26:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysOpenApi implements Serializable {
    private static final long serialVersionUID = 8529642972427240572L;
    //ID
    private String id;
    //API访问路径
    private String apiUrl;
    //API描述
    private String description;
}