package com.cn.stardust.tool.request;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * @author stardust
 * @date 2020/11/4 13:41
 *
 * 封装Http请求
 *
 */
public class HttpRequest extends AbsRequest{

    /**
     * 请求参数
     */
    private RequestValues requestParams;
    /**
     * 请求体
     */
    private String requestBody;
    /**
     * 响应内容
     */
    private String responseContent;

    /**
     * 初始化一个请求
     * @param requestUrl
     */
    public HttpRequest(String requestUrl){
        super.requestUrl = requestUrl;
    }

    /**
     * 发送get请求
     * GET 请求是直接无视请求体，即RequestBody 所有的参数都是再URL后面以键值对的形式进行拼接
     *
     * @return
     */
    @Override
    protected void doGet()throws Exception {
        if(null != this.requestParams && this.requestParams.size() > 0){
            // 附带GET 请求参数
            this.requestUrl += "?"+this.requestParams.toRequestString(Boolean.TRUE);
        }
        connection = getURLConnection(this.requestUrl,"GET");
        byte[] bytes = new byte[1024];
        InputStream inputStream = connection.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int index = inputStream.read(bytes);
        while ( index > 0){
            outputStream.write(bytes,0,index);
            index = inputStream.read(bytes);
        }
        this.responseContent = outputStream.toString();
    }

    /**
     * 发送POST 请求
     * 注意：POST 请求中，当 {@link this.requestBody} 和 {@link this.requestParams} 都有值的时候。
     *   仅仅只有  {@link this.requestBody} 的值会被写入请求体中，
     *   而  {@link this.requestParams} 会以键值对追加到url中。
     *   当只有一个有值的时候，那么它直接被写入请求体中。
     *
     * @return
     * @throws Exception
     */
    @Override
    protected void doPost()throws Exception {
        connection = getURLConnection(this.requestUrl,"POST");
        if(null != requestBody && null != this.requestParams && this.requestParams.size() > 0){
            // URL附带请求参数
            this.requestUrl += "?"+this.requestParams.toRequestString(Boolean.TRUE);
        }else if(null != requestBody){
            // 请求体中写入参数
            connection.getOutputStream().write(requestBody.getBytes("utf-8"));
        }else{
            connection.getOutputStream().write(requestParams.toRequestString(Boolean.TRUE).getBytes("utf-8"));
        }
        byte[] bytes = new byte[1024];
        InputStream inputStream = connection.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int index = inputStream.read(bytes);
        while ( index > 0){
            outputStream.write(bytes,0,index);
            index = inputStream.read(bytes);
        }
        this.responseContent = outputStream.toString();
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public RequestValues getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(RequestValues requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public void addRequestHeaders(RequestValue requestHeader) {
        if(null == this.requestHeaders){
            this.requestHeaders = new RequestValues();
        }
        this.requestHeaders.addValue(requestHeader);
    }

    public RequestValues getResponseHeaders() {
        return responseHeaders;
    }


    public RequestValues getRequestParams() {
        return requestParams;
    }

    public void addRequestParams(RequestValue requestParam) {
        if(null == this.requestParams){
            this.requestParams = new RequestValues();
        }
        this.requestParams.addValue(requestParam);
    }

    public void setRequestParams(RequestValues requestParams) {
        this.requestParams = requestParams;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getResponseContent() {
        return responseContent;
    }
}
