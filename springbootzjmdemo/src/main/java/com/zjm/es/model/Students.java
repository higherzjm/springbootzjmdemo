package com.zjm.es.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author zhujianming
 * @description
 * @date 2022/4/10 14:05
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName ="studentsinfo2",type = "_doc")
public  class Students {
    private String id;
    private String name;
    private Integer age;
}
