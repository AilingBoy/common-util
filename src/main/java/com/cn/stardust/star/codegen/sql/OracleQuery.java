package com.cn.stardust.star.codegen.sql;

import com.beust.jcommander.internal.Lists;
import com.cn.stardust.star.codegen.CamelCaseConvert;
import com.cn.stardust.star.codegen.metadata.ClassMetaData;
import com.cn.stardust.star.codegen.metadata.FieldMetaData;
import com.cn.stardust.star.codegen.typeconvert.Convert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/oraclexing
 * <p>
 * Oracle sql查询器
 *
 * @author stardust
 * @version 1.0.0
 */
public class OracleQuery extends Query {

    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";

    /**
     * 数据库ip
     */
    private String dbIP;

    /**
     * 数据库端口号
     */
    private Integer dbPort = 1521;

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

    public OracleQuery(String dbIP , String dbName, String dbUsername, String dbPassword, Convert convert) {
        this.dbIP = dbIP;
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
            connection = connect("jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS =(PROTOCOL = TCP)(HOST = "+dbIP+")(PORT = "+dbPort+"))(CONNECT_DATA =(SERVER = DEDICATED)(SERVICE_NAME = "+dbName+")))",dbUsername,dbPassword);
            Statement statement = connection.createStatement();
            String source = "SELECT USER_TAB_COLS.COLUMN_NAME as name,Lower(USER_TAB_COLS.DATA_TYPE) as \"type\"," +
                    "user_col_comments.comments as \"comment\" " +
                    "from USER_TAB_COLS left join user_tab_comments utc on USER_TAB_COLS.TABLE_NAME = utc.table_name " +
                    "inner join user_col_comments on user_col_comments.TABLE_NAME = USER_TAB_COLS.TABLE_NAME " +
                    "and user_col_comments.COLUMN_NAME = USER_TAB_COLS.COLUMN_NAME and USER_TAB_COLS.TABLE_NAME = '#####'";
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
        ClassMetaData classMetaData = new ClassMetaData(tableName, CamelCaseConvert.toUpperCamelCase(tableName));
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
