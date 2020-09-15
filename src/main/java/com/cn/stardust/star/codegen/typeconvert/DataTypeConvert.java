package com.cn.stardust.star.codegen.typeconvert;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * https://github.com/KnowNoUnknown
 * <p>
 * 数据库字段类型映射java类型
 *
 * @author stardust
 * @version 1.0.0
 */
final public class DataTypeConvert implements Convert{

    /**
     * 转换器必须要对父类属性实例化
     */
    @Override
    public void initialize() {
        dataTypeMap.clear();
        // Mysql 字段转换
        dataTypeMap.put("bigint",Long.class);
        dataTypeMap.put("char",String.class);
        dataTypeMap.put("varchar",String.class);
        dataTypeMap.put("bit",Boolean.class);
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
        // Oracle 字段转换
        dataTypeMap.put("varchar2",String.class);
        dataTypeMap.put("number(1)",Boolean.class);
        dataTypeMap.put("number(2)",Byte.class);
        dataTypeMap.put("number",Integer.class);
        dataTypeMap.put("varchar(3)",Short.class);
        dataTypeMap.put("varchar(4)",Short.class);
        dataTypeMap.put("varchar(5)",Integer.class);
        dataTypeMap.put("varchar(6)",Integer.class);
        dataTypeMap.put("varchar(7)",Integer.class);
        dataTypeMap.put("varchar(8)",Integer.class);
        dataTypeMap.put("varchar(9)",Integer.class);
        dataTypeMap.put("varchar(10)",Long.class);
        dataTypeMap.put("varchar(11)",Long.class);
        dataTypeMap.put("varchar(12)",Long.class);
        dataTypeMap.put("varchar(13)",Long.class);
        dataTypeMap.put("varchar(14)",Long.class);
        dataTypeMap.put("varchar(15)",Long.class);
        dataTypeMap.put("varchar(16)",Long.class);
        dataTypeMap.put("varchar(17)",Long.class);
        dataTypeMap.put("varchar(18)",Long.class);
        dataTypeMap.put("timestamp(6)",LocalDateTime.class);
        dataTypeMap.put("timestamp(7)",LocalDateTime.class);
        dataTypeMap.put("timestamp(8)",LocalDateTime.class);
        dataTypeMap.put("timestamp(9)",LocalDateTime.class);
        dataTypeMap.put("timestamp(10)",LocalDateTime.class);
        dataTypeMap.put("date",LocalDateTime.class);
        dataTypeMap.put("timestamp",LocalDateTime.class);
    }
}