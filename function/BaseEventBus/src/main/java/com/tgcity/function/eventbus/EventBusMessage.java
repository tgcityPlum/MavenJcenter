package com.tgcity.function.eventbus;

/**
 * @author TGCity
 * @date 2019/10/9
 * @description EventBusMessage
 */
public class EventBusMessage<T> {
    private T data;
    private String message;

    public EventBusMessage(String message) {
        this.message = message;
    }

    public EventBusMessage(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

}
