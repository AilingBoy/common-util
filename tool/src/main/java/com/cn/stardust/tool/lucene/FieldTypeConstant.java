package com.cn.stardust.tool.lucene;

import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;

/**
 * @author stardust
 * @date 2020/10/15 14:51
 */
public class FieldTypeConstant {

    public static FieldType BUSINESS_ID;

    public static FieldType TITLE;

    public static FieldType SUB_TITLE;

    public static FieldType CONTENT;

    public static FieldType AUTHOR;

    public static FieldType SOURCE;

    public static FieldType TABLE_NAME;

    static {
        BUSINESS_ID = new FieldType();
        // 是否创建分词
        BUSINESS_ID.setTokenized(Boolean.FALSE);
        // 是否创建索引
        BUSINESS_ID.setIndexOptions(IndexOptions.NONE);
        // 是否存储
        BUSINESS_ID.setStored(Boolean.TRUE);

        TITLE = new FieldType();
        TITLE.setTokenized(Boolean.TRUE);
        TITLE.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
        TITLE.setStored(Boolean.TRUE);

        SUB_TITLE = new FieldType();
        SUB_TITLE.setTokenized(Boolean.TRUE);
        SUB_TITLE.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
        SUB_TITLE.setStored(Boolean.TRUE);

        CONTENT = new FieldType();
        CONTENT.setTokenized(Boolean.TRUE);
        CONTENT.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
        CONTENT.setStored(Boolean.FALSE);

        AUTHOR = new FieldType();
        AUTHOR.setTokenized(Boolean.TRUE);
        AUTHOR.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
        AUTHOR.setStored(Boolean.TRUE);

        SOURCE = new FieldType();
        SOURCE.setTokenized(Boolean.TRUE);
        SOURCE.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
        SOURCE.setStored(Boolean.TRUE);

        TABLE_NAME = new FieldType();
        TABLE_NAME.setTokenized(Boolean.FALSE);
        TABLE_NAME.setIndexOptions(IndexOptions.NONE);
        TABLE_NAME.setStored(Boolean.TRUE);
    }
}