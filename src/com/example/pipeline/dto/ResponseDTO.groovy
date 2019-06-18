package com.example.pipeline.dto

class ResponseDTO<T> {
    boolean status
    String message
    T data


    @Override
    String toString() {
        return "ResponseDTO{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}'
    }
}