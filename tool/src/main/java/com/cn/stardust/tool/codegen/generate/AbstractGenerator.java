package com.cn.stardust.tool.codegen.generate;


import com.cn.stardust.tool.codegen.metadata.ClassMetaData;

/**
 * https://github.com/KnowNoUnknown
 * <p>
 * 生成器中间的抽象类
 *
 * @author stardust
 * @version 1.0.0
 */
public abstract class AbstractGenerator implements Generator{

    /**
     * 初始化元数据
     */
    protected ClassMetaData classMetaData;

    /**
     * 输出文件位置
     * classMetaData
     */
    protected static String outputPath;

    @Override
    final public void setClassMetaData(ClassMetaData classMetaData) {
        this.classMetaData = classMetaData;
    }

    /**
     * 获取导入信息
     * @return
     */
    @Override
    public String getImportInfo(){
        return null;
    }

    /**
     * 获取class信息
     * @return
     */
    @Override
    public String getClassInfo(){
        return null;
    }

    /**
     * 获取class所有属性信息
     * @return
     */
    @Override
    public String getFieldsInfo(){
        return null;
    }

    /**
     * 获取方法信息
     * @return
     */
    @Override
    public String getMethodsInfo(){
        return null;
    }

    @Override
    public String getFileName() {
        return null;
    }
}