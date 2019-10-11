package com.rainyalley.practice.algorithm.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 1. 若它的左子树不空，则左子树上所有结点的值均小于它的根结点的值；

 2. 若它的右子树不空，则右子树上所有结点的值均大于它的根结点的值；

 3. 它的左、右子树也分别为二叉排序树。
 */
public class BinarySearchTree<E extends Comparable<? super E>> {

    protected Node<E> root;

    public BinarySearchTree(E value){
        this.root = newNode(value);
    }


    /**
     * <p>1.若为是空树，则搜索失败，否则：
     *
     * <p>2.若key等于根节点的数据域之值，则查找成功；否则：
     *
     * <p>3.若key小于根节点的数据域之值，则搜索左子树；否则：
     *
     * <p>4.查找右子树。</p>
     *
     * @param key 期望子树的数据域之值
     * @return contains or not
     */
    private boolean contains(E key, Node<E> node){

        if(node == null){
            return false;
        }

        if(node.getValue().compareTo(key) == 0){
            return true;
        }

        if(node.getValue().compareTo(key) > 0){
            return contains(key, node.getLeftChild());
        } else {
            return contains(key, node.getRightChild());
        }
    }

    public final boolean contains(E key){return contains(key,root);}

    /**
     * 若树中已存在相等的值，则插入失败
     * 若插入成功，则返回新子树的引用，否则返回 null
     * @param key 期望插入的子树的数据域之值
     * @return success or not
     */
    protected final Node<E> insert(E key, Node<E> node){

        if(node == null){
            root = newNode(key);
            return root;
        }

        E value = node.getValue();

        if(!value.getClass().equals(key.getClass())){
            throw new IllegalArgumentException("discrepant type");
        }

        if(value.compareTo(key) == 0){
            return null;
        }

        Node<E> newNode;

        if(value.compareTo(key) > 0){
            Node<E> leftChild = node.getLeftChild();
            if(leftChild == null){
                newNode = newNode(key);
                node.setLeftChild(newNode);
            } else {
                return insert(key, leftChild);
            }
        } else {
            Node<E> rightChild = node.getRightChild();
            if(rightChild == null){
                newNode = newNode(key);
                node.setRightChild(newNode);
            } else {
                return insert(key, rightChild);
            }
        }

        return newNode;
    }

    /**
     * 插入节点
     * @param key 节点值
     * @return
     */
    public boolean insert(E key){return insert(key, root) != null;}

    protected Node<E> newNode(E key){
        return new Node<E>(key);
    }

    /**
     * 删除节点
     * @param key
     * @param node
     * @return
     */
    protected final Node<E> delete(E key, Node<E> node){
        if(node == null){
            return null;
        }

        if(node.getValue().compareTo(key) == 0) {
            Node<E> deleted = node;
            //本身是叶子，直接删除
            if (node.isLeaf()) {
                replace(node, null);
            //只有右子树，直接让右子树代替自身
            } else if (node.getLeftChild() == null) {
                replace(node, node.getRightChild());
            //只有左子树，直接让左子树代替自身
            } else if (node.getRightChild() == null) {
                replace(node, node.getLeftChild());
            //左右子树都有，用直接前驱的值替换自己的值，然后删除直接前驱
            } else {
                Node<E> predecessor = node.getLeftChild();
                while (predecessor.getRightChild() != null){
                    predecessor = predecessor.getRightChild();
                }
                node.setValue(predecessor.getValue());
                replace(predecessor, predecessor.getLeftChild());
                deleted = predecessor;
            }
            return deleted;
        } else if(node.getValue().compareTo(key) > 0){
            return delete(key, node.getLeftChild());
        } else {
            return delete(key, node.getRightChild());
        }
    }

    /**
     * 删除节点
     * @param key 节点值
     * @return
     */
    public boolean delete(E key){return delete(key, root) != null;}

    /**
     * 节点替换
     * @param origin 原始节点
     * @param fresh 新节点
     */
    protected final void replace(Node<E> origin, Node<E> fresh){
        if(Direction.LEFT.equals(origin.getDirection())){
            origin.getParent().setLeftChild(fresh);
        } else if(Direction.RIGHT.equals(origin.getDirection())){
            origin.getParent().setRightChild(fresh);
        } else if(origin.getParent() == null) {
            root = fresh;
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * 中序遍历
     * @param list 存放有序结果的容器
     * @return 存放有序结果的容器，填充遍历结果
     */
    private List<E> inorderTraversal(List<E> list, Node<E> node){

        if(node == null){
            return list;
        }

        inorderTraversal(list, node.getLeftChild());

        list.add(node.getValue());

        inorderTraversal(list, node.getRightChild());

        return list;
    }

    /**
     * 中序遍历
     * @return
     */
    public final List<E> inorderTraversal(){
        return inorderTraversal(new ArrayList<>(),root);
    }


    //extra ~~

    private int leftDepth(Node<E> node){
        if(node.getLeftChild() == null){
            return 1;
        }
        return leftDepth(node.getLeftChild()) + 1;
    }

    private int rightDepth(Node<E> node){
        if(node.getRightChild() == null){
            return 1;
        }
        return rightDepth(node.getRightChild()) + 1;
    }

    private void depthDiff(Map<Node<E>, Integer> map, Node<E> node){
        int diff =leftDepth(node) - rightDepth(node);
        map.put(node, diff);
        if(node.getLeftChild() != null){
            depthDiff(map, node.getLeftChild());
        }
        if(node.getRightChild() != null){
            depthDiff(map, node.getRightChild());
        }
    }

    public Map<Node<E>, Integer> depthDiff(){
        Map<Node<E>,Integer> map = new HashMap<>();
        depthDiff(map, root);
        return map;
    }

}


