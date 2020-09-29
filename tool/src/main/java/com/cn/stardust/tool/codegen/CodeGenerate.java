package com.cn.stardust.tool.codegen;

import com.cn.stardust.tool.codegen.generate.GeneratorBoot;
import com.cn.stardust.tool.codegen.metadata.ClassMetaData;
import com.cn.stardust.tool.codegen.sql.MysqlQuery;
import com.cn.stardust.tool.codegen.sql.OracleQuery;
import com.cn.stardust.tool.codegen.sql.Query;
import com.cn.stardust.tool.codegen.typeconvert.Convert;
import com.cn.stardust.tool.codegen.typeconvert.DataTypeConvert;

import java.util.List;


/**
 * https://github.com/KnowNoUnknown
 * <p>
 *  代码生成器
 *
 *
 * @author stardust
 * @version 1.0.0
 */
public class CodeGenerate {

    private static Class clazz ;

    /**
     * 查询器
     */
    private static Query query ;

    private static CodeGenerate codeGenerate;

    static {
        codeGenerate = new CodeGenerate();
    }

    private CodeGenerate(){}

    /**
     * 构造生成器
     * @param jdcbUrl 数据库地址
     * @param dbName 数据库名称
     * @param dbUserName 数据库用户名
     * @param dbPassword 数据库密码
     * @param convert sql 到java 的类型转换器 ， 可自定义Convert实现类，传入即可
     * @return
     */
    public static CodeGenerate getMysqlInstance(String jdcbUrl , String dbName , String dbUserName , String dbPassword , Convert convert){
        query  = new MysqlQuery(jdcbUrl,dbName,dbUserName,dbPassword,convert);
        if( null == codeGenerate ){
            codeGenerate = new CodeGenerate();
        }
        return codeGenerate;
    }

    /**
     * 构造生成器
     * @param dbIp 数据库地址
     * @param dbName 数据库名称
     * @param dbUserName 数据库用户名
     * @param dbPassword 数据库密码
     * @param convert sql 到java 的类型转换器 ， 可自定义Convert实现类，传入即可
     * @return
     */
    public static CodeGenerate getOracleInstance(String dbIp , String dbName , String dbUserName , String dbPassword , Convert convert){
        query  = new OracleQuery(dbIp,dbName,dbUserName,dbPassword,convert);
        if( null == codeGenerate ){
            codeGenerate = new CodeGenerate();
        }
        return codeGenerate;
    }

    /**
     * 构造生成器
     * @param jdbcUrl 数据库地址
     * @param dbUserName 数据库用户名
     * @param dbPassword 数据库密码
     * @return
     */
    public static CodeGenerate getMysqlInstance(String jdbcUrl ,String dbName , String dbUserName , String dbPassword){
        query  = new MysqlQuery(jdbcUrl,dbName,dbUserName,dbPassword,new DataTypeConvert());
        if( null == codeGenerate ){
            codeGenerate = new CodeGenerate();
        }
        return codeGenerate;
    }

    /**
     * 构造生成器
     * @param dbIp 数据库地址
     * @param dbName 数据库名称
     * @param dbUserName 数据库用户名
     * @param dbPassword 数据库密码
     * @return
     */
    public static CodeGenerate getOracleInstance(String dbIp , String dbName , String dbUserName , String dbPassword){
        query  = new OracleQuery(dbIp,dbName,dbUserName,dbPassword,new DataTypeConvert());
        if( null == codeGenerate ){
            codeGenerate = new CodeGenerate();
        }
        return codeGenerate;
    }

    /**
     * 开始生成
     * 注意：生成后的所有类都会和该class 处于同一个包下面，因此请尽可能将使用的clazz所在包直接复制path作为 outputPath进行传递
     * @param tables 待自动生成的表名称集合
     * @param outputPath 输出文件位置
     * @param clazz 外部调用的启动类class
     */
    public void generate(List<String> tables , String outputPath , Class clazz){
        CodeGenerate.clazz = clazz;
        query.setTables(tables);
        List<ClassMetaData> metaDataList = query.query();
        if(metaDataList.size() > 0){
            GeneratorBoot generatorBoot = GeneratorBoot.getInstance(query.query(),outputPath);
            generatorBoot.generate();
        }
    }

    public static Class getClassInfo(){
        return clazz;
    }
}