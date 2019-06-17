package com.example.pipeline.dto


import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true)
class ResponseDTO<T> {
    boolean status
    String message
    T data
}