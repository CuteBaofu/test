package com.itcast.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LuceneTest {

    /**
     * 创建索引（创建一个）
     */
    @Test
    public void indexCreate() throws IOException {

        // 创建文档对象(一条记录就是一个Document)
        Document document = new Document();
        // 添加字段，参数Field是一个接口，要new实现类的对象(StringField, TextField)
        // StringField的实例化需要3个参数：1-字段名，2-字段值，3-是否保存到文档，Store.YES存储，NO不存储
        document.add(new StringField("id", "1", Field.Store.YES));
        // TextField：创建索引并提供分词，StringField创建索引但不分词，如果不分词，会造成整个字段作为一个词条，除非用户完全匹配，否则搜索不到
        document.add(new TextField("title", "谷歌地图之父跳槽FaceBook", Field.Store.YES));


        // 创建目录对象，指定索引库的存放位置；FSDirectory文件系统，会把索引库保存到本地磁盘；RAMDirectory内存，会把索引库保存在内存
        Directory dir = FSDirectory.open(new File("E:\\资料\\itheima\\品优购\\lucene\\indexDir"));
        //创建分词器对象
        Analyzer analyzer = new StandardAnalyzer();
        //创建索引写入器配置对象，第一个参数版本VerSion.LATEST,第一个参数分词器
        IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, analyzer);
        // 创建索引写入器
        IndexWriter indexWriter = new IndexWriter(dir, config);

        // 向索引库写入文档对象
        indexWriter.addDocument(document);
        //提交
        indexWriter.commit();
        //关闭
        indexWriter.close();

    }


    /**
     * 创建索引（创建一个）
     */
    @Test
    public void indexCreate2() throws IOException {

        // 创建文档对象集合
        List<Document> docs = new ArrayList<Document>();

        // 创建文档对象(一条记录就是一个Document)
        Document document1 = new Document();
        // 添加字段，参数Field是一个接口，要new实现类的对象(StringField, TextField)
        // StringField的实例化需要3个参数：1-字段名，2-字段值，3-是否保存到文档，Store.YES存储，NO不存储
        document1.add(new StringField("id", "3", Field.Store.YES));
        // TextField：创建索引并提供分词，StringField创建索引但不分词，如果不分词，会造成整个字段作为一个词条，除非用户完全匹配，否则搜索不到
        document1.add(new TextField("title", "谷歌地图之父跳槽FaceBook", Field.Store.YES));
        docs.add(document1);


        // 创建文档对象(一条记录就是一个Document)
        Document document2 = new Document();
        // 添加字段，参数Field是一个接口，要new实现类的对象(StringField, TextField)
        // StringField的实例化需要3个参数：1-字段名，2-字段值，3-是否保存到文档，Store.YES存储，NO不存储
        document2.add(new StringField("id", "4", Field.Store.YES));
        // TextField：创建索引并提供分词，StringField创建索引但不分词，如果不分词，会造成整个字段作为一个词条，除非用户完全匹配，否则搜索不到
        document2.add(new TextField("title", "一定会考上", Field.Store.YES));
        docs.add(document2);

        // 创建目录对象，指定索引库的存放位置；FSDirectory文件系统，会把索引库保存到本地磁盘；RAMDirectory内存，会把索引库保存在内存
        Directory dir = FSDirectory.open(new File("E:\\资料\\itheima\\品优购\\lucene\\indexDir"));
        //创建分词器对象
        Analyzer analyzer = new StandardAnalyzer();
        //创建索引写入器配置对象，第一个参数版本VerSion.LATEST,第一个参数分词器
        IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, analyzer);
        // 创建索引写入器
        IndexWriter indexWriter = new IndexWriter(dir, config);

        // 向索引库写入文档对象
        indexWriter.addDocuments(docs);
        //提交
        indexWriter.commit();
        //关闭
        indexWriter.close();

    }


    /**
     * IK分词器
     */
    @Test
    public void iKAnalyzer() throws IOException {

        // 创建文档对象(一条记录就是一个Document)
        Document document = new Document();
        // 添加字段，参数Field是一个接口，要new实现类的对象(StringField, TextField)
        // StringField的实例化需要3个参数：1-字段名，2-字段值，3-是否保存到文档，Store.YES存储，NO不存储
        document.add(new StringField("id", "1", Field.Store.YES));
        // TextField：创建索引并提供分词，StringField创建索引但不分词，如果不分词，会造成整个字段作为一个词条，除非用户完全匹配，否则搜索不到
        document.add(new TextField("title", "传智播客", Field.Store.YES));


        // 创建目录对象，指定索引库的存放位置；FSDirectory文件系统，会把索引库保存到本地磁盘；RAMDirectory内存，会把索引库保存在内存
        Directory dir = FSDirectory.open(new File("E:\\资料\\itheima\\品优购\\lucene\\indexDir"));
        //创建分词器对象
        IKAnalyzer analyzer = new IKAnalyzer();
        //创建索引写入器配置对象，第一个参数版本VerSion.LATEST,第一个参数分词器
        IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, analyzer);
        // 创建索引写入器
        IndexWriter indexWriter = new IndexWriter(dir, config);

        // 向索引库写入文档对象
        indexWriter.addDocument(document);
        //提交
        indexWriter.commit();
        //关闭
        indexWriter.close();

    }

    /**
     * 查询索引数据
     */
    @Test
    public void testSearch() throws IOException, ParseException {
        // 创建目录对象，指定索引库的存放位置
        Directory dir = FSDirectory.open(new File("E:\\资料\\itheima\\品优购\\lucene\\indexDir"));

        // 索引读取工具
        IndexReader indexReader = DirectoryReader.open(dir);
        // 索引搜寻对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        // 创建查询解析器对象,两个字段（默认要查询的字段名称，分词器）
        QueryParser parser = new QueryParser("title", new IKAnalyzer());
        // 创建查询对象
        Query query = parser.parse("谷歌");
        // 执行搜索操作，返回值topDocs包含命中数，得分文档
        TopDocs topDocs = indexSearcher.search(query, Integer.MAX_VALUE);
        // 打印命中数
        System.out.println("一共命中：" + topDocs.totalHits + "条数据");
        // 获得得分文档数组对象，得分文档对象包含得分和文档编号
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            System.out.println("得分：" + scoreDoc.score);
            // 文档的编号
            int doc = scoreDoc.doc;
            System.out.println("编号：" + doc);
            // 获取文档对象，通过索引读取工具
            Document document = indexReader.document(doc);
            System.out.println("id:" + document.get("id"));
            System.out.println("title:" + document.get("title"));

        }
    }


    /**
     * 多字段查询解析器-->MultiFieldQueryParser
     */
   /* @Test
    public void testSearch2() throws IOException, ParseException {
        // 创建目录对象，指定索引库的存放位置
        Directory dir = FSDirectory.open(new File("E:\\资料\\itheima\\品优购\\lucene\\indexDir"));

        // 索引读取工具
        IndexReader indexReader = DirectoryReader.open(dir);
        // 索引搜寻对象,IndexSearcher需要依赖IndexReader类
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        // 创建查询解析器对象,两个字段（默认要查询的字段名称，分词器）
        MultiFieldQueryParser parser = new MultiFieldQueryParser(new String[]{"id","title"}, new IKAnalyzer());
        // 创建查询对象
        Query query = parser.parse("谷歌地图之父");
        // 执行搜索操作，返回值topDocs包含命中数，得分文档
        *//**在TopDocs中，包含两部分信息：
     int totalHits ：查询到的总条数
     ScoreDoc[] scoreDocs	： 得分文档对象的数组
     *//*
        TopDocs topDocs = indexSearcher.search(query, Integer.MAX_VALUE);
        // 打印命中数
        System.out.println("一共命中：" + topDocs.totalHits + "条数据");
        // 获得得分文档数组对象，得分文档对象包含得分和文档编号
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        *//**
     * ScoreDoc是得分文档对象，包含两部分数据：
     int doc	：文档的编号
     float score	：文档的得分信息
     *//*
        for (ScoreDoc scoreDoc : scoreDocs) {
            System.out.println("得分：" + scoreDoc.score);
            // 文档的编号
            int doc = scoreDoc.doc;
            System.out.println("文档编号：" + doc);
            // 获取文档对象，通过索引读取工具
            Document document = indexReader.document(doc);
            System.out.println("id:" + document.get("id"));
            System.out.println("title:" + document.get("title"));
        }
    }*/


    /**
     * 抽取公用的搜索方法
     */

    public void searcher(Query query) throws Exception {

        Directory dir = FSDirectory.open(new File("E:\\资料\\itheima\\品优购\\lucene\\indexDir"));
        // 索引的读取对象
        IndexReader indexReader = DirectoryReader.open(dir);
        // 索引的搜索工具
        IndexSearcher searcher = new IndexSearcher(indexReader);
        // 尝试查询，1-查询对象，2-查询的条数
        // 返回的是前n条文档的对象，topDocs：包含文档的总条数，文档的得分数组
        TopDocs topDocs = searcher.search(query, 10);

        System.out.println("搜索的命中总条数：" + topDocs.totalHits);
        // 获取得分文档的数组，得分文档包含文档编号以及得分
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            System.out.println("文档编号" + scoreDoc.doc);
            System.out.println("文档得分" + scoreDoc.score);
            // 根据编号查询文档
            Document document = indexReader.document(scoreDoc.doc);
            System.out.println(document.get("id"));
            System.out.println(document.get("title"));
        }
    }

    /**
     * 词条查询/
     * 查询条件必须是最小粒度不可再分割的内容
     * 场景：不可分割的字段可以采用，比如id
     * 缺点：只能查询一个词，例如可以查询"谷歌"，不能查询"谷歌地图"
     *
     * @throws IOException
     * @throws ParseException
     */
    @Test
    public void termQuerySearch() throws Exception {

        Query query = new TermQuery(new Term("title", "谷歌"));
        searcher(query);

    }

    /**
     * WildcardQuery（通配符查询）
     * 查询条件对象（通配符)
     * ?：通配一个字符
     * *：通配多个字符
     *
     * @throws Exception
     */
    @Test
    public void WildCardQuerySearch() throws Exception {

        WildcardQuery query = new WildcardQuery(new Term("title", "传智播?"));
        WildcardQuery query1 = new WildcardQuery(new Term("title", "*智*"));

        searcher(query);
        searcher(query1);

    }

    /**
     * 模糊查询FuzzyQuery
     * 查询条件对象（模糊查询
     * 参数：1-词条，查询字段及关键词，关键词允许写错；2-允许写错的最大编辑距离，并且不能大于2（0~2）
     * 最大编辑距离：facebool-->facebook需要编辑的次数，包括大小写
     */
    @Test
    public void fuzzyQuerySearch() throws Exception {

        FuzzyQuery query = new FuzzyQuery(new Term("title", "传智从i"));//传智播客
        FuzzyQuery query1 = new FuzzyQuery(new Term("title", "智传播i"), 2);//传智播客
        searcher(query1);

    }

    /**
     * NumericRangeQuery（数值范围查询）
     * 查询条件对象（数值范围查询
     * 查询非String类型的数据或者说是一些继承Numeric类的对象的查询
     * 1-字段；2-最小值；3-最大值；4-是否包含最小值；5-是否包含最大值
     */
    @Test
    public void numericRangeQuerySearch() throws Exception {

        Query query = NumericRangeQuery.newLongRange("id", 2L, 4L, true, true);
        searcher(query);

    }


    /**
     * BooleanQuery（组合查询）
     */
    @Test
    public void BooleanQuerySearch() throws Exception {
        Query query1 = NumericRangeQuery.newLongRange("id", 2l, 4l, true, true);
        Query query2 = NumericRangeQuery.newLongRange("id", 1l, 3l, true, true);

        // boolean查询本身没有查询条件，它可以组合其他查询
        BooleanQuery query = new BooleanQuery();
        // 交集： Occur.MUST + Occur.MUST
        // 并集：Occur.SHOULD + Occur.SHOULD
        // 非：Occur.MUST_NOT
        query.add(query1, BooleanClause.Occur.SHOULD);
        query.add(query2, BooleanClause.Occur.SHOULD);

        searcher(query);

    }

    /**
     * 更新索引
     * 本质先删除再添加
     * 先删除所有满足条件的文档，再创建文档
     * 因此，更新索引通常要根据唯一字段
     *
     * @throws IOException
     */
    @Test
    public void testUpdate() throws IOException {

        Document document = new Document();
        document.add(new StringField("id", "5", Field.Store.YES));
        document.add(new StringField("title", "李文苑的大宝贝", Field.Store.YES));

        Directory dir = FSDirectory.open(new File("E:\\资料\\itheima\\品优购\\lucene\\indexDir"));
        IndexWriterConfig conf = new IndexWriterConfig(Version.LATEST, new IKAnalyzer());
        IndexWriter indexWriter = new IndexWriter(dir, conf);

        // 执行更新操作
        indexWriter.updateDocument(new Term("id", "1"), document);

        // 提交
        indexWriter.commit();
        // 关闭
        indexWriter.close();

    }


    /**
     * 删除索引
     */
    @Test
    public void deleIndex() throws IOException {

        Directory dir = FSDirectory.open(new File("E:\\资料\\itheima\\品优购\\lucene\\indexDir"));
        IndexWriterConfig conf = new IndexWriterConfig(Version.LATEST, new IKAnalyzer());
        IndexWriter indexWriter = new IndexWriter(dir, conf);

        //删除操作
        indexWriter.deleteDocuments(new Term("id", "2"));

        //删除全部
        //indexWriter.deleteAll();

        // 提交
        indexWriter.commit();
        // 关闭
        indexWriter.close();

    }


    /**
     * 高亮显示
     */
    @Test
    public void highLighter() throws IOException, ParseException, InvalidTokenOffsetsException {


        Directory dir = FSDirectory.open(new File("E:\\资料\\itheima\\品优购\\lucene\\indexDir"));
        IndexReader indexReader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(indexReader);

        QueryParser parser = new QueryParser("title", new IKAnalyzer());
        Query query = parser.parse("谷歌地图");

        //格式化器
        SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<em>", "</em>");
        QueryScorer scorer = new QueryScorer(query);
        // 准备高亮工具
        Highlighter highlighter = new Highlighter(formatter, scorer);

        // 搜索
        TopDocs topDocs = searcher.search(query, 10);
        System.out.println("本次搜索共" + topDocs.totalHits + "条数据");
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            // 获取文档编号
            int docID = scoreDoc.doc;
            Document doc = indexReader.document(docID);
            System.out.println("id: " + doc.get("id"));

            java.lang.String title = doc.get("title");

            // 用高亮工具处理普通的查询结果,参数：分词器，要高亮的字段的名称，高亮字段的原始值
            String hTitle = highlighter.getBestFragment(new IKAnalyzer(), "title", title);

            System.out.println("title: " + hTitle);
            // 获取文档的得分
            System.out.println("得分：" + scoreDoc.score);
        }
    }

    /**
     * 排序
     */
    @Test
    public void sortQuery() throws ParseException, IOException {

        Directory dir = FSDirectory.open(new File("E:\\资料\\itheima\\品优购\\lucene\\indexDir"));
        IndexReader indexReader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(indexReader);

        QueryParser parser = new QueryParser("title", new IKAnalyzer());
        Query query = parser.parse("谷歌地图");

        // 创建排序对象,需要排序字段SortField，参数：字段的名称、字段的类型、是否反转如果是false，升序。true降序
        Sort sort = new Sort(new SortField("id", SortField.Type.LONG, false));

        // 搜索
        TopDocs topDocs = searcher.search(query, 10, sort);
        System.out.println("本次搜索共" + topDocs.totalHits + "条数据");

        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            // 获取文档编号
            int docID = scoreDoc.doc;
            Document doc = indexReader.document(docID);
            System.out.println("id: " + doc.get("id"));
            System.out.println("title: " + doc.get("title"));
        }

    }

    /**
     * 分页
     */
    @Test
    public void testPageQuery() throws Exception {
        // 实际上Lucene本身不支持分页。因此我们需要自己进行逻辑分页。我们要准备分页参数：
        int pageSize = 1;// 每页条数
        int pageNum = 1;// 当前页码
        int start = (pageNum - 1) * pageSize;// 当前页的起始条数
        int end = start + pageSize;// 当前页的结束条数（不能包含）

        // 目录对象
        Directory directory = FSDirectory.open(new File("E:\\资料\\itheima\\品优购\\lucene\\indexDir"));
        // 创建读取工具
        IndexReader reader = DirectoryReader.open(directory);
        // 创建搜索工具
        IndexSearcher searcher = new IndexSearcher(reader);

        QueryParser parser = new QueryParser("title", new IKAnalyzer());
        Query query = parser.parse("谷歌地图");

        // 创建排序对象,需要排序字段SortField，参数：字段的名称、字段的类型、是否反转如果是false，升序。true降序
        Sort sort = new Sort(new SortField("id", SortField .Type.LONG, false));
        // 搜索数据，查询0~end条
        TopDocs topDocs = searcher.search(query, end,sort);
        System.out.println("本次搜索共" + topDocs.totalHits + "条数据");

        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (int i = start; i < end; i++) {
            ScoreDoc scoreDoc = scoreDocs[i];
            // 获取文档编号
            int docID = scoreDoc.doc;
            Document doc = reader.document(docID);
            System.out.println("id: " + doc.get("id"));
            System.out.println("title: " + doc.get("title"));
        }
    }


}
