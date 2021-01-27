package com.cn.stardust.tool.request;

/**
 * @author stardust
 * @date 2020/11/4 16:39
 */
public class RequestTest {


    public static void main(String[] args) throws Exception{
        HttpRequest request = new HttpRequest("http://baidu.com");
        request.addRequestParams(new RequestValue("username","username"));
        request.execute(RequestMethodEnum.GET);
        System.out.println(request.getResponseContent());
    }
}
