package cn.onestore.exception;

/**
 * Created by 明柯 on 2017/4/12.
 * 注册时出现的异常，定义异常类主要是分类异常
 * 覆写Exception的几个构造方法即可,即在自己的构造方法中调用父类的构造方法
 */
public class RegisterException extends Exception{
    public RegisterException() {
        super();
    }

    public RegisterException(String message) {
        super(message);
    }

    public RegisterException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegisterException(Throwable cause) {
        super(cause);
    }

    /*protected RegisterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }*/
}
