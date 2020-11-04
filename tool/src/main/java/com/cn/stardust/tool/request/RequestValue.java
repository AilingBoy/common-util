package com.cn.stardust.tool.request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.NoSuchElementException;

/**
 * @author stardust
 * @date 2020/11/4 14:40
 */
public class RequestValue {

    private String key;

    private String value;

    public RequestValue(String key,String value) {
        this.key = key;
        this.value = value;
    }

    public RequestValue() {}

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 解析字符串为 {@link RequestValue} 对象
     * @param content 内容
     * @param splitSeq 字符串的分割标识
     * @return
     */
    public static RequestValue parse(String content,String splitSeq)throws Exception{
        if(null == content || content.trim().length()==0){
            return null;
        }
        if(null == splitSeq || splitSeq.trim().length()==0){
            return new RequestValue(content,new String());
        }
        int index = content.indexOf(splitSeq);
        if(index == -1){
            throw new NoSuchElementException(splitSeq);
        }
        if(index == 0){
            throw new Exception("RequestValue Key should not be Null");
        }
        String[] values = content.split(splitSeq);
        return new RequestValue(values[0],values[1]);
    }

    /**
     * 将值转换为请求参数字符串
     * @param needUrlEncode
     * @return
     */
    public String toRequestString(Boolean urlEncode)throws UnsupportedEncodingException {
        StringBuffer buffer = new StringBuffer();
        buffer.append(this.key);
        buffer.append("=");
        if(urlEncode){
            String encodeStr = URLEncoder.encode(this.value,"utf-8");
            buffer.append(encodeStr);
        }else{
            buffer.append(this.value);
        }
      return buffer.toString();
    }
}