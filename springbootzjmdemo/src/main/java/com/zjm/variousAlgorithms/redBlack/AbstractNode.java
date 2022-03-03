package com.zjm.variousAlgorithms.redBlack;

import java.io.Serializable;

public abstract class AbstractNode<T, E> implements Node, Serializable {

    private static final long serialVersionUID = -2321782309212147194L;

    /**
     * 数据域
     */
    T data;
    /**
     * 左孩子
     */
    E left;
    /**
     * 右孩子
     */
    E right;



    public AbstractNode() {
    }

    public AbstractNode(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public E getLeft() {
        return left;
    }

    public void setLeft(E left) {
        this.left = left;
    }

    public E getRight() {
        return right;
    }

    public void setRight(E right) {
        this.right = right;
    }
}
