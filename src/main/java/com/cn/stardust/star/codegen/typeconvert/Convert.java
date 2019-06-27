package com.cn.stardust.star.codegen.typeconvert;

import java.util.Map;

/**
 * https://github.com/oraclexing
 * <p>
 *
 * @author stardust
 * @version 1.0.0
 */
public abstract class Convert {

    /**
     * 类型转换器,在转换中需要
     * 可自定义实现转换类，并继承该类，对该dataTypeMap必须初始化
     */
    protected Map<String,Class<?>> dataTypeMap;

    /**
     * 根据数据库字段类型获取java数据库类型
     * @param key
     * @return
     */
    final public Class<?> getType(String key){
        return dataTypeMap.get(key);
    }
}