package com.mahindra.util;

/**
 *  response 返回编码
 * Created by weican on 2017/7/20.
 */
public enum ResultHttpStatus {
    OK(200, "ok"),

    NO_LOGIN(201, "no login"),//无法判断，废弃

    LOGIN_TIMEOUT(202, "login timeout or no login"),

    LOGIN_DENIED(203, "user login denied"),

    INTERNAL_ERROR(505, "internal error"),

    ALREADY_EXISTS_ERROR(506, "already exists"),

    SENSITIVE_WORD_EXCEPTION(507, "sensitive word exception"),
    
    SSOID_EXCEPTION(508,"ssoid exception");
	

    private int value;
    private String name;

    ResultHttpStatus(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
