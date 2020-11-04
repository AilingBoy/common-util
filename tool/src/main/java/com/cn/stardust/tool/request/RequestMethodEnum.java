package com.cn.stardust.tool.request;

/**
 * @author stardust
 * @date 2020/11/4 17:39
 */
public enum RequestMethodEnum {
    GET("GET"),
    POST("POST"),
    DELETE("DELETE"),
    PUT("PUT"),;

    private String method;

    RequestMethodEnum(String method){
       this.method = method;
    }

    public String getMethod(){
        return this.method;
    }
}
