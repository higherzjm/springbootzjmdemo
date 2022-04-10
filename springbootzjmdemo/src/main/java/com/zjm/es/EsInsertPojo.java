package com.zjm.es;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhujianming
 * @description
 * @date 2022/4/10 14:05
 */
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
