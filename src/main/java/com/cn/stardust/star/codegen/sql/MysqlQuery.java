package com.cn.stardust.star.codegen.sql;

import com.cn.stardust.star.codegen.CamelCaseConvert;
import com.cn.stardust.star.codegen.Metadata;
import com.cn.stardust.star.codegen.typeconvert.Convert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://github.com/oraclexing
 * <p>
 *
 * @author stardust
 * @version 1.0.0
 */
final public class MysqlQuery extends Query{

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


    public MysqlQuery(String dbIP, Integer dbPort, String dbName, String dbUsername, String dbPassword,Convert convert) {
        this.dbIP = dbIP;
        this.dbPort = dbPort;
        this.dbName = dbName;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
        this.convert = convert;
    }

    @Override
    public Map<String, List<Metadata>> query() {
        Connection connection = null;
        Map<String, List<Metadata>> resultMap = new HashMap<>();
        try {
            Class.forName(JDBC_DRIVER);
            connection = connect("jdbc:mysql://"+dbIP+":"+dbPort+"/"+dbName,dbUsername,dbPassword);
            Statement statement = connection.createStatement();
            /**
             * 源sql
             */
            String source = "select COLUMN_NAME as name, DATA_TYPE as type, COLUMN_COMMENT as comment from INFORMATION_SCHEMA.Columns " +
                    " where table_name='#####' and table_schema='"+dbName+"'";
            for(String tablename : tables){
                resultMap.put(CamelCaseConvert.toUpperCamelCase(tablename),getMetaData(statement,source.replaceAll("#####",tablename)));
            }
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
            System.err.println("数据库操作失败!");
        }
        if(null != connection){
            close(connection);
        }
        return resultMap;
    }

    /**
     * 返回一个表的元数据
     * @param statement
     * @param sql 查询sql
     * @return
     * @throws Exception
     */
    private List<Metadata> getMetaData(Statement statement,String sql)throws Exception{
        ResultSet resultSet = statement.executeQuery(sql);
        List<Metadata> metadatas = new ArrayList<>();
        Metadata metadata;
        while (resultSet.next()){
            metadata = new Metadata();
            metadata.setName(CamelCaseConvert.toLowerCamelCase(resultSet.getString("name")));
            metadata.setClazz(convert.getType(resultSet.getString("type")));
            metadata.setDesc(resultSet.getString("comment"));
            metadatas.add(metadata);
        }
        return metadatas;
    }

    @Override
    public void close(Connection con) {
        try {
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}