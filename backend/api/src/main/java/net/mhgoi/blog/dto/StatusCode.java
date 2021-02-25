package net.mhgoi.blog.dto;

/**
 * @author 少年
 */
public class StatusCode {
    public static final int OK = 20000; //成功
    public static final int ERROR = 20001; //失败
    public static final int LOGIN_ERROR = 20002; //用户名或密码错误
    public static final int ACCESSERROR = 20003; //权限不足
    public static final int REMOT_EERROR = 20004; //远程调用失败
    public static final int REP_ERROR = 20005; //重复操作
    public static final int LOGOUT = 20006; //退出登录
    public static final int TOKEN_EXPIRED = 20007; //令牌过期
    public static final int REP_KEY = 20008; //重复索引
    public static final int USER_FAILURE = 20009; //用户失效
    public static final int REP_MOBILE = 20010; //手机号重复
    public static final int CHECK_CODE_ERROR = 20011; //验证码错误
    public static final int BAD_TOKEN = 20012; //错误令牌
    public static final int NO_USER = 20013; //用户已删除
    public static final int TOO_MANY_REQUESTS = 20014; //请求次数过多
}
