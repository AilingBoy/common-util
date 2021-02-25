package com.cn.stardust.tool.request;

/**
 * @author stardust
 * @date 2020/11/4 16:39
 */
public class RequestTest implements IRequest{


    @Override
    public void request() throws Exception{
        HttpRequest request = new HttpRequest("http://baidu.com");
        request.addRequestParams(new RequestValue("username","username"));
        request.execute(RequestMethodEnum.GET);
        System.out.println(request.getResponseContent());
    }

    @Override
    public void print() throws Exception {
        System.out.println("Print method Called!");
    }

    @Override
    public void printContent(String content) throws Exception {
        System.out.println(content);
    }

    @Override
    public Integer multiply(Integer num) throws Exception {
        System.out.println("result="+num*num);
        return num;
    }
}
