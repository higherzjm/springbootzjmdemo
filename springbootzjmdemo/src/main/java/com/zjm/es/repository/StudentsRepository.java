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
    Page<Students> findByName(String name, Pageable pageable);
}
