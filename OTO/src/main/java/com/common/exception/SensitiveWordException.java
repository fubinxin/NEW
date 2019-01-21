package com.common.exception;

/**
 * Created by weican on 2017/10/11.
 */
public class SensitiveWordException extends RuntimeException {

    private static final long serialVersionUID = 1099436364231429639L;

    /**
     * 错误代码
     */
    private String code = "SENSITIVEWORDEXCEPTION";

    /**
     * 错误描述
     */
    private String msg;

    public SensitiveWordException() {
        super();
    }

    public SensitiveWordException(String message, Throwable cause) {
        super(message, cause);
    }

    public SensitiveWordException(String message) {
        super(message);
    }

    public SensitiveWordException(Throwable cause) {
        super(cause);
    }

    public SensitiveWordException(String code, String msg) {
        super(code + ":" + msg);
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    protected void setCode(String code) {
        this.code = code;
    }
}
