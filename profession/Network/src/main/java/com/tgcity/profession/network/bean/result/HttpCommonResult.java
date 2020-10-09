package com.tgcity.profession.network.bean.result;

/**
 * @author TGCity
 */

public class HttpCommonResult<T> {

    private int code;
    private String message;
    private String msg;
    private T data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return data;
    }

    public void setResult(T result) {
        this.data = result;
    }

    @Override
    public String toString() {
        return "HttpCommonResult{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", msg='" + msg + '\'' +
                ", result=" + data +
                '}';
    }
}
