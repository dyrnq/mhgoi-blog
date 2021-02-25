package net.mhgoi.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.mhgoi.blog.dto.Result;
import net.mhgoi.blog.dto.StatusCode;
import net.mhgoi.blog.entity.Friend;
import net.mhgoi.blog.service.FriendService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Friend)表控制层
 *
 * @author 少年
 * @since 2020-08-02 20:10:04
 */
@RestController
@RequestMapping("friend")
@io.swagger.annotations.Api(value = "友接接口", tags = {"友接接口"})
public class FriendController {
    /**
     * 服务对象
     */
    @Resource
    private FriendService friendService;

    /**
     * 查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping
    public Result selectAll() {
        QueryWrapper<Friend> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("create_time");
        return new Result(true, StatusCode.OK, "查询成功", this.friendService.list(wrapper));
    }

    /**
     * 新增数据
     *
     * @param friend 实体对象
     * @return 新增结果
     */
    @PostMapping
    public Result insert(@RequestBody Friend friend) {
        return new Result(true, StatusCode.OK, "添加成功", this.friendService.save(friend));
    }

    /**
     * 修改数据
     *
     * @param friend 实体对象
     * @return 修改结果
     */
    @PutMapping
    public Result update(@RequestBody Friend friend) {
        return new Result(true, StatusCode.OK, "修改成功", this.friendService.updateById(friend));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public Result delete(@RequestBody List<String> idList) {
        return new Result(true, StatusCode.OK, "删除成功", this.friendService.removeByIds(idList));
    }
}