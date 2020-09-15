package com.cn.stardust.star.scanner;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * https://github.com/KnowNoUnknown
 * <p>
 *
 * @author stardust
 * @version 1.0.0
 */
public class Package {

    /**
     * 父包
     */
    private Package parentPackage;

    /**
     * 子包集合
     */
    private List<Package> subPackages = Lists.newArrayList();

    /**
     * 包的名称
     */
    private String packageName;

    /**
     * 当前包下面的class集合
     */
    private List<Clazz> clazzes = Lists.newArrayList();

    private List<Class> allClasses = Lists.newArrayList();

    public Package(String packageName) {
        this.packageName = packageName;
    }

    public Package(Package parentPackage, String packageName) {
        this.parentPackage = parentPackage;
        this.packageName = packageName;
    }

    public Package() {}

    public Package getParentPackage() {
        return parentPackage;
    }

    public void setParentPackage(Package parentPackage) {
        this.parentPackage = parentPackage;
    }

    public List<Package> getSubPackages() {
        return subPackages;
    }

    public void setSubPackages(List<Package> subPackages) {
        this.subPackages = subPackages;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<Clazz> getClazzes() {
        return clazzes;
    }

    public void setClazzes(List<Clazz> clazzes) {
        this.clazzes = clazzes;
    }
}