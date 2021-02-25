package net.mhgoi.blog.service;

/**
 * @author 少年
 */
public interface VerificationService {
    /**
     * 发送邮箱验证码
     *
     * @param email 邮箱
     */
    void sendEmailCode(String email);

    /**
     * 发送短信验证码
     *
     * @param mobile 手机号
     */
    void sendMobileCode(String mobile);

    /**
     * 验证短信发送次数
     *
     * @param mobile 手机号
     * @return 是否符合发送次数
     */
    boolean validateMobileCodeCount(String mobile);

    /**
     * 验证验证码是否正确
     *
     * @param key       手机或邮箱
     * @param checkCode 验证码
     * @return 验证码是否正确
     */
    boolean validateCheckCode(String key, String checkCode);
}
