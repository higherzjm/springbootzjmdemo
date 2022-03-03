package com.zjm.variousAlgorithms.redBlack;

public class RedBlackNode extends AbstractNode<Integer, RedBlackNode>  {
    /**
     * 红黑树节点颜色标记
     */
    boolean isBlack;

    /**
     * 红黑树父亲节点
     */
    RedBlackNode parent;

    public RedBlackNode(Integer data) {
        super(data);
        //默认为红色
        this.isBlack = false;
    }

    public boolean isBlack() {
        return isBlack;
    }

    public void setBlack(boolean black) {
        isBlack = black;
    }

    public RedBlackNode getParent() {
        return parent;
    }

    public void setParent(RedBlackNode parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "RedBlackNode{" +
                "isBlack=" + isBlack +
                ", data=" + data +
                '}';
    }
}

