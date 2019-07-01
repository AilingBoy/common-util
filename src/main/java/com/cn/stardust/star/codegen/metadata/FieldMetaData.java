package com.cn.stardust.star.codegen.metadata;

/**
 * https://github.com/oraclexing
 * <p>
 *  Field 的元数据
 *
 * @author stardust
 * @version 1.0.0
 */
final public class FieldMetaData {

    /**
     * 字段名称
     */
    private String name;

    /**
     * 属性名称
     */
    private String FieldName;

    /**
     * 字段备注
     */
    private String desc;

    /**
     * 字段类型
     */
    private Class<?> clazz;

    public FieldMetaData() {
    }

    public FieldMetaData(String name, String desc, Class<?> clazz) {
        this.name = name;
        this.desc = desc;
        this.clazz = clazz;
    }

    public FieldMetaData(String name, String fieldName, String desc, Class<?> clazz) {
        this.name = name;
        FieldName = fieldName;
        this.desc = desc;
        this.clazz = clazz;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String getFieldName() {
        return FieldName;
    }

    public void setFieldName(String fieldName) {
        FieldName = fieldName;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}