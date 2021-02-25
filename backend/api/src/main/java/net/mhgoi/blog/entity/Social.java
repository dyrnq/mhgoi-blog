package net.mhgoi.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (Social)表实体类
 *
 * @author 少年
 * @since 2020-07-29 13:25:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Social implements Serializable {
    private static final long serialVersionUID = -6163046319567219413L;

    private String id;

    private String title;

    private String icon;

    private String color;

    private String href;
}