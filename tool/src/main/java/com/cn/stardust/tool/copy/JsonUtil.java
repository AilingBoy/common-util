package com.cn.stardust.tool.copy;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Iterator;

/**
 * https://github.com/KnowNoUnknown
 * <p>
 *  基于alibaba fastjson 的对象复制，
 *  根据target json字段获取目标字段
 *  如：
 *  jsonObject source = {a:1,b:2,c:3,d:4}
 *  jsonObject target = {a:null,b:null}
 *  复制后得到：result = {a:1,b:2}
 *
 *  jsonArray source = [{a:1,b:2,c:3,d:4},{a:1,b:2,c:3,d:4},{a:1,b:2,c:3,d:4},{a:1,b:2,c:3,d:4},{a:1,b:2,c:3,d:4}]
 *  jsonArray target = [{a:null,c:null}]
 *  复制后：result = [{a:1,c:3},{a:1,c:3},{a:1,c:3},{a:1,c:3},{a:1,c:3}]
 *
 *  注意:该工具支持复杂JsonObject 与 jsonArray 的互相嵌套格式数据的复制,请保证待复制模板的正确格式即可!
 *
 * @author stardust
 * @version 1.0.0
 *
 */
public class JsonUtil {

    /**
     * JsonArray 复制
     * @param source 源对象
     * @param target 目标对象格式，用于输出JsonArray的格式参考,不做修改
     * @return JSONArray 新的JSONArray对象
     */
    public static JSONArray copyArray(JSONArray source, JSONArray target){
        if(source == null || source.size() == 0){
            return null;
        }
        if(null == target || target.size() == 0){
            return source;
        }
        Object object = target.get(0);
        JSONArray output = new JSONArray();
        if(object instanceof JSONObject){
            for(int i = 0 ; i < source.size() ; i ++){
                output.add(copyObject(source.getJSONObject(i) , (JSONObject)object));
            }
        }else{
            // FIXME 暂时不考虑JSONArray包含JSONArray的情况
            output = source;
        }
        return output;
    }

    /**
     * JsonObject 复制
     * @param source 源对象
     * @param target 目标对象格式,注意:对目标对象格式不进行修改
     * @return JSONObject 是一个新的JsonObject
     */
    public static JSONObject copyObject(JSONObject source , JSONObject target) {
        if(null == source || source.keySet().size() == 0){
            return null;
        }
        if(null == target || target.keySet().size() == 0){
            return source;
        }
        Iterator<String> it = target.keySet().iterator();
        JSONObject outPut = new JSONObject();
        String key;
        while (it.hasNext()){
            key = it.next();
            Object object = source.get(key);
            if (null == object){
                continue;
            }
            if(object instanceof JSONObject){
                outPut.put(key,copyObject((JSONObject) object,target.getJSONObject(key)));
            }
            if(object instanceof JSONArray){
                outPut.put(key,copyArray((JSONArray) object,target.getJSONArray(key)));
            }else {
                outPut.put(key,object);
            }
        }
        return outPut;
    }
}