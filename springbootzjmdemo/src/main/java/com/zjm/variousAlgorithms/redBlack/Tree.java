package com.zjm.variousAlgorithms.redBlack;

import java.util.List;

public interface Tree<T,E> {

    /**
     * 构建树
     * @param dataList
     */
     void createTree(List<T> dataList);

    /**
     * 添加节点
     * @param data
     */
    E addNode(E tree, T data);


    /**
     * 删除节点
     * @param tree
     * @param node
     */
    void deleteNode(E tree, E node);

    /**
     * 前序遍历:根节点->左节点->右节点
     */
    void preOrder(List<T> list, E node);

    /**
     * 中序遍历：左节点->根节点->右节点
     * @return
     */
    void inOrder(List<T> list, E node);

    /**
     * 后序遍历：左节点->右节点->根节点
     */
    void laOrder(List<T> list, E node);

    /**
     * 广度优先遍历：层序遍历
     * @param list
     * @param node
     */
    void bfs(List<T> list, E node);

}
