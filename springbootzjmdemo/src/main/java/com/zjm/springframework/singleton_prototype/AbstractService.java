package com.zjm.springframework.singleton_prototype;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author zhujianming
 * @description
 * @date 2022/3/19 13:59
 */
@Slf4j
public abstract class AbstractService {
    List<String> data = new ArrayList<>();

    public void say() {

        data.add(IntStream.rangeClosed(1, 1000000)

                .mapToObj(__ -> "a")

                .collect(Collectors.joining("")) + UUID.randomUUID().toString());

        log.info("I'm {} size:{}", this, data.size());

    }
}
