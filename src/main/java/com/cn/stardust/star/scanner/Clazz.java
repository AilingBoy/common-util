package com.cn.stardust.star.scanner;


/**
 * https://github.com/oraclexing
 * <p>
 *
 * @author stardust
 * @version 1.0.0
 */
public class Clazz {

    /**
     * 所属包
     */
    private Package pak;

    /**
     * class
     */
    private Class<?> clazz;

    public Clazz() {
    }

    public Clazz(Package pak, Class<?> clazz) {
        this.pak = pak;
        this.clazz = clazz;
    }

    public Package getPak() {
        return pak;
    }

    public void setPak(Package pak) {
        this.pak = pak;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}