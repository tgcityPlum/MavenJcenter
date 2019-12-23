package com.tgcity.function.network.bean;

/**
 * @author TGCity
 * 服务器返回的基础类，此处进行拆分
 * @param <T> 核心的类，支持List
 */
public class HttpResult<T> {

    private T data;
    private int status;
    private String msg;

    public T getResults() {
        return data;
    }

    public int getCode() {
        return status;
    }

    public void setCode(int status) {
        this.status = status;
    }

    public String getMessage() {
        return msg;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "data=" + data +
                ", status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }
}
