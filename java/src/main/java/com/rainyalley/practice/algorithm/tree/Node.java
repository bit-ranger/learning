package com.rainyalley.practice.algorithm.tree;

/**
 * Created by sllx on 6/27/15.
 */
public class Node<E extends Comparable<? super E>>{

    /**
     * 二叉查找树的数据域之值,不可为null
     */
    private E value;

    /**
     * 左子
     */
    private Node<E> leftChild;

    /**
     * 右子
     */
    private Node<E> rightChild;

    /**
     * 双亲
     */
    private Node<E> parent;

    /**
     * 方向
     */
    private Direction direction;


    public Node(E value) {
        this(value, null);
    }

    public Node(E value, Node<E> parent) {
        this.value = value;
        this.parent = parent;

        if (this.value == null) {
            throw new IllegalArgumentException("value can not be null");
        }
    }

    /**
     * 断言属于叶子节点
     * @return
     */
    public boolean isLeaf() {
        return leftChild == null && rightChild == null;
    }

    /**
     * 设置左子节点
     * @param leftChild
     */
    public void setLeftChild(Node<E> leftChild) {
        this.leftChild = leftChild;
        if(leftChild == null){
            return;
        }
        leftChild.direction = Direction.LEFT;
        leftChild.parent = this;
    }

    /**
     * 设置右子节点
     * @param rightChild
     */
    public void setRightChild(Node<E> rightChild) {
        this.rightChild = rightChild;
        if(rightChild == null){
            return;
        }
        rightChild.direction = Direction.RIGHT;
        rightChild.parent = this;
    }


    public Node<E> getLeftChild() {
        return leftChild;
    }

    public Node<E> getRightChild() {
        return rightChild;
    }

    /**
     * 设置节点值
     * @param value
     */
    public void setValue(E value) {
        this.value = value;
    }

    public E getValue() {
        return value;
    }


    public Node<E> getParent() {
        return parent;
    }

    /**
     * 设置父节点
     * @return
     */
    public void setParent(Node<E> parent) {
        this.parent = parent;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}