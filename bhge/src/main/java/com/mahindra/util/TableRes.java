package com.mahindra.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 表格返回结果对象
 * @param <T>
 */
@ApiModel(value = "表格返回结果对象")
public class TableRes<T> {
    @ApiModelProperty(value = "返回状态")
    private int code;
    @ApiModelProperty(value = "返回消息")
    private String msg;
    @ApiModelProperty(value = "表格使用")
    private int count;
    @ApiModelProperty(value = "返回数据")
    private T data;

    public TableRes() {
    }

    public TableRes(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
