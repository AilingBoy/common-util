package com.cn.stardust.tool.dbexport.metadata;

import lombok.Data;

import java.util.List;

@Data
public class DatabaseMetadata {

    private String ip;
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

    private List<TableMetadata> tableMetadatas;


    public DatabaseMetadata(String ip, Integer dbPort, String dbName, String dbUsername, List<TableMetadata> tableMetadatas) {
        this.ip = ip;
        this.dbPort = dbPort;
        this.dbName = dbName;
        this.dbUsername = dbUsername;
        this.tableMetadatas = tableMetadatas;
    }
}
