package com.zjm.es;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.zjm.es.model.Students;
import com.zjm.es.repository.StudentsRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;


/**
 * @author zhujianming
 * @description
 * @date 2022/4/5 15:19
 */
@RequestMapping("/es")
@RestController
@Api(tags = "es【elasticsearch】测试")
@AllArgsConstructor
@Slf4j
public class EsController {
    private final RestHighLevelClient restHighLevelClient;
    private final StudentsRepository studentsRepository;

    @GetMapping("/esSave")
    @ApiOperation(value = "es保存")
    public String esSave() {
        Students students1 = Students.builder().name("李四").age(31).id("id012").build();
        EsInsertPojo pojo = EsInsertPojo.builder().indexName("studentsinfo2").data(students1).build();

        IndexRequest request = new IndexRequest(pojo.getIndexName());
        if (pojo.getId() != null) {
            request.id(pojo.getId());
        }
        if (pojo.getRouteId() != null) {
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

    @GetMapping("/esquery")
    @ApiOperation(value = "es查询")
    public List<Students> esquery() {
        return queryEsData("苏州街桔子");
    }

    private List<Students> queryEsData(String name) {

        SearchRequest searchRequest = new SearchRequest().searchType(SearchType.DEFAULT);
        searchRequest.indices("studentsinfo2");
        //查询条件
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.matchQuery("name", name));
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(boolQueryBuilder);
        //各种组合条件
        searchRequest.source(sourceBuilder);
        log.info("查询命令:" + searchRequest.source().toString());
        SearchResponse response = null;
        try {
            response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits.getHits();
        log.info("查询结果数量:" + searchHits.length);
        List<Students> studentsList = Lists.newArrayList();
        for (SearchHit hit : searchHits) {
            Students students = JSONObject.parseObject(hit.getSourceAsString(), Students.class);
            studentsList.add(students);
        }

        return studentsList;

    }

    @GetMapping("/esSpringQuery")
    @ApiOperation(value = "es spring 集成查询")
    public List<Students> esSpringQuery(@RequestParam(defaultValue = "苏州街桔子") String name, @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {

        Pageable pageable= PageRequest.of(pageNo,pageSize);
       Page<Students> pageData=studentsRepository.findByName(name,pageable);
        return pageData!=null?pageData.getContent():null;
    }
}
