package cn.onestore.exception;

/**
 * Created by 明柯 on 2017/4/19.
 * 创建店铺异常
 */
public class CreateShopException extends Exception{
    public CreateShopException() {
        super();
    }

    public CreateShopException(String message) {
        super(message);
    }

    public CreateShopException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreateShopException(Throwable cause) {
        super(cause);
    }
}
