package cn.onestore.exception;

/**
 * Created by 明柯 on 2017/4/20.
 * 上传文件异常，主要是IO异常
 */
public class UploadException extends Exception{
    public UploadException(Throwable cause) {
        super(cause);
    }

    public UploadException() {
        super();
    }

    public UploadException(String message) {
        super(message);
    }

    public UploadException(String message, Throwable cause) {
        super(message, cause);
    }
}
