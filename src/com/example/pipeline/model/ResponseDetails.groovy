package com.example.pipeline.model

class ResponseDetails<T> {
    boolean status
    String message
    String stashName
    T data


    @Override
    String toString() {
        return "ResponseDetails{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", stashName=" + stashName +
                ", data=" + data +
                '}'
    }
}