package com.cn.stardust.star.codegen;

/**
 * https://github.com/oraclexing
 * <p>
 *  元数据
 * @author stardust
 * @version 1.0.0
 */
final public class Metadata {

    /**
     * 字段名称
     */
    private String name;

    /**
     * 字段备注
     */
    private String desc;

    /**
     * 字段类型
     */
    private Class<?> clazz;

    public Metadata() {
    }

    public Metadata(String name, String desc, Class<?> clazz) {
        this.name = name;
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