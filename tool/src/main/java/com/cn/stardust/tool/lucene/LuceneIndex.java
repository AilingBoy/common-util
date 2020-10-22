package com.cn.stardust.tool.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * lucene 索引
 * @author stardust
 * @date 2020/10/15 14:24
 */
public class LuceneIndex implements Index{

    private IndexWriter indexWriter;

    @Override
    public Boolean createIndex(List<LuceneDocument> documents) throws IOException {
        List<Document> docs = new ArrayList<>();
        for(LuceneDocument document : documents){
            docs.add(convertDocument(document));
        }
        indexWriter.addDocuments(docs);
        indexWriter.commit();
        return Boolean.TRUE;
    }

    @Override
    public Boolean createIndex(LuceneDocument luceneDocument)throws IOException {
        indexWriter.addDocument(convertDocument(luceneDocument));
        indexWriter.commit();
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteIndex(String docId)throws IOException {
        Term term = new Term("docId",docId);
        indexWriter.deleteDocuments(term);
        return Boolean.TRUE;
    }

    private Document convertDocument(LuceneDocument luceneDocument){
        Document document = new Document();
        // todo 优化魔法字段
        document.add(new StoredField("id",luceneDocument.getBusinessId(), FieldTypeConstant.BUSINESS_ID));
        if(!StringUtils.isEmpty(luceneDocument.getTitle())){
            document.add(new Field("title", luceneDocument.getTitle(), FieldTypeConstant.TITLE));
        }
        if(!StringUtils.isEmpty(luceneDocument.getContent())){
            document.add(new Field("content",luceneDocument.getContent(), FieldTypeConstant.CONTENT));
        }
        if(!StringUtils.isEmpty(luceneDocument.getSubTitle())){
            document.add(new Field("subTitle", luceneDocument.getSubTitle(), FieldTypeConstant.SUB_TITLE));
        }
        if(!StringUtils.isEmpty(luceneDocument.getSource())){
            document.add(new Field("source", luceneDocument.getSource(), FieldTypeConstant.SOURCE));
        }
        document.add(new StoredField("tableName", luceneDocument.getTableName(), FieldTypeConstant.TABLE_NAME));
        return document;
    }
}