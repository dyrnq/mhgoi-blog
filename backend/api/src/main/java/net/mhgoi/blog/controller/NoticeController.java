package net.mhgoi.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.mhgoi.blog.dto.Result;
import net.mhgoi.blog.dto.StatusCode;
import net.mhgoi.blog.entity.Notice;
import net.mhgoi.blog.service.NoticeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Notice)表控制层
 *
 * @author 少年
 * @since 2020-08-14 16:55:05
 */
@RestController
@RequestMapping("notice")
@Tag(name = "blog", description = "the blog API")
public class NoticeController {
    /**
     * 服务对象
     */
    @Resource
    private NoticeService noticeService;

    /**
     * 查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping
    public Result selectAll() {
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort");
        return new Result(true, StatusCode.OK, "查询成功", this.noticeService.list(wrapper));
    }

    /**
     * 新增数据
     *
     * @param notice 实体对象
     * @return 新增结果
     */
    @PostMapping
    public Result insert(@RequestBody Notice notice) {
        return new Result(true, StatusCode.OK, "添加成功", this.noticeService.save(notice));
    }

    /**
     * 修改数据
     *
     * @param notice 实体对象
     * @return 修改结果
     */
    @PutMapping
    public Result update(@RequestBody Notice notice) {
        return new Result(true, StatusCode.OK, "修改成功", this.noticeService.updateById(notice));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public Result delete(@RequestBody List<String> idList) {
        return new Result(true, StatusCode.OK, "删除成功", this.noticeService.removeByIds(idList));
    }
}