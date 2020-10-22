package com.cn.stardust.tool.lucene;

import java.io.IOException;
import java.util.List;

/**
 *
 * 索引接口
 * @author stardust
 * @date 2020/10/15 14:08
 */
public interface Index {

    /**
     * 创建索引
     * @return
     */
    Boolean createIndex(List<LuceneDocument> documents)throws IOException ;

    /**
     * 创建索引
     * @return
     */
    Boolean createIndex(LuceneDocument document)throws IOException;

    /**
     * 删除索引
     * @return
     */
    Boolean deleteIndex(String docId)throws IOException ;
}
