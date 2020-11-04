package com.cn.stardust.tool.request;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * @author stardust
 * @date 2020/11/4 14:40
 */
public class RequestValues extends ArrayList<RequestValue> {

    /**
     * 解析内容
     * @param content 待解析内容
     * @param arraySplit 多key-value 的分隔符
     * @param kvSplit key-value 之间的分隔符
     * @return
     */
    public static RequestValues parseValues(String content,String arraySplit,String kvSplit)throws Exception{
        String[] array = content.split(arraySplit);
        RequestValues values = new RequestValues();
        for(String value : array){
            values.add(RequestValue.parse(value,kvSplit));
        }
        return values;
    }

    public RequestValues addValue(RequestValue requestValue){
        this.add(requestValue);
        return this;
    }

    /**
     * 是否需要将Value 转码
     * @param urlEncode
     * @return
     */
    public String toRequestString(Boolean urlEncode)throws UnsupportedEncodingException {
        if(this.size()==0){
            return new String();
        }
        StringBuffer buffer = new StringBuffer();
        for(RequestValue requestValue : this){
            buffer.append(requestValue.toRequestString(urlEncode));
            buffer.append("&");
        }
        return buffer.toString();
    }
}
