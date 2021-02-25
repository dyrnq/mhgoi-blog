package net.mhgoi.blog.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 少年
 */
@Data
@NoArgsConstructor
public class Result {
    private boolean flag;
    private Integer code;
    private String msg;
    private Object data;

    public Result(boolean flag, Integer code, String msg) {
        this.flag = flag;
        this.code = code;
        this.msg = msg;
    }

    public Result(boolean flag, Integer code, String msg, Object data) {
        this.flag = flag;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
