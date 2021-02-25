package net.mhgoi.blog.controller;

import net.mhgoi.blog.dto.Result;
import net.mhgoi.blog.dto.StatusCode;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
//import net.mhgoi.blog.util.QiniuUtil;

/**
 * @author 少年
 */
@RestController
@RequestMapping("file")
@io.swagger.annotations.Api(value = "文件接口", tags = {"文件接口"})

public class FileController {

    /**
     * 获取上传文件token
     *
     * @return
     */
    @GetMapping("/token")
    public Result getToken() {
        return new Result(true, StatusCode.OK, "NOTIMPL", null);
    }

    /**
     * 删除文件
     *
     * @param map 文件名{"key":"xxx"}
     * @return
     */
    @DeleteMapping
    public Result deleteFile(@RequestBody Map<String, String> map) {
        return new Result(true, StatusCode.OK, "NOTIMPL", null);

    }

    /**
     * 查询所有文件
     *
     * @param prefix 文件名前缀
     * @return
     */
    @GetMapping("/list")
    public Result getFileList(@RequestParam String prefix) {
        return new Result(true, StatusCode.OK, "NOTIMPL", null);
    }
}
