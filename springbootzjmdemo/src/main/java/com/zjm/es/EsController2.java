package com.zjm.es;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zjm.util.JsonUtil;
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
import org.elasticsearch.action.search.SearchType;
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
@RequestMapping("/es2")
@RestController
@Api(tags = "es【elasticsearch】测试二")
@AllArgsConstructor
@Slf4j
public class EsController2 {
    private final RestHighLevelClient restHighLevelClient;
    @GetMapping("/esquery")
    @ApiOperation(value = "es查询")
    public String esquery() {
        Students students2 = Students.builder().build();
        String hitId = queryEsData("苏州街桔子", students2);
        return hitId;
    }
    private String queryEsData(String name, Students students) {

        SearchRequest searchRequest = new SearchRequest().searchType(SearchType.DEFAULT);
        searchRequest.indices("studentsinfo2");
        //查询条件
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.matchQuery("name", name));
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.size(1);
        sourceBuilder.trackTotalHits(true);
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
        log.info("查询结果数量:"+searchHits.length);
        SearchHit hit = searchHits[0];
        Students students2 = JSONObject.parseObject(hit.getSourceAsString(), Students.class);
        BeanUtil.copyProperties(students2,students);
        return hit.getId();

    }

}
