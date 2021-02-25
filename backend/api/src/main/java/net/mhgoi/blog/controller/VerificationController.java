package net.mhgoi.blog.controller;

import net.mhgoi.blog.dto.Result;
import net.mhgoi.blog.dto.StatusCode;
import net.mhgoi.blog.entity.User;
import net.mhgoi.blog.service.UserService;
import net.mhgoi.blog.service.VerificationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 少年
 */
@RestController
@RequestMapping("verification")
public class VerificationController {

    @Resource
    private VerificationService verificationService;

    @Resource
    private UserService userService;

    /**
     * 发送邮箱验证码
     *
     * @param map 数据{"email":"xxx"}
     * @return 结果
     */
    @PostMapping("/email")
    public Result sendEmailCode(@RequestBody Map<String, String> map) {
        String email = map.get("email");
        if (email == null) return new Result(false, StatusCode.ERROR, "邮箱为空");
        verificationService.sendEmailCode(email);
        return new Result(true, StatusCode.OK, "操作成功");
    }

    /**
     * 发送短信验证码
     *
     * @param map 数据{"mobile":"xxx"}
     * @return 结果
     */
    @PostMapping("/mobile")
    public Result sendMobileCode(@RequestBody Map<String, String> map) {
        String mobile = map.get("mobile");
        User user = userService.getByMobile(mobile);
        if (user != null) return new Result(false, StatusCode.REP_MOBILE, "手机号已存在");
        boolean flag = verificationService.validateMobileCodeCount(mobile);
        if (!flag) return new Result(false, StatusCode.TOO_MANY_REQUESTS, "操作失败");
        verificationService.sendMobileCode(mobile);
        return new Result(true, StatusCode.OK, "操作成功");
    }
}
