package com.zjm.variousAlgorithms.redBlack;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
/**
 * @description 红黑树测试
 * @date 2022/3/3 10:35
 */
public class RedBlackTreeTest {

    RedBlackTree RBtree = new RedBlackTree();

    @Before
    public void init() {
        //test commit 2
        List<Integer> list = Arrays.asList(10, 5, 9, 3, 6, 7, 19, 32, 24, 17);
        RBtree.createTree(list);
    }

    @Test
    public void addNode(){

    }

    @Test
    public void deleteNode() {
    }

    @Test
    public void preOrder() {
        List<Integer> actualList = new ArrayList<>();
        RBtree.preOrder(actualList, RBtree.root);
        List<Integer> expectList = Arrays.asList(9, 5, 3, 6, 7, 19, 10, 17, 32, 24);
        Assertions.assertEquals(expectList, actualList);
    }

    @Test
    public void inOrder() {
        List<Integer> actualList = new ArrayList<>();
        RBtree.inOrder(actualList, RBtree.root);
        List<Integer> expectList = Arrays.asList(3, 5, 6, 7, 9, 10, 17, 19, 24, 32);
        Assertions.assertEquals(expectList, actualList);
    }

    @Test
    public void laOrder() {
        List<Integer> actualList = new ArrayList<>();
        RBtree.laOrder(actualList, RBtree.root);
        List<Integer> expectList = Arrays.asList(3, 7, 6, 5, 17, 10, 24, 32, 19, 9);
        Assertions.assertEquals(expectList, actualList);
    }

    @Test
    public void bfs() {
        List<Integer> actualList = new ArrayList<>();
        RBtree.bfs(actualList, RBtree.root);
        List<Integer> expectList = Arrays.asList(9, 5, 19, 3, 6, 10, 32, 7, 17, 24);
        Assertions.assertEquals(expectList, actualList);
    }

    @Test
    public void leftRotate(){

    }

    @Test
    public void rightRotate(){
        RedBlackNode node5 = new RedBlackNode(5);
        RedBlackNode node3 = new RedBlackNode(3);
        RedBlackNode node8 = new RedBlackNode(8);
        RedBlackNode node7 = new RedBlackNode(7);
        RedBlackNode node9 = new RedBlackNode(9);

        RBtree.root = node5;
        RBtree.root.left = node3;

        node8.left = node7;
        node8.right = node9;

        RBtree.root.right = node8;

        RBtree.leftRotate(RBtree.root);
    }
}
