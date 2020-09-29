package com.cn.stardust.tool.dbexport.metadata;

import lombok.Data;

@Data
public class ColumnMetadata {
    /** 字段名 */
    private String name;
    /** 字段类型 */
    private String type;
    /** 是否为空 */
    private String nullable;
    /** 主键标识 */
    private String primaryKey;
    /** 备注 */
    private String comment;
    /** 默认值 */
    private String defaultValue;

}
