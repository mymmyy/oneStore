package cn.onestore.exception;

/**
 * Created by 明柯 on 2017/4/21.
 * 更新异常
 */
public class UpdateException extends Exception{
    public UpdateException() {
        super();
    }

    public UpdateException(String message) {
        super(message);
    }

    public UpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateException(Throwable cause) {
        super(cause);
    }
}
