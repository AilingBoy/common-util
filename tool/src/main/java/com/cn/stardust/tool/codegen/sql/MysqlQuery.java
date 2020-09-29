package com.cn.stardust.tool.codegen.sql;

import com.beust.jcommander.internal.Lists;
import com.cn.stardust.tool.codegen.CamelCaseConvert;
import com.cn.stardust.tool.codegen.metadata.ClassMetaData;
import com.cn.stardust.tool.codegen.metadata.FieldMetaData;
import com.cn.stardust.tool.codegen.typeconvert.Convert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/KnowNoUnknown
 * <p>
 *  Mysql 查询器
 *
 *
 * @author stardust
 * @version 1.0.0
 */
final public class MysqlQuery extends Query{

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    /**
     * 数据库ip
     */
    private String jdbcUrl;

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


    public MysqlQuery(String jdbcUrl,String dbName, String dbUsername, String dbPassword,Convert convert) {
        this.jdbcUrl = jdbcUrl;
        this.dbName = dbName;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
        this.convert = convert;
    }

    /**
     * 查询元数据集合
     * @return 表的元数据集合
     */
    @Override
    public List<ClassMetaData> query() {
        Connection connection = null;
        List<ClassMetaData> metaDataList = Lists.newArrayList();
        try {
            Class.forName(JDBC_DRIVER);
            connection = connect(jdbcUrl,dbUsername,dbPassword);
            Statement statement = connection.createStatement();
            /**
             * 源sql
             */
            String source = "select COLUMN_NAME as name, DATA_TYPE as type, COLUMN_COMMENT as comment from INFORMATION_SCHEMA.Columns " +
                    " where table_name='#####' and table_schema='"+dbName+"'";
            for(String tablename : tables){
                metaDataList.add(getMetaData(statement,source.replaceAll("#####",tablename),tablename));
            }
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
            System.err.println("数据库操作失败!");
        }
        if(null != connection){
            close(connection);
        }
        return metaDataList;
    }

    /**
     * 返回一个表的元数据
     * @param statement
     * @param sql 查询sql
     * @return
     * @throws Exception
     */
    private ClassMetaData getMetaData(Statement statement, String sql,String tableName)throws Exception{
        ResultSet resultSet = statement.executeQuery(sql);
        ClassMetaData classMetaData = new ClassMetaData(tableName,CamelCaseConvert.toUpperCamelCase(tableName));
        List<FieldMetaData> metadatas = new ArrayList<>();
        FieldMetaData metadata;
        while (resultSet.next()){
            metadata = new FieldMetaData();
            metadata.setFieldName(CamelCaseConvert.toLowerCamelCase(resultSet.getString("name")));
            metadata.setName(resultSet.getString("name"));
            metadata.setClazz(convert.getType(resultSet.getString("type")));
            metadata.setDesc(resultSet.getString("comment"));
            metadatas.add(metadata);
        }
        classMetaData.setFieldMetaDatas(metadatas);
        return classMetaData;
    }
}