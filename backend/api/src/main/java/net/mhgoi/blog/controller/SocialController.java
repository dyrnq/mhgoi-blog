package net.mhgoi.blog.controller;


import net.mhgoi.blog.dto.Result;
import net.mhgoi.blog.dto.StatusCode;
import net.mhgoi.blog.entity.Social;
import net.mhgoi.blog.service.SocialService;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Social)表控制层
 *
 * @author 少年
 * @since 2020-07-29 13:25:36
 */
@RestController
@RequestMapping("social")
@Tag(name = "blog", description = "the blog API")
public class SocialController {
    /**
     * 服务对象
     */
    @Resource
    private SocialService socialService;

    /**
     * 查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping
    public Result selectAll() {
        return new Result(true, StatusCode.OK, "查询成功", this.socialService.list());
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result selectOne(@PathVariable Serializable id) {
        return new Result(true, StatusCode.OK, "查询成功", socialService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param social 实体对象
     * @return 新增结果
     */
    @PostMapping
    public Result insert(@RequestBody Social social) {
        return new Result(true, StatusCode.OK, "添加成功", this.socialService.save(social));
    }

    /**
     * 修改数据
     *
     * @param social 实体对象
     * @return 修改结果
     */
    @PutMapping
    public Result update(@RequestBody Social social) {
        return new Result(true, StatusCode.OK, "修改成功", this.socialService.updateById(social));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public Result delete(@RequestBody List<String> idList) {
        return new Result(true, StatusCode.OK, "删除成功", this.socialService.removeByIds(idList));
    }
}