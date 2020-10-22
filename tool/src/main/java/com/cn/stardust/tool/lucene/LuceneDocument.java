package com.cn.stardust.tool.lucene;

import lombok.Data;

/**
 * 索引输出结构化数据
 *
 * @author stardust
 * @date 2020/10/15 14:17
 */
@Data
public class LuceneDocument {

    @Deprecated
    private String docId;

    /**
     * 业务主键id
     */
    private String businessId;

    /**
     * 标题
     */
    private String title;

    /**
     * 子标题
     */
    private String subTitle;

    /**
     * 内容
     */
    private String content;

    /**
     * 作者
     */
    private String author;

    /**
     * 来源
     */
    private String source;

    /**
     * 表名称
     */
    private String tableName;

}
