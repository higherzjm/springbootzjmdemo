package com.zjm.variousAlgorithms.redBlack;

import java.util.LinkedList;
import java.util.List;

/**
 * Title: 红黑树
 * Description:
 * 规则：
 * 1.每个节点不是红色就是黑色
 * 2.每个根节点是黑色
 * 3.每个叶子节点就是黑色的空节点
 * 4.如果一个节点是红色的，则它的子节点必须是黑色的(父子不能同为红)
 * 5.平衡的关键字：从任一节点到其每个叶子的所有路径都包含相同的黑色的节点
 * 6.新插入节点默认为红色，插入后需要校验红黑树是否符合规则，不符合则需要进行平衡
 *
 * 再平衡涉及到：左旋、右旋、颜色反转
 *
 * 红黑树插入分为五种情况：
 *
 * 1.新节点(A)位于树根，没有父节点
 *  直接让新节点变成黑色，规则二得到满足，同时，黑色的根节点使得每条路径上的黑色节点数目都增加1，所以
 *  并没有打破规则5
 *     A(红)  ->   A(黑)
 *   1  2        1  2
 *
 *
 * 2.新节点(B)的父节点是黑色
 *   新插入的红色结点B并没有打破红黑树的规则，所以不需要做任何调整
 *          A(黑)
 *       B(红)  3
 *      1  2
 *
 *
 * 3.新节点(D)的父节点和叔叔节点都是红色
 *            A(黑)               A(黑)                   A(红)
 *       B(红)    C(红)  ->   B(黑)    C(红)  -> ... ->B(黑)   c(黑)
 *      D(红) 3  4  5        D(红)                   D(红)
 *     1  2
 *经过上面的调整，这一局部重新符合了红黑树的规则
 * 4.新节点(D)的父节点是红色，叔叔节点是黑色或者没有叔叔，且新节点是父节点的右孩子，父节点(B)是祖父节点的左孩子
 * 我们以节点B为轴，做一次左旋，使得节点D成为父节点，原来的父节点B成为D的左孩子
 *       A(黑)                 A(黑)
 *    B(红)   C(黑)  ->   D(红)   C(黑)
 *   1  D(红) 4  5      B(红) 3  4  5
 *     2  3           1  2
 *
 * 5.新节点(D)的父节点是红色，叔叔节点是黑色或者没有叔叔，且新节点是父节点的左孩子，父节点（B）
 *  是祖父节点的左孩子
 *  我们以节点A为抽，做一次右旋转，使得节点B成为祖父节点，节点A成为节点B的右孩子
 *         A(黑)                 B(红)                   B(黑)
 *      B(红)  C(黑)  ->      D(红)  A(黑)     ->    D(红)   A(红)
 *    D  3   4   5         1    2  3  C(黑)        1   2  3   C(黑)
 *  1  2                             4  5                    4  5
 *
 * 颜色反转：
 * 如果当前节点、父节点、叔叔节点同为红色，这种情况违反了红黑树的规则，需求将红色向祖辈上传，
 * 父节点和叔叔节点变为黑色，爷爷节点变为黑->红色
 *
 * 左旋：逆时针旋转红黑树的两个节点，使得父节点被自己的右孩子取代，而自己成为自己的左孩子
 *
 *
 * 右旋：顺时针旋转红黑树的两个节点，使得父节点被自己的左孩子取代，而自己成为自己的右孩子
 *
 * 时间负责度：logn
 * @version 1.0
 * @author: weijie
 * @date: 2020/10/19 17:39
 */
public class RedBlackTree extends AbstractTree<Integer, RedBlackNode> {


    @Override
    public void createTree(List<Integer> dataList) {
        for (Integer data : dataList){
            addNode(data);
        }
    }

    @Override
    public void addNode(Integer data) {
        RedBlackNode node = new RedBlackNode(data);
        if (root == null){
            //根为黑色
            node.setBlack(true);
            root = node;
            return ;
        }
        RedBlackNode parent = root;
        RedBlackNode son = null;
        /**
         * 判断新节点是放在左子树还是右子树
         */
        if (data <= parent.getData()){
            son = parent.getLeft();
        }else {
            son = parent.getRight();
        }
        /**
         * 对树深度遍历，寻找新节点存放的位置
         */
        while (son != null){
            parent = son;
            if (data <= parent.getData()){
                son = parent.getLeft();
            }else {
                son = parent.getRight();
            }
        }
        /**
         * 节点插入
         */
        if (data <= parent.getData()){
            parent.setLeft(node);
        }else {
            parent.setRight(node);
        }
        node.setParent(parent);
        /**
         * 自平衡
         */
        balance(node);
    }

    @Override
    public RedBlackNode addNode(RedBlackNode tree, Integer data) {
        return null;
    }

    /**
     * 自平衡
     * @param node
     */
    private void balance(RedBlackNode node){
        RedBlackNode father;
        RedBlackNode grandFather;
        /**
         * 获取父节点并判断父节点是否为红色节点，规则：父子不同为红
         */
        while ((father = node.getParent()) != null && father.isBlack() == false){
            //获取祖父节点
            grandFather = father.getParent();
            //判断父节点在祖先节点存在的位置
            if (grandFather.getLeft() == father){
                //叔叔节点
                RedBlackNode uncle = grandFather.getRight();
                //如果父亲、叔叔节点存在且都为红，则父亲、叔叔节点变为黑色
                if (uncle != null && uncle.isBlack() == false){
                    father.setBlack(true);
                    uncle.setBlack(true);
                    grandFather.setBlack(false);
                    //接着对祖先节点进行颜色反转
                    node = grandFather;
                    continue;
                }
                /**
                 * 如果没有触发颜色反转，需要进行左旋、右旋操作
                 */
                if (node == father.getRight()){
                    //左旋
                    leftRotate(father);
                    RedBlackNode temp = node;
                    node = father;
                    father = temp;
                }
                father.setBlack(true);
                grandFather.setBlack(false);
                rightRotate(grandFather);
            }else {
                RedBlackNode uncle = grandFather.getLeft();
                if (uncle != null && uncle.isBlack() == false){
                    father.setBlack(true);
                    uncle.setBlack(true);
                    grandFather.setBlack(false);
                    node = grandFather;
                    continue;
                }
                if (node == father.getLeft()){
                    rightRotate(father);
                    RedBlackNode temp = node;
                    node = father;
                    father = temp;
                }
                father.setBlack(true);
                grandFather.setBlack(false);
                leftRotate(grandFather);
            }
        }
        root.setBlack(true);
    }

    public void leftRotate(RedBlackNode node){
        RedBlackNode right = node.getRight();
        RedBlackNode parent = node.getParent();
        if (parent == null){
            root = right;
            right.setParent(null);
        }else {
            if (parent.getLeft() != null && parent.getLeft() == node){
                parent.setLeft(right);
            }else {
                parent.setRight(right);
            }
            right.setParent(parent);
        }
        node.setParent(right);
        node.setRight(right.getLeft());
        if (right.getLeft() != null){
            right.getLeft().setParent(node);
        }
        right.setLeft(node);
    }

    private void rightRotate(RedBlackNode node){
        RedBlackNode left = node.getLeft();
        RedBlackNode parent = node.getParent();

        if (parent == null){
            root = left;
            left.setParent(null);
        }else {
            if (parent.getLeft() != null && parent.getLeft() == node){
                parent.setLeft(left);
            }else {
                parent.setRight(left);
            }
            left.setParent(left);
        }
        node.setParent(left);
        node.setLeft(left.getRight());
        if (left.getRight() != null){
            left.getRight().setParent(node);
        }
        left.setRight(node);
    }

    @Override
    public void deleteNode(RedBlackNode root, RedBlackNode node) {

    }


    @Override
    public void preOrder(List<Integer> showList, RedBlackNode node) {
        if(node == null) {
            return ;
        }
        //叶子
        if(node.getLeft() == null && node.getRight()==null){
            showList.add(node.getData());
            return ;
        }
        showList.add(node.getData());
        //递归 左孩子
        preOrder(showList, node.getLeft());
        //递归 右孩子
        preOrder(showList, node.getRight());
    }

    @Override
    public void inOrder(List<Integer> showList, RedBlackNode node) {
        if(node == null) {
            return ;
        }
        //叶子
        if(node.getLeft() == null && node.getRight()==null){
            showList.add(node.getData());
            return ;
        }
        //递归 左孩子
        inOrder(showList, node.getLeft());
        showList.add(node.getData());
        //递归 右孩子
        inOrder(showList, node.getRight());
    }

    @Override
    public void laOrder(List<Integer> showList, RedBlackNode node) {
        if(node == null) {
            return ;
        }
        //叶子
        if(node.getLeft() == null && node.getRight()==null){
            showList.add(node.getData());
            return ;
        }
        //递归 左孩子
        laOrder(showList, node.getLeft());
        //递归 右孩子
        laOrder(showList, node.getRight());
        showList.add(node.getData());
    }

    @Override
    public void bfs(List<Integer> list, RedBlackNode node) {
        if (node == null){
            return;
        }
        LinkedList<RedBlackNode> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()){
            RedBlackNode child = queue.poll();
            list.add(child.data);
            if (child.left != null){
                queue.offer(child.left);
            }
            if (child.right != null){
                queue.offer(child.right);
            }
        }
    }
}

