package cn.onestore.exception;

/**
 * Created by 明柯 on 2017/4/27.
 * 添加购物车时的异常
 */
public class AddShopcartException extends Exception{
    public AddShopcartException() {
        super();
    }

    public AddShopcartException(String message) {
        super(message);
    }

    public AddShopcartException(String message, Throwable cause) {
        super(message, cause);
    }

    public AddShopcartException(Throwable cause) {
        super(cause);
    }
}
