package cn.onestore.exception;

/**
 * Created by 明柯 on 2017/4/13.
 * 登陆异常
 */
public class LoginException extends Exception{
    public LoginException() {
        super();
    }

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginException(Throwable cause) {
        super(cause);
    }
}
