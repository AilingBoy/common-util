package com.cn.stardust.tool.request;

/**
 * <p>
 *
 * @author stardust
 * @version 1.0.0
 * 2021/2/8 14:36
 */
public interface IRequest {

    void request() throws Exception;

    void print() throws Exception;

    void printContent(String content) throws Exception;

    Integer multiply(Integer num) throws Exception;
}
