package com.cn.stardust.star.dbexport.sql;

import com.cn.stardust.star.dbexport.metadata.ColumnMetadata;
import com.cn.stardust.star.dbexport.metadata.DatabaseMetadata;
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

    public MysqlQuery(String dbIP ,Integer dbPort, String dbName, String dbUsername, String dbPassword) {
        this.dbIP = dbIP;
        this.dbPort = dbPort;
        this.dbName = dbName;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
    }

    @Override
    public DatabaseMetadata query() {
        Connection connection = null;
        List<TableMetadata> metaDataList;
        DatabaseMetadata metadata = null;
        try {
            Class.forName(JDBC_DRIVER);
            connection = connect("jdbc:mysql://"+dbIP+":"+dbPort+"/"+dbName+"?characterEncoding=utf8&useSSL=false",dbUsername,dbPassword);
            metaDataList = queryTable(connection);
            queryColumn(connection,metaDataList);
            metadata = new DatabaseMetadata(dbIP,dbPort,dbName,dbUsername,metaDataList);
        }catch (Exception e){
            e.printStackTrace();
            System.err.println("数据库操作失败!");
        }finally {
            if(null != connection){
                close(connection);
            }
            return metadata;
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
        List<TableMetadata> tableMetadata = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            tableMetadata = new ArrayList<>();
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
        return tableMetadata;
    }

    /**
     * 查询表字段结构
     * @return
     */
    private void queryColumn(Connection connection,List<TableMetadata> tableMetadatas){
        if(null == tableMetadatas || tableMetadatas.size() == 0){
            return ;
        }
        String sql = "select table_name as tableName,column_name as name,column_type as type,is_nullable as nullable,column_key as primaryKey,column_comment as comment,column_default as defaultValue from information_schema.columns where table_schema = '"+dbName+"'";

        Statement statement = null;
        List<ColumnMetadata> columnMetadatas = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            String lastColumnTableName = new String();
            ColumnMetadata columnMetadata;
            while (resultSet.next()){
                String tableName =  resultSet.getString("tableName");
                if(!tableName.equals(lastColumnTableName) || null == columnMetadatas){
                    columnMetadatas = getColumnMetadata(tableMetadatas,tableName);
                }
                columnMetadata = new ColumnMetadata();
                columnMetadata.setName(resultSet.getString("name"));
                columnMetadata.setType(resultSet.getString("type"));
                columnMetadata.setNullable(resultSet.getString("nullable"));
                columnMetadata.setPrimaryKey(resultSet.getString("primaryKey"));
                columnMetadata.setComment(resultSet.getString("comment"));
                columnMetadata.setDefaultValue(resultSet.getString("defaultValue"));
                columnMetadatas.add(columnMetadata);
                lastColumnTableName = tableName;
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
    }

    private List<ColumnMetadata> getColumnMetadata(List<TableMetadata> tableMetadatas,String tableName){
        TableMetadata metadata = tableMetadatas.stream().filter(e ->e.getTableName().equals(tableName)).findFirst().get();
        if(null != metadata){
            if(null == metadata.getColumns()){
                metadata.setColumns(new ArrayList<>());
            }
            return metadata.getColumns();
        }
        return null;
    }
}
