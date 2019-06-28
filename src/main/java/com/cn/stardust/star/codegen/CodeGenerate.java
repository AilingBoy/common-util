package com.cn.stardust.star.codegen;

import com.cn.stardust.star.codegen.sql.MysqlQuery;
import com.cn.stardust.star.codegen.sql.Query;
import com.cn.stardust.star.codegen.template.GeneratorBoot;
import com.cn.stardust.star.codegen.typeconvert.DataTypeConvert;
import com.google.common.collect.Lists;

import java.util.List;


/**
 * https://github.com/oraclexing
 * <p>
 *  代码生成器
 *
 *
 * @author stardust
 * @version 1.0.0
 */
public class CodeGenerate {

    private static ThreadLocal<Class> classThreadLocal = new ThreadLocal<>();

    public static void main(String args[])throws Exception{
        classThreadLocal.set(CodeGenerate.class);
        Query query = new MysqlQuery("127.0.0.1",
                "0","0","0",new DataTypeConvert());
        query.setTables(Lists.newArrayList("table1","table2"));
        List<ClassMetaData> list = query.query();
        GeneratorBoot generatorBoot = GeneratorBoot.getInstance(list,"E:\\");
        generatorBoot.generate();
        System.out.println("finished!");
    }

    public static Class getClassInfo(){
        return classThreadLocal.get();
    }
}