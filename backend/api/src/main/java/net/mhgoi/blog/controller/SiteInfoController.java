package net.mhgoi.blog.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import net.mhgoi.blog.dto.Result;
import net.mhgoi.blog.dto.StatusCode;
import net.mhgoi.blog.entity.SiteInfo;
import net.mhgoi.blog.service.RuntimeService;
import net.mhgoi.blog.service.SiteInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (SiteInfo)表控制层
 *
 * @author 少年
 * @since 2020-07-31 07:50:23
 */
@RestController
@RequestMapping("siteInfo")
@Tag(name = "blog", description = "the blog API")
public class SiteInfoController {
    /**
     * 服务对象
     */
    @Resource
    private SiteInfoService siteInfoService;

    @Resource
    private RuntimeService runtimeService;

    /**
     * 查询数据
     */
    @GetMapping
    public Result selectOne() {
        return new Result(true, StatusCode.OK, "查询成功", this.siteInfoService.getOne(null));
    }

    @GetMapping("/runtime")
    public Result getRuntime() {
        return new Result(true, StatusCode.OK, "查询成功", this.runtimeService.getRuntime());
    }
    
    @GetMapping("/thread")
    public Result getThread() {
        return new Result(true, StatusCode.OK, "查询成功", this.runtimeService.getThread());
    }

    /**
     * 修改数据
     *
     * @param siteInfo 实体对象
     * @return 修改结果
     */
    @PutMapping
    public Result update(@RequestBody SiteInfo siteInfo) {
        return new Result(true, StatusCode.OK, "修改成功", this.siteInfoService.updateById(siteInfo));
    }
}