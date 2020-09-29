package com.cn.stardust.tool.codegen.typeconvert;

import java.util.HashMap;
import java.util.Map;

/**
 * https://github.com/KnowNoUnknown
 * <p>
 * 类型转换器
 *
 * @author stardust
 * @version 1.0.0
 */
public interface Convert {

    /**
     * 类型转换器,在转换中需要
     * 可自定义实现转换类，并继承该类，对该dataTypeMap必须初始化
     */
    Map<String,Class<?>> dataTypeMap = new HashMap<>();


    /**
     * 初始化dataTypeMap
     */
    void initialize();

    /**
     * 根据数据库字段类型获取java数据库类型
     * @param key
     * @return
     */
    default Class<?> getType(String key){
        // 若map为空，则进行初始化
        if(dataTypeMap.isEmpty()){
            initialize();
        }
        return dataTypeMap.get(key);
    }
}