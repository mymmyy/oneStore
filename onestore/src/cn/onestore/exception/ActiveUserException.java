package cn.onestore.exception;

/**
 * Created by 明柯 on 2017/4/13.
 * 激活用户异常
 */
public class ActiveUserException extends Exception{
    public ActiveUserException() {
        super();
    }

    public ActiveUserException(String message) {
        super(message);
    }

    public ActiveUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActiveUserException(Throwable cause) {
        super(cause);
    }
}
