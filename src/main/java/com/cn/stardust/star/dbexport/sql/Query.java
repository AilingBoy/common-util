package com.cn.stardust.star.dbexport.sql;

import com.cn.stardust.star.dbexport.metadata.DatabaseMetadata;
import com.cn.stardust.star.dbexport.metadata.TableMetadata;

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
public interface Query {

    /**
     * 建立连接
     * @param DB_URL 数据库url
     * @param USERNAME 连接用户名
     * @param PASSWORD 连接密码
     * @return
     * @throws Exception
     */
    default Connection connect(String DB_URL, String USERNAME, String PASSWORD)throws Exception{
        return DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
    }

    /**
     * 查询表，返回元数据集合
     * @return
     */
    DatabaseMetadata query();

    /**
     * 关闭连接
     * @param con
     */
    default void close(Connection con){
        try {
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
