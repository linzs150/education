package com.one.education.network;

/**
 *
 *
 */
public class RestError {

    public String code;
    public String msg;
    public RestError(String code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
