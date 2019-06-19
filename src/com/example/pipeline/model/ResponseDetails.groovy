package com.example.pipeline.model

class ResponseDetails<T> {
    boolean status
    String message
    T data


    @Override
    String toString() {
        return "ResponseDetails{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}'
    }
}