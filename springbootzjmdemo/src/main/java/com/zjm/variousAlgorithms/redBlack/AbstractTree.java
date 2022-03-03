package com.zjm.variousAlgorithms.redBlack;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractTree<T, E> implements Tree<T, E>, Serializable {

    private static final long serialVersionUID = -8046156919125106629L;

    /**
     * 根节点
     */
    E root;

    @Override
    public void createTree(List<T> dataList) {
        for (T data : dataList){
            addNode(root, data);
        }
    }

    void addNode(T data){

    };


}

