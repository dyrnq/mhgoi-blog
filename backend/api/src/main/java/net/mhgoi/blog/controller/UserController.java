package net.mhgoi.blog.controller;


import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.mhgoi.blog.dto.PageResult;
import net.mhgoi.blog.dto.Result;
import net.mhgoi.blog.dto.StatusCode;
import net.mhgoi.blog.entity.User;
import net.mhgoi.blog.exception.TokenExpiredException;
import net.mhgoi.blog.service.RoleService;
import net.mhgoi.blog.service.UserService;
import net.mhgoi.blog.service.VerificationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 用户表(User)表控制层
 *
 * @author 少年
 * @since 2020-07-02 20:10:30
 */
@RestController
@RequestMapping("user")
@Api(value = "用户接口", tags = {"用户接口"})
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    @Resource
    private VerificationService verificationService;

    @Resource
    private RoleService roleService;

    /**
     * 分页查询数据
     */
    @PostMapping("/{currentPage}/{size}")
    @ApiOperation(value = "查询用户", notes = "查询用户")
    public Result selectPage(@PathVariable("currentPage") Integer currentPage, @PathVariable("size") Integer size, @RequestBody(required = false) Map<String, Object> map) {
        Page<User> page = this.userService.queryPage(currentPage, size, map);
        PageResult<User> pageResult = new PageResult<>(page.getTotal(), page.getResult());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    @ApiOperation(value = "根据ID查询用户", notes = "根据ID查询用户")
    public Result selectOne(@PathVariable Serializable id) {
        return new Result(true, StatusCode.OK, "查询成功", userService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param user 实体对象
     * @return 新增结果
     */
    @PostMapping
    public Result insert(@RequestBody User user) {
        return new Result(true, StatusCode.OK, "添加成功", this.userService.saveUser(user));
    }

    /**
     * 修改数据
     *
     * @param user 实体对象
     * @return 修改结果
     */
    @PutMapping
    public Result update(@RequestBody User user) {
        this.userService.updateById(user);
        return new Result(true, StatusCode.OK, "修改成功", null);
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public Result delete(@RequestBody List<String> idList) {
        return new Result(true, StatusCode.OK, "删除成功", this.userService.removeByIds(idList));
    }

    /**
     * 注册
     *
     * @param user      用户
     * @param checkCode 验证码
     * @return 结果
     */
    @PostMapping("/register/{checkCode}")
    public Result register(@RequestBody User user, @PathVariable String checkCode) {
        boolean flag = verificationService.validateCheckCode(user.getMobile(), checkCode);
        if (!flag) {
            return new Result(false, StatusCode.CHECK_CODE_ERROR, "验证码错误");
        }

        userService.saveUser(user);
        roleService.grantRole(user.getId(), Collections.singletonList("6e2464dfacfc7bd9d0d15b397eb7e001"));
        return new Result(true, StatusCode.OK, "注册成功");
    }

    /**
     * 刷新 Token
     *
     * @param token 旧token
     * @return 结果
     */
    @PostMapping("/refresh")
    public Result refreshToken(@RequestHeader(value = "Freshman") String token) {
        return new Result(true, StatusCode.OK, "操作成功", userService.refreshToken(token));
    }

    /**
     * 获取用户信息
     *
     * @param token 携带的token
     * @return 用户信息
     * @throws TokenExpiredException 令牌过期
     */
    @GetMapping("/info")
    @ApiOperation(value = "关于我", notes = "关于我")
    public Result getUserInfo(@RequestParam String token) throws TokenExpiredException {
        return new Result(true, StatusCode.OK, "操作成功", userService.getUserInfo(token));
    }

    /**
     * 绑定邮箱
     *
     * @param map       邮箱 Map
     * @param checkCode 验证码
     * @return 结果
     */
    @PostMapping("/email/{checkCode}")
    @PreAuthorize("isAuthenticated()")
    public Result bindEmail(@RequestBody Map<String, String> map, @PathVariable String checkCode) {
        String email = map.get("email");
        if (email == null) return new Result(false, StatusCode.ERROR, "操作失败");
        boolean flag = verificationService.validateCheckCode(email, checkCode);
        if (!flag) return new Result(false, StatusCode.CHECK_CODE_ERROR, "验证码错误");
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = new User();
        user.setId(currentUser.getId());
        user.setEmail(email);
        user.setUsername(currentUser.getUsername());
        userService.updateById(user);
        return new Result(true, StatusCode.OK, "绑定成功");
    }
}