package com.xuxiang.common.net.callback;

/**
 * @author
 * @describe
 */
public class CommonResult<T> {

    private int status;
    private T data;
    private String message;

    public CommonResult(T data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
