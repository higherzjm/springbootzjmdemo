package com.zjm.springframework.cyclicdependence;

public interface ObjectFactory<T> {
    T getObject();
}
