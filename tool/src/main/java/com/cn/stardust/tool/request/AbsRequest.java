package com.cn.stardust.tool.request;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author stardust
 * @date 2020/11/4 17:33
 */
public abstract class AbsRequest {
    /**
     * 请求地址
     */
    protected String requestUrl;
    /**
     * 请求头
     */
    protected RequestValues requestHeaders;
    /**
     * 响应头
     */
    protected RequestValues responseHeaders = new RequestValues();

    protected HttpURLConnection connection;
    /**
     * 响应状态码
     */
    protected Integer responseCode;

    /**
     * 开始请求
     * @param method
     */
    public void execute(RequestMethodEnum method)throws Exception{
        preRequest();
        switch (method) {
            case GET:
                doGet();
                break;
            default:
                doPost();
                break;
        }
        //填充响应头
        fillResponseHeader(connection);
        responseCode = connection.getResponseCode();
        postRequest();
    }

    /**
     * 请求前置行为
     */
    private void preRequest() {
        // Do something before request
    }

    /**
     * 请求后置行为
     */
    private void postRequest() {
        // Do something after request
    }

    protected abstract void doGet()throws Exception;

    protected abstract void doPost()throws Exception;
    /**
     * 获取请求连接
     * @param requestUrl
     * @param method
     * @return
     * @throws Exception
     */
    protected HttpURLConnection getURLConnection(String requestUrl,String method)throws Exception{
        URL url =new URL(requestUrl);
        HttpURLConnection connection=(HttpURLConnection) url.openConnection();
        connection.addRequestProperty("encoding", "UTF-8");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod(method);
        /**
         * 填充请求头
         */
        if(null != requestHeaders && requestHeaders.size() > 0){
            requestHeaders.forEach(e ->connection.addRequestProperty(e.getKey(),e.getValue()));
        }
        return connection;
    }

    private void fillResponseHeader(HttpURLConnection connection){
        Map<String, List<String>> mapHeaders = connection.getHeaderFields();
        Iterator<String> it = mapHeaders.keySet().iterator();
        while (it.hasNext()){
            String key = it.next();
            String value = mapHeaders.get(key).stream().reduce((a,b)-> a+","+b ).get();
            responseHeaders.addValue(new RequestValue(key,value));
        }
    }
}
