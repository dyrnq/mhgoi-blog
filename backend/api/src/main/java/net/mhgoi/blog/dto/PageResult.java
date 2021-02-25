package net.mhgoi.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author 少年
 */
@Data
@AllArgsConstructor
public class PageResult<T> {
    private Long counts;
    private List<T> rows;
}
