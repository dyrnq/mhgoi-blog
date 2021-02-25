package net.mhgoi.blog.handler;

import io.jsonwebtoken.SignatureException;
import net.mhgoi.blog.dto.Result;
import net.mhgoi.blog.dto.StatusCode;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 少年
 */
@RestControllerAdvice

public class GlobalExceptionHandler {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DataAccessException.class)
    public Result dataAccess(DataAccessException e) {
        Throwable cause = e.getCause();
//        if (cause instanceof MySQLIntegrityConstraintViolationException) {
//            return new Result(false, StatusCode.REP_KEY, "操作失败");
//        }
        log.error(e.getMessage(), e);
        return new Result(false, StatusCode.ERROR, "操作失败" + (cause.getMessage() != null ? cause.getMessage() : ""));
    }

    @ExceptionHandler(SignatureException.class)
    public Result signature(SignatureException e) {
        return new Result(false, StatusCode.BAD_TOKEN, "操作失败");
    }

    @ExceptionHandler(Exception.class)
    public Result exception(Exception e) {
        log.error(e.getMessage(), e);
        return new Result(false, StatusCode.ERROR, "服务错误");
    }
}
