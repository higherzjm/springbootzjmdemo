package com.zjm.es;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;


/**
 * @author zhujianming
 * @description
 * @date 2022/4/5 15:19
 */
@RequestMapping("/es")
@RestController
@Api(tags = "es【elasticsearch】测试一")
@AllArgsConstructor
@Slf4j
public class EsController {
    private final RestHighLevelClient restHighLevelClient;

    @GetMapping("/esquery")
    @ApiOperation(value = "es查询")
    public String esquery() {
        String esId = queryEsData("张三", null);
        //esQuery();
        return esId;
    }

    @GetMapping("/esSave")
    @ApiOperation(value = "es保存")
    public String esSave() {
        Students students1 = Students.builder().name("李四").age(31).id("id012").build();
        EsInsertPojo pojo = EsInsertPojo.builder().indexName("studentsinfo-gitproject2021").data(students1).build();

        IndexRequest request = new IndexRequest(pojo.getIndexName());
        if (pojo.getId()!=null) {
            request.id(pojo.getId());
        }
        if (pojo.getRouteId()!=null) {
            request.routing(pojo.getRouteId());
        }
        request.source(JSON.toJSONString(pojo), XContentType.JSON);
        try {
            IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            log.info("ES创建文档成功:{}", response.status());
        } catch (IOException e) {
            log.error("ES创建文档失败，data:{}", pojo);
        }
        return "保存成功";
    }

    private String queryEsData(String name, Students students) {

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        SearchRequest searchRequest = new SearchRequest("studentsinfo-gitproject2021");
        //searchRequest.indices("studentsinfo-gitproject2021");
        //查询条件
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.matchQuery("name", name));
        sourceBuilder.query(boolQueryBuilder);
        //各种组合条件
        searchRequest.source(sourceBuilder);
        log.info("查询命令:"+searchRequest.source().toString());
        SearchResponse response = null;
        try {
            response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits.getHits();
        SearchHit hit = searchHits[0];
       /* Students students2 = JSONUtil.parse(hit.getSourceAsString(), Students.class);
        BeanUtil.copyProperties(students2,students);*/
        return hit.getId();

    }
    public void esQuery() {

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        SearchRequest searchRequest = new SearchRequest();
        //索引
        searchRequest.indices("studentsinfo");
        //查询条件
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.matchQuery("students.name", "王五"));//map存值方式
        sourceBuilder.query(boolQueryBuilder);
        //各种组合条件
        searchRequest.source(sourceBuilder);
        //请求
        log.info(searchRequest.source().toString());

        //排序+分页
        sourceBuilder.from(0);
        sourceBuilder.size(3);
        sourceBuilder.sort("students.age", SortOrder.DESC);//map存值方式

        searchRequest.source(sourceBuilder);
        SearchResponse response = null;
        try {
            response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits.getHits();
        TotalHits totalHitsCount = hits.getTotalHits();
        for (SearchHit hit : searchHits) {
            String hitId = hit.getId();
            Map<String, Object> hitMap = hit.getSourceAsMap();
            Students vo = JSON.parseObject(JSON.toJSONString(hitMap.get("students")), Students.class);//map存值方式
            log.info("查询结果 vo:{},totalHitsCount:{}", vo, totalHitsCount);
            log.info("hitId:{}", hitId);
        }
    }
}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class Students {
    private String id;
    private String name;
    private Integer age;
}
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
 class EsInsertPojo<T> {

    /**
     * 索引名称
     */
    private String indexName;

    /**
     * es主键
     */
    private String id;

    /**
     * es路由键
     */
    private String routeId;

    private T data;
}