package com.cn.stardust.star.codegen.typeconvert;


import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

/**
 * https://github.com/oraclexing
 * <p>
 * 数据库字段类型映射java类型
 *
 * @author stardust
 * @version 1.0.0
 */
final public class DataTypeConvert extends Convert{

    /**
     * 初始化转换器必须要对父类属性实例化
     */
    {
        dataTypeMap = new HashMap<>();
        dataTypeMap.put("bigint",Long.class);
        dataTypeMap.put("char",String.class);
        dataTypeMap.put("varchar",String.class);
        dataTypeMap.put("bit",Boolean.class);
        dataTypeMap.put("date", Date.class);
        dataTypeMap.put("int", Integer.class);
        dataTypeMap.put("tinyint", Integer.class);
        dataTypeMap.put("integer", Integer.class);
        dataTypeMap.put("datetime", Date.class);
        dataTypeMap.put("decimal", BigDecimal.class);
        dataTypeMap.put("double", Double.class);
        dataTypeMap.put("float", Float.class);
        dataTypeMap.put("longtext", String.class);
        dataTypeMap.put("text", String.class);
        dataTypeMap.put("time", Date.class);
    }

}