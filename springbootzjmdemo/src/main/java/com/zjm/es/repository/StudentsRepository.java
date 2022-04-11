package com.zjm.es.repository;

import com.zjm.es.model.Students;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author zhujianming
 * @description
 * @date 2022/4/10 14:54
 */
public interface StudentsRepository extends ElasticsearchRepository<Students,String> {
    /**
     * @description 未做分词查询
     * @date 2022/4/11 10:02
     */
    Page<Students> findByName(String name, Pageable pageable);
    /**
     * @description 有做分词查询
     * @date 2022/4/11 10:02
     */
    Page<Students> findByNameLike(String name, Pageable pageable);
}
