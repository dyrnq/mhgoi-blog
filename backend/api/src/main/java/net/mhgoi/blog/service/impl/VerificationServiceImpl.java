package net.mhgoi.blog.service.impl;

import net.mhgoi.blog.components.EmailUtil;
import net.mhgoi.blog.service.VerificationService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author 少年
 */
@Service("verificationService")
public class VerificationServiceImpl implements VerificationService {

    @Resource
    RedisTemplate redisTemplate;

//    @Resource
//    RabbitTemplate rabbitTemplate;

    @Resource
    EmailUtil emailUtil;

    @Override
    public void sendEmailCode(String email) {
        //生成随机数
        String checkCode = RandomStringUtils.randomNumeric(6);
        //向缓存中放一份
        redisTemplate.opsForValue().set("checkCode_" + email, checkCode, 5, TimeUnit.MINUTES);
        emailUtil.sendEmail(email, "验证码", "您的验证码: " + checkCode);
    }

    @Override
    public void sendMobileCode(String mobile) {
//        //生成随机数
//        String checkCode = RandomStringUtils.randomNumeric(6);
//        //向缓存中放一份
//        redisTemplate.opsForValue().set("checkCode_" + mobile, checkCode, 5, TimeUnit.MINUTES);
//        //给用户发一份(用到rabbitmq)
//        Map<String, String> map = new HashMap<>();
//        map.put("mobile", mobile);
//        map.put("checkCode", checkCode);
//        rabbitTemplate.convertAndSend("sms", map);
    }

    @Override
    public boolean validateMobileCodeCount(String mobile) {
        //判断用户发送次数
        Integer count = (Integer) redisTemplate.opsForValue().get("checkCode_count_" + mobile);
        count = count == null ? 0 : count;
        if (count >= 3) return false;
        redisTemplate.opsForValue().set("checkCode_count_" + mobile, count + 1, 5, TimeUnit.HOURS);
        return true;
    }

    @Override
    public boolean validateCheckCode(String key, String checkCode) {
        String checkCodeRedis = (String) redisTemplate.opsForValue().get("checkCode_" + key);
        return checkCodeRedis != null && !checkCodeRedis.equals("") && checkCodeRedis.equals(checkCode);
    }
}
