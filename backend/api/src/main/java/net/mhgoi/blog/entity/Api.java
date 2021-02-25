package net.mhgoi.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (Api)表实体类
 *
 * @author 少年
 * @since 2020-07-13 21:43:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Api implements Serializable {
    private static final long serialVersionUID = -3513684767204776386L;
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    //API名称
    private String apiName;
    //API请求地址
    private String apiUrl;
    //API请求方式：GET、POST、PUT、DELETE
    private String apiMethod;
    //模块
    //private String moduleId;
    //是否开放
    private Integer open;
    //排序
    private Integer apiSort;
    //描述
    private String description;

    private String moduleName;
}