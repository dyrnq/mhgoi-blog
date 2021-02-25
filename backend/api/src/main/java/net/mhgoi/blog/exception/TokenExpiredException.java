package net.mhgoi.blog.exception;

/**
 * @author 少年
 */
public class TokenExpiredException extends Exception {
    private static final long serialVersionUID = -1435635459744147463L;

    public TokenExpiredException(String message) {
        super(message);
    }
}
