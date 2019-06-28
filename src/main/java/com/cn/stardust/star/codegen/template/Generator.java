package com.cn.stardust.star.codegen.template;

import com.cn.stardust.star.codegen.ClassMetaData;
import com.cn.stardust.star.codegen.CodeGenerate;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/oraclexing
 * <p>
 *
 * @author stardust
 * @version 1.0.0
 */
public interface Generator {

    List<Generator> generators = new ArrayList<>();

    List<ClassMetaData> metaDatas = new ArrayList<>();

    void setClassMetaData(ClassMetaData classMetaData);

    /**
     * 获取包信息
     * @return
     */
    default String getPackageInfo(){
        Class clazz = CodeGenerate.getClassInfo();
        return "package "+clazz.getPackage().getName()
                + Character.SEMICOLON
                + Character.getLineFeed(2);
    }

    /**
     * 签名信息
     * @return
     */
    default String getSignInfo(){
        return "/**" + Character.LINE_FEED+
                " * https://github.com/oraclexing" + Character.LINE_FEED +
                " * <p>" + Character.LINE_FEED +
                " *  代码生成器自动创建" + Character.LINE_FEED +
                " *" + Character.LINE_FEED +
                " * @author stardust" +Character.LINE_FEED +
                " * @version 1.0.0" + Character.LINE_FEED +
                " *" + Character.LINE_FEED +
                " */" + Character.LINE_FEED;
    }

    /**
     * 获取导入信息
     * @return
     */
    String getImportInfo();

    /**
     * 获取public class info
     * @return
     */
    String getClassInfo();

    /**
     * 获取属性信息
     * @return
     */
    String getFieldsInfo();

    /**
     * 获取方法信息
     * @return
     */
    String getMethodsInfo();

    /**
     * 获取文件名，带后缀
     * @return
     */
    String getFileName();

    /**
     * 入口
     * @return
     */
    default void generate(){
        metaDatas.forEach(e ->
                generators.forEach(f ->{
                f.setClassMetaData(e);
                genWriteToFile(f);
            })
        );
    }

    /**
     * 按照模板生成文件
     * @param generator
     */
    default void genWriteToFile(Generator generator){
        StringBuffer buffer = new StringBuffer();
        buffer.append(generator.getPackageInfo());
        buffer.append(generator.getImportInfo());
        buffer.append(generator.getSignInfo());
        buffer.append(generator.getClassInfo());
        buffer.append(generator.getFieldsInfo());
        buffer.append(generator.getMethodsInfo());
        buffer.append(Character.CLOSE_BRACE);
        try {
            FileOutputStream outputStream = new FileOutputStream(new File(generator.getFileName()));
            outputStream.write(buffer.toString().getBytes());
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}