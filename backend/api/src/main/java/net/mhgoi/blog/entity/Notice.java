package net.mhgoi.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (Notice)表实体类
 *
 * @author 少年
 * @since 2020-08-14 16:55:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notice implements Serializable {
    private static final long serialVersionUID = 7165025726148892842L;

    private String id;

    private String content;

    private Integer important;

    private Integer sort;
}