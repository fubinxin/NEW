package com.common.exception;

/**
 * Created by weican on 2017/11/13.
 */
public class AlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1099436364231429639L;

    /**
     * 错误代码
     */
    private String code = "ALREADYEXISTSEXCEPTION";

    /**
     * 错误描述
     */
    private String msg;

    public AlreadyExistsException() {
        super();
    }

    public AlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyExistsException(String message) {
        super(message);
    }

    public AlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public AlreadyExistsException(String code, String msg) {
        super(code + ":" + msg);
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
