package com.cn.stardust.star.dbexport.sql;

import com.beust.jcommander.internal.Lists;
import com.cn.stardust.star.codegen.CamelCaseConvert;
import com.cn.stardust.star.codegen.metadata.ClassMetaData;
import com.cn.stardust.star.codegen.metadata.FieldMetaData;
import com.cn.stardust.star.dbexport.metadata.ColumnMetadata;
import com.cn.stardust.star.dbexport.metadata.TableMetadata;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MysqlQuery implements Query {

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    /**
     * 数据库ip
     */
    private String dbIP;

    /**
     * 数据库端口号
     */
    private Integer dbPort = 3306;

    /**
     * 数据库名称
     */
    private String dbName;

    /**
     * 数据库连接用户名
     */
    private String dbUsername;

    /**
     * 数据库密码
     */
    private String dbPassword;

    public MysqlQuery(String dbIP , String dbName, String dbUsername, String dbPassword) {
        this.dbIP = dbIP;
        this.dbName = dbName;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
    }

    @Override
    public List<TableMetadata> query() {
        Connection connection = null;
        List<TableMetadata> metaDataList = Lists.newArrayList();
        try {
            Class.forName(JDBC_DRIVER);
            connection = connect("jdbc:mysql://"+dbIP+":"+dbPort+"/"+dbName,dbUsername,dbPassword);
            metaDataList = queryTable(connection);
            return metaDataList;
        }catch (Exception e){
            e.printStackTrace();
            System.err.println("数据库操作失败!");
        }finally {
            if(null != connection){
                close(connection);
            }
            return metaDataList;
        }

    }

    /**
     * 查询表头结构
     * @return
     */
    private List<TableMetadata> queryTable(Connection connection){
        /**
         * 源sql
         */
        String sql = "select table_name as tableName,engine,create_time as createTime,table_collation as tableCollection,table_comment as comment from information_schema.tables where table_schema ='"+dbName+"'";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            List<TableMetadata> tableMetadata = new ArrayList<>();
            TableMetadata metadata;
            while (resultSet.next()){
                metadata = new TableMetadata();
                metadata.setTableName(resultSet.getString("tableName"));
                metadata.setEngine(resultSet.getString("engine"));
                metadata.setCreateTime(resultSet.getString("createTime"));
                metadata.setTableCollection(resultSet.getString("tableCollection"));
                metadata.setComment(resultSet.getString("comment"));
                tableMetadata.add(metadata);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null != statement){
                try {
                    statement.close();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }

        return new ArrayList<>();
    }

    /**
     * 查询表字段结构
     * @return
     */
    private List<ColumnMetadata> queryColumn(Connection connection){
        return new ArrayList<>();
    }
}
