package com.example.study.service;


import com.example.study.bean.Subject;
import com.example.study.bean.Subject_info;
import com.example.study.bean.User;
import com.example.study.util.ESconfig;
import com.example.study.util.PageResult;
import com.google.gson.Gson;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Service
public class ESService {

    @Autowired
    private RestHighLevelClient elasticsearchClient;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private SubjectInfoService subjectInfoService;
    public Map<String,String> search(Map<String,String> m){

        //封装查询请求
        SearchRequest searchRequest=new SearchRequest("course");
        searchRequest.types("doc");
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();//query
        BoolQueryBuilder boolQueryBuilder=new BoolQueryBuilder();     //bool

        //关键字查询
        MatchQueryBuilder matchQueryBuilder= QueryBuilders.matchQuery("name",m.get("keywords"));//name
        boolQueryBuilder.must(matchQueryBuilder);
        //分类过滤
        String categoryName=m.get("category");
        if(categoryName!=null&&!"".equals(categoryName)) {
            TermQueryBuilder termQueryBuilder_category = QueryBuilders.termQuery("category", m.get("category"));//term
            boolQueryBuilder.filter(termQueryBuilder_category);  //filter
        }
        //标签过滤
        TermQueryBuilder termQueryBuilder_lab=QueryBuilders.termQuery("lab",m.get("lab"));//term
        boolQueryBuilder.filter(termQueryBuilder_lab);  //filter


        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);

        //聚合查询
        TermsAggregationBuilder termsAggregationBuilder=AggregationBuilders.terms("sku_category").field("category");//agg term
        searchSourceBuilder.aggregation(termsAggregationBuilder);
        //获取执行结果
        Map resultMap=new HashMap();
        try {
            //课程列表
            SearchResponse  searchResponse= elasticsearchClient.search(searchRequest,RequestOptions.DEFAULT);
            SearchHits searchHits=searchResponse.getHits();
            SearchHit[] hits=searchHits.getHits();
            List<Map<String,Object>> resultList=new ArrayList<>();
            for (SearchHit hit:hits){
                resultList.add(hit.getSourceAsMap());
            }
            resultMap.put("rows",resultList);
            //分类列表
            Aggregations aggregations=   searchResponse.getAggregations();
            Map<String, Aggregation>  map=aggregations.asMap();
            Terms terms=(Terms)map.get("sku_category");
            List<? extends Terms.Bucket> buckets=terms.getBuckets();
            List<String> listCategory=new ArrayList<>();
            for (Terms.Bucket bucket :buckets) {
               listCategory.add(bucket.getKeyAsString());
            }
            resultMap.put("categorys",listCategory);
            //一级标签列表



            //二级标签列表




           //三级标签列表







        } catch (IOException e) {
            e.printStackTrace();
        }

        return  resultMap;
    }


    public  void loadDatetoEs(){
        //封装请求对象
        BulkRequest bulkRequest=new BulkRequest();
        //从数据库查询数据
       List<Subject> subjects=subjectService.showSubjects();
        for (Subject s:subjects) {
            //1从数据库得到课程额外信息
           // 1.1获取课程id
            String subjectId=s.getId();
            //1.2构造课程信息查询参数
            Map params= new HashMap<String,String>();
            params.put("subject_id",subjectId);
            //1.3根据课程id查询课程附带信息，一般不超过6个附带信息
            PageResult<Subject_info> infos=subjectInfoService.findPage(1,6,params);
            //1.4封装额外信息
            Map infoMap=new HashMap();
            List<Subject_info>  infoList=infos.getRows();
            for (Subject_info info: infoList) {
                infoMap.put(info.getSubject_info_name(),info.getValue_list());
            }
            //2封装课程信息
            IndexRequest indexRequest=new IndexRequest("course","doc",subjectId);
            Map map=new HashMap();
            map.put("subject_name",s.getSubject_name());
            map.put("subject_url",s.getSubject_url());
            map.put("image_url",s.getImage_url());

            map.put("infos",infoMap);
            //3转换为json格式
            Gson gson=new Gson();
            String mapjson=gson.toJson(map);
            indexRequest.source(mapjson, XContentType.JSON);
            //4.添加到请求桶
            bulkRequest.add(indexRequest);
        }

        //5获取执行结果
        BulkResponse bulkResponse=null;
        try {
            bulkResponse = elasticsearchClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            int s= bulkResponse.status().getStatus();
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /*
    测试高级rest注入是否成功
     **/
    public void test(){
      System.out.println(elasticsearchClient.toString());

    }

    /**
     * 测试分类查询
     */
    public static void aggs(){
        //连接rest接口
        HttpHost httpHost=new HttpHost("127.0.0.1",9200,"http");
        RestClientBuilder restClientBuilder= RestClient.builder(httpHost);
        RestHighLevelClient restHighLevelClient=new RestHighLevelClient(restClientBuilder);
        //封装查询请求
        SearchRequest searchRequest=new SearchRequest("course");
        searchRequest.types("doc");
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();//query

        TermsAggregationBuilder termsAggregationBuilder=AggregationBuilders.terms("sku_category").field("category");//agg term



        searchSourceBuilder.aggregation(termsAggregationBuilder);
        searchSourceBuilder.size(0);
        searchRequest.source(searchSourceBuilder);
        //获取执行结果
        try {
            SearchResponse  searchResponse= restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
            Aggregations aggregations=   searchResponse.getAggregations();
             Map<String, Aggregation>  map=aggregations.asMap();
            Terms terms=(Terms)map.get("sku_category");
            List<? extends Terms.Bucket> buckets=terms.getBuckets();
            for (Terms.Bucket bucket :buckets) {
                System.out.println("--------------agg>"+bucket.getKeyAsString()+":"+bucket.getDocCount());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (restHighLevelClient != null)
                    restHighLevelClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 测试过滤查询
     */
    public static void filterSelect(){
    //连接rest接口
    HttpHost httpHost=new HttpHost("127.0.0.1",9200,"http");
    RestClientBuilder restClientBuilder= RestClient.builder(httpHost);
    RestHighLevelClient restHighLevelClient=new RestHighLevelClient(restClientBuilder);
    //封装查询请求
    SearchRequest searchRequest=new SearchRequest("course");
    searchRequest.types("doc");
    SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();//query
    BoolQueryBuilder boolQueryBuilder=new BoolQueryBuilder();     //bool

    TermQueryBuilder termQueryBuilder=QueryBuilders.termQuery("lab","php");//term
    boolQueryBuilder.filter(termQueryBuilder);  //filter
    searchSourceBuilder.query(boolQueryBuilder);

    searchRequest.source(searchSourceBuilder);
    //获取执行结果
    try {
        SearchResponse  searchResponse= restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        SearchHits searchHits=searchResponse.getHits();
        System.out.println("total---------------------bool>"+searchHits.getTotalHits());
        SearchHit[] hits=searchHits.getHits();
        for (SearchHit hit:hits){
            String sourseAsString=hit.getSourceAsString();
            System.out.println(sourseAsString);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }finally {
        try {
            if (restHighLevelClient != null)
                restHighLevelClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

    /**
     * 测试bool查询
     */
    public static void boolSelect(){
    //连接rest接口
    HttpHost httpHost=new HttpHost("127.0.0.1",9200,"http");
    RestClientBuilder restClientBuilder= RestClient.builder(httpHost);
    RestHighLevelClient restHighLevelClient=new RestHighLevelClient(restClientBuilder);
    //封装查询请求
    SearchRequest searchRequest=new SearchRequest("course");
    searchRequest.types("doc");
    SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
    BoolQueryBuilder boolQueryBuilder=new BoolQueryBuilder();


    MatchQueryBuilder matchQueryBuilder= QueryBuilders.matchQuery("name","计算机");
    TermQueryBuilder termQueryBuilder=QueryBuilders.termQuery("lab","php");
    boolQueryBuilder.must(matchQueryBuilder);
    boolQueryBuilder.must(termQueryBuilder);
    searchSourceBuilder.query(boolQueryBuilder);

    searchRequest.source(searchSourceBuilder);
    //获取执行结果
    try {
        SearchResponse  searchResponse= restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        SearchHits searchHits=searchResponse.getHits();
        System.out.println("total---------------------bool>"+searchHits.getTotalHits());
        SearchHit[] hits=searchHits.getHits();
        for (SearchHit hit:hits){
            String sourseAsString=hit.getSourceAsString();
            System.out.println(sourseAsString);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }finally {
        try {
            if (restHighLevelClient != null)
                restHighLevelClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

    /**
     * 测试普通查询
     */
    public static void select() {
        //连接rest接口
        HttpHost httpHost=new HttpHost("127.0.0.1",9200,"http");
        RestClientBuilder restClientBuilder= RestClient.builder(httpHost);
        RestHighLevelClient restHighLevelClient=new RestHighLevelClient(restClientBuilder);
        //封装查询请求
        SearchRequest searchRequest=new SearchRequest("courtest");
        searchRequest.types("doc");
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        MatchQueryBuilder matchQueryBuilder= QueryBuilders.matchQuery("name","华为");
        searchSourceBuilder.query(matchQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        //获取执行结果
        try {
             SearchResponse  searchResponse= restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
             SearchHits searchHits=searchResponse.getHits();
             System.out.println("total--------------------->"+searchHits.getTotalHits());
            SearchHit[] hits=searchHits.getHits();
            for (SearchHit hit:hits){
                String sourseAsString=hit.getSourceAsString();
                System.out.println(sourseAsString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 测试添加
     */
    public static void testAdd(){
        //连接rest接口
        HttpHost httpHost=new HttpHost("127.0.0.1",9200,"http");
        RestClientBuilder restClientBuilder= RestClient.builder(httpHost);
        RestHighLevelClient restHighLevelClient=new RestHighLevelClient(restClientBuilder);
        //封装请求对象
        BulkRequest bulkRequest=new BulkRequest();


            IndexRequest indexRequest = new IndexRequest("course", "doc", "021021");
            Map map = new HashMap();

        Gson gson=new Gson();
        User user=new User();
        user.setName("csm");
        user.setEmail("2793219495@qq.com");
        user.setPassword("123");
        user.setType("public");
//
//        String json=gson.toJson(user);
//        System.out.println(json);
            //map.put("name","华为123");
            map.put("subject_name", "testNew");
            map.put("subject_url", "testNew");
            map.put("image_url", "testNew");
          // map.put("infos", json);
             map.put("infos", user);
        String mapjson=gson.toJson(map);
        System.out.println(mapjson);
            indexRequest.source(mapjson, XContentType.JSON);
            bulkRequest.add(indexRequest);


        //获取执行结果
        //IndexResponse indexResponse= null;
        BulkResponse bulkResponse=null;
        try {
           // indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            int s= bulkResponse.status().getStatus();
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }


   }
}
