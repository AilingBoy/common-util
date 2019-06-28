package com.cn.stardust.star.codegen;

import java.util.List;

/**
 * https://github.com/oraclexing
 * <p>
 *
 * @author stardust
 * @version 1.0.0
 */
final public class ClassMetaData {

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 类名称
     */
    private String  className;

    /**
     * 字段信息
     */
    private List<FieldMetaData> fieldMetaDatas;

    public ClassMetaData() {
    }

    public ClassMetaData(String tableName, String className) {
        this.tableName = tableName;
        this.className = className;
    }

    public ClassMetaData(String tableName, String className, List<FieldMetaData> fieldMetaDatas) {
        this.tableName = tableName;
        this.className = className;
        this.fieldMetaDatas = fieldMetaDatas;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<FieldMetaData> getFieldMetaDatas() {
        return fieldMetaDatas;
    }

    public void setFieldMetaDatas(List<FieldMetaData> fieldMetaDatas) {
        this.fieldMetaDatas = fieldMetaDatas;
    }
}
