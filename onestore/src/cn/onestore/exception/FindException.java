package cn.onestore.exception;

/**
 * Created by 明柯 on 2017/4/17.
 * 专门用于上传查找时的异常
 */
public class FindException extends Exception{
    public FindException() {
        super();
    }

    public FindException(String message) {
        super(message);
    }

    public FindException(String message, Throwable cause) {
        super(message, cause);
    }

    public FindException(Throwable cause) {
        super(cause);
    }
}
