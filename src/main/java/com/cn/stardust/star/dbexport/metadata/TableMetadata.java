package com.cn.stardust.star.dbexport.metadata;

import lombok.Data;

import java.util.List;

@Data
public class TableMetadata {
    /** 表名称 */
    private String tableName;
    /** 存储引擎 */
    private String engine;
    /** 建立表日期 */
    private String createTime;
    /** 字符编码 */
    private String tableCollection;
    /** 表备注 */
    private String comment;
    /** 表中字段 */
    private List<ColumnMetadata> columns;

}
