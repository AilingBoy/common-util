package com.cn.stardust.tool.dbexport;

import com.cn.stardust.tool.dbexport.metadata.DatabaseMetadata;
import com.cn.stardust.tool.dbexport.office.WordWriter;
import com.cn.stardust.tool.dbexport.sql.MysqlQuery;
import com.cn.stardust.tool.dbexport.sql.Query;

/**
 * https://github.com/KnowNoUnknown
 * <p>
 *
 * @author stardust
 * @version 1.0.0
 * <p>
 * 数据库导出到word
 */

public class Export2Word {

    private String dbIpAddress;

    private Integer dpPort;

    private String userName;

    private String password;

    private String dbName;

    public Export2Word(String dbIpAddress, Integer dpPort,String dbName, String userName, String password) {
        this.dbIpAddress = dbIpAddress;
        this.dpPort = dpPort;
        this.dbName = dbName;
        this.userName = userName;
        this.password = password;
    }

    /**
     * 启动示例
     * @param args
     */
    public static void main(String[] args) {
        Query mysqlQuery = new MysqlQuery("127.0.0.1","dev","mysql","123456");
        DatabaseMetadata metadata = mysqlQuery.query();
        WordWriter wordWriter = new WordWriter();
        wordWriter.export(metadata,"C:/Users/MyPC/Desktop");
        System.out.println("================ Export Finished! ================");
    }

    /**
     * mysql 数据库导出
     */
    public void exportMysqlDB(String exportPath){
        Query mysqlQuery = new MysqlQuery(dbIpAddress,dpPort,dbName,userName,password);
        DatabaseMetadata metadata = mysqlQuery.query();
        WordWriter wordWriter = new WordWriter();
        wordWriter.export(metadata,exportPath);
        System.out.println("================ Export Finished! ================");
    }

    /**
     * oracle 数据库导出
     */
    public static void exportOracleDB(){
        // todo 待添加Oracle数据库导出支持
    }
}
