package net.mhgoi.blog.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author 少年
 */
@Data
@ToString
public class MyFile implements Serializable {
    private static final long serialVersionUID = 8330622133979683513L;
    private String key;
    private Long fsize;
    private String mimeType;
    private Long putTime;
}
