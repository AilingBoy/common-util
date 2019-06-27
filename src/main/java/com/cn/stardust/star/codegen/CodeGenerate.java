package com.cn.stardust.star.codegen;

import com.cn.stardust.star.codegen.sql.MysqlQuery;
import com.cn.stardust.star.codegen.sql.Query;
import com.cn.stardust.star.codegen.typeconvert.DataTypeConvert;
import com.google.common.collect.Lists;

import java.util.Map;

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

    public static void main(String args[])throws Exception{
        Query query = new MysqlQuery("0.0.0.0",3306,
                "0000","0000","0000",new DataTypeConvert());
        query.setTables(Lists.newArrayList("user1","user2"));
        Map map = query.query();
        System.out.println("finished!");
    }
}