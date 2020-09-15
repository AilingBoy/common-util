package com.cn.stardust.star.codegen.sql;

import com.cn.stardust.star.codegen.metadata.ClassMetaData;
import com.cn.stardust.star.codegen.typeconvert.Convert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

/**
 * https://github.com/KnowNoUnknown
 * <p>
 * SQL 查询接口
 *
 * @author stardust
 * @version 1.0.0
 */
public abstract class Query {


    /**
     * 待生成的表集合
     */
    protected List<String> tables;

    protected Convert convert;

    /**
     * 建立连接
     * @param DB_URL 数据库url
     * @param USERNAME 连接用户名
     * @param PASSWORD 连接密码
     * @return
     * @throws Exception
     */
    protected Connection connect(String DB_URL,String USERNAME,String PASSWORD)throws Exception{
        return DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
    }

    /**
     * 查询表，返回元数据集合
     * @return
     */
    public abstract List<ClassMetaData> query();

    /**
     * 关闭连接
     * @param con
     */
    protected void close(Connection con){
        try {
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setTables(List<String> tables) {
        this.tables = tables;
    }
}