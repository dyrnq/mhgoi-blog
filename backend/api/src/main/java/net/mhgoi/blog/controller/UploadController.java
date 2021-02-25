package net.mhgoi.blog.controller;

import net.mhgoi.blog.components.FileUploadUtil;
import net.mhgoi.blog.dto.Result;
import net.mhgoi.blog.dto.StatusCode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 少年
 */
@RestController
@RequestMapping("upload")
@io.swagger.annotations.Api(value = "上传图片接口", tags = {"上传图片接口"})
public class UploadController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UploadController.class);
    @Resource
    private FileUploadUtil fileUploadUtil;

    @PostMapping
    public Result singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes,
                                   HttpServletRequest req,
                                   HttpServletResponse resp) {
        try {
            byte[] bytes = file.getBytes();
            String fileName = file.getOriginalFilename();
            String s = fileUploadUtil.write(fileName, bytes);
            Map<String, String> rs = new HashMap<String, String>();
            rs.put("url", s);
            return new Result(true, StatusCode.OK, "", rs);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return new Result(false, StatusCode.ERROR, e.getMessage());
        }
    }
}
