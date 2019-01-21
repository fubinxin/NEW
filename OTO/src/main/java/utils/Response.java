package utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Andrew Qian
 * @class description :
 * @time 2017年7月17日 下午4:32:36
 */
//@ApiModel(value = "返回结果对象")
public class Response<T> {
    @ApiModelProperty(value = "返回状态")
    private int status;
    @ApiModelProperty(value = "返回消息")
    private String msg;
    @ApiModelProperty(value = "返回数据")
    private T data;

    public Response() {
    }

    public Response(int status) {
        this.status = status;
    }

    public Response(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Response(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    @JsonIgnore
    //使之不在json序列化当中
    public boolean isSuccess() {
        return this.status == ResultHttpStatus.OK.getValue();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
