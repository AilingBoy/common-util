package com.cn.stardust.tool.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.util.StopWatch;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author stardust
 * @date 2020/10/13 14:57
 * Lucene
 * @see IndexWriter
 *
 */
public class IndexReader {

    public static void main(String[] args) throws Exception{
        search();
    }

    public void creatIndex(String[] args) {
        // 创建3个News对象
        News news1 = new News();
        news1.setId(1);
        news1.setTitle("安倍晋三本周会晤特朗普 将强调日本对美国益处");
        news1.setContent("日本首相安倍晋三计划2月10日在华盛顿与美国总统特朗普举行会晤时提出加大日本在美国投资的设想");
        news1.setReply(672);

        News news2 = new News();
        news2.setId(2);
        news2.setTitle("北大迎4380名新生 农村学生700多人近年最多");
        news2.setContent("昨天，北京大学迎来4380名来自全国各地及数十个国家的本科新生。其中，农村学生共700余名，为近年最多...");
        news2.setReply(995);

        News news3 = new News();
        news3.setId(3);
        news3.setTitle("特朗普宣誓(Donald Trump)就任美国第45任总统");
        news3.setContent("当地时间1月20日，唐纳德·特朗普在美国国会宣誓就职，正式成为美国第45任总统。");
        news3.setReply(1872);

        // 开始时间
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("Generate Index");
        System.out.println("**********开始创建索引**********");
        // 创建IK分词器
        Analyzer analyzer = new IKAnalyzer();
        IndexWriterConfig icw = new IndexWriterConfig(analyzer);
        // CREATE 表示先清空索引再重新创建
        icw.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        Directory dir = null;
        IndexWriter inWriter = null;
        // 存储索引的目录
        Path indexPath = Paths.get("newsIndex");

        try {
            if (!Files.isReadable(indexPath)) {
                System.out.println("索引目录 '" + indexPath.toAbsolutePath() + "' 不存在或者不可读,请检查");
                System.exit(1);
            }
            dir = FSDirectory.open(indexPath);
            inWriter = new IndexWriter(dir, icw);

            // 设置新闻ID索引并存储
            FieldType idType = new FieldType();
            idType.setIndexOptions(IndexOptions.DOCS);
            idType.setStored(true);

            // 设置新闻标题索引文档、词项频率、位移信息和偏移量，存储并词条化
            FieldType titleType = new FieldType();
            titleType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
            titleType.setStored(true);
            titleType.setTokenized(true);

            FieldType contentType = new FieldType();
            contentType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
            contentType.setStored(true);
            contentType.setTokenized(true);
            contentType.setStoreTermVectors(true);
            contentType.setStoreTermVectorPositions(true);
            contentType.setStoreTermVectorOffsets(true);

            Document doc1 = new Document();
            doc1.add(new Field("id", String.valueOf(news1.getId()), idType));
            doc1.add(new Field("title", news1.getTitle(), titleType));
            doc1.add(new Field("content", news1.getContent(), contentType));
            doc1.add(new IntPoint("reply", news1.getReply()));
            doc1.add(new StoredField("reply_display", news1.getReply()));

            Document doc2 = new Document();
            doc2.add(new Field("id", String.valueOf(news2.getId()), idType));
            doc2.add(new Field("title", news2.getTitle(), titleType));
            doc2.add(new Field("content", news2.getContent(), contentType));
            doc2.add(new IntPoint("reply", news2.getReply()));
            doc2.add(new StoredField("reply_display", news2.getReply()));

            Document doc3 = new Document();
            doc3.add(new Field("id", String.valueOf(news3.getId()), idType));
            doc3.add(new Field("title", news3.getTitle(), titleType));
            doc3.add(new Field("content", news3.getContent(), contentType));
            doc3.add(new IntPoint("reply", news3.getReply()));
            doc3.add(new StoredField("reply_display", news3.getReply()));

            inWriter.addDocument(doc1);
            inWriter.addDocument(doc2);
            inWriter.addDocument(doc3);

            inWriter.commit();

            inWriter.close();
            dir.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        stopWatch.stop();
        stopWatch.prettyPrint();
        System.out.println("**********索引创建完成**********");
    }




    public static void search() throws ParseException, IOException {
        // 搜索单个字段
        String field = "content";
        // 搜索多个字段时使用数组
        //String[] fields = { "title", "content" };

        Path indexPath = Paths.get("newsIndex");
        Directory dir = FSDirectory.open(indexPath);
        org.apache.lucene.index.DirectoryReader reader = DirectoryReader.open(dir);
        org.apache.lucene.index.IndexReader newReader = DirectoryReader.openIfChanged(reader);
        IndexSearcher searcher = new IndexSearcher(reader);
        //最细粒度分词
        Analyzer analyzer = new IKAnalyzer(false);
        QueryParser parser = new QueryParser(field, analyzer);

        // 多域搜索
        //MultiFieldQueryParser multiParser = new MultiFieldQueryParser(fields, analyzer);

        // 关键字同时成立使用 AND, 默认是 OR
        parser.setDefaultOperator(QueryParser.Operator.AND);
        // 查询语句，查询关键词
        Query query = parser.parse("设想");
        System.out.println("Query:" + query.toString());

        // 返回前10条
        TopDocs tds = searcher.search(query, 10);
        for (ScoreDoc sd : tds.scoreDocs) {
            // Explanation explanation = searcher.explain(query, sd.doc);
            // System.out.println("explain:" + explanation + "\n");
            Document doc = searcher.doc(sd.doc);
            System.out.println("DocID:" + sd.doc);
            System.out.println("id:" + doc.get("id"));
            System.out.println("title:" + doc.get("title"));
            System.out.println("content:" + doc.get("content"));
            System.out.println("文档评分:" + sd.score);
        }
        dir.close();
        reader.close();
    }
}