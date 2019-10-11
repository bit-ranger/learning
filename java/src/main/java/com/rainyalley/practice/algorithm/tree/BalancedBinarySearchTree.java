package com.rainyalley.practice.algorithm.tree;


public class BalancedBinarySearchTree<E extends Comparable<? super E>> extends BinarySearchTree<E>{

    private boolean ascend;

    public BalancedBinarySearchTree(E value) {
        super(value);
    }


    public boolean insert(E key) {
        BalancedNode<E> newNode = (BalancedNode<E>)super.insert(key, root);

        if(newNode == null) {
            return false;
        }

        ascend = true;

        makeBalance(newNode);

        return true;
    }

    @Override
    public boolean delete(E key) {
        BalancedNode<E> deletedNode = (BalancedNode<E>)super.delete(key, root);

        if(deletedNode == null){
            return false;
        }

        ascend = true;

        reMakeBalance(deletedNode);

        return true;
    }

    private void reMakeBalance(BalancedNode<E> deletedNode){

        BalancedNode<E> parent = deletedNode.getParent();

        //root
        if(parent == null){
            return;
        }

        switch (parent.getBalance()){
            case EH: {
                if(isLeft(deletedNode)){
                    parent.setBalance(Balance.RH);
                } else if(isRight(deletedNode)){
                    parent.setBalance(Balance.LH);
                } else {
                    throw new IllegalStateException();
                }
                break;
            }
            case LH: {
                if(isLeft(deletedNode)){
                    parent.setBalance(Balance.EH);
                    reMakeBalance(parent);
                } else if(isRight(deletedNode)){
                    makeBalance(parent.getLeftChild());
                } else {
                    throw new IllegalStateException();
                }
                break;
            }
            case RH: {
                if(isLeft(deletedNode)){
                    makeBalance(parent.getRightChild());
                } else if (isRight(deletedNode)){
                    parent.setBalance(Balance.EH);
                    reMakeBalance(parent);
                } else {
                    throw new IllegalStateException();
                }
                break;
            }
        }
    }

    private void makeBalance(BalancedNode<E> node){

        if(!ascend){
            return;
        }

        //root
        if(node.getParent() == null){
            return;
        }

        if(isLeft(node)){
            makeBalanceForLeft(node);
        } else if(isRight(node)){
            makeBalanceForRight(node);
        }

        makeBalance(node.getParent());
    }

    private void makeBalanceForLeft(BalancedNode<E> node){

        if(!isLeft(node)){
            throw new IllegalArgumentException("node must be left child");
        }

        BalancedNode<E> parent = node.getParent();

        switch (parent.getBalance()){
            case EH: {
                parent.setBalance(Balance.LH);
                ascend = true;
                break;
            }
            case RH: {
                parent.setBalance(Balance.EH);
                ascend = false;
                break;
            }
            case LH: {
                //因为当前节点是左节点，而父节点原本就左高，这表明以父节点为根的树不再平衡。

                //通过当前节点以及子节点的平衡因子，可以判断目前的不平衡形态。
                switch (node.getBalance()) {
                    case EH: {
                        // 悖论状态，
                        // 情形一：node即为新节点，然而此时父节点不可能左高。
                        // 情形二：node拥有一颗平衡子树，然而此情形下，新节点没有破坏平衡，needCheck应该为false, 程序不应该执行到此处
                        //throw new IllegalStateException();
                        //情形三: node处于平衡状态，parent也处于平衡状态，当node的兄弟节点被删除时，将会出现此状态
                        rightRotate(parent);
                        node.setBalance(Balance.RH);
                        break;
                    }
                    case LH: {
                        //新节点在当前节点的左子树上, 直接右旋
                        rightRotate(parent);

                        node.setBalance(Balance.EH);
                        parent.setBalance(Balance.EH);
                        break;
                    }
                    case RH: {
                        BalancedNode<E> rightChild = node.getRightChild();

                        //新节点在当前节点的右子树上, 先左旋再右旋
                        leftRightRotate(parent);

                        //根据右子树的挂件判断旋转后的平衡状态
                        switch (rightChild.getBalance()) {
                            case LH: {
                                parent.setBalance(Balance.RH);
                                node.setBalance(Balance.EH);
                                rightChild.setBalance(Balance.EH);
                                break;
                            }
                            case RH: {
                                parent.setBalance(Balance.EH);
                                node.setBalance(Balance.LH);
                                rightChild.setBalance(Balance.EH);
                                break;
                            }
                            case EH: {
                                //rightChild is leaf
                                parent.setBalance(Balance.EH);
                                node.setBalance(Balance.EH);
                                break;
                            }
                        }
                        break;
                    }
                }
                ascend = false;
                break;
            }
        }
    }

    private void makeBalanceForRight(BalancedNode<E> node){

        if(!isRight(node)){
            throw new IllegalArgumentException("node must be right child");
        }

        BalancedNode<E> parent = node.getParent();

        switch (parent.getBalance()){
            case EH: {
                parent.setBalance(Balance.RH);
                ascend = true;
                break;
            }
            case LH: {
                parent.setBalance(Balance.EH);
                ascend = false;
                break;
            }
            case RH: {
                //因为当前节点是右节点，而父节点原本就右高，这表明以父节点为根的树不再平衡。

                //通过当前节点以及子节点的平衡因子，可以判断目前的不平衡形态。
                switch (node.getBalance()) {
                    case EH: {
                        // 悖论状态，
                        // 情形一：node即为新节点，然而此时父节点不可能左高。
                        // 情形二：node拥有一颗平衡子树，然而此情形下，新节点没有破坏平衡，needCheck应该为false, 程序不应该执行到此处
                        //throw new IllegalStateException();
                        //情形三: node处于平衡状态，parent也处于平衡状态，当node的兄弟节点被删除时，将会出现此状态
                        leftRotate(parent);
                        node.setBalance(Balance.LH);
                        break;
                    }
                    case RH: {
                        //新节点在当前节点的右子树上, 直接左旋
                        leftRotate(parent);
                        node.setBalance(Balance.EH);
                        parent.setBalance(Balance.EH);
                        break;
                    }
                    case LH: {
                        BalancedNode<E> leftChild = node.getLeftChild();

                        //新节点在当前节点的左子树上, 先右子再左旋
                        rightLeftRotate(parent);

                        //根据左子树的挂件判断旋转后的平衡状态
                        switch (leftChild.getBalance()) {
                            case LH: {
                                parent.setBalance(Balance.EH);
                                node.setBalance(Balance.RH);
                                leftChild.setBalance(Balance.EH);
                                break;
                            }
                            case RH: {
                                parent.setBalance(Balance.LH);
                                node.setBalance(Balance.EH);
                                leftChild.setBalance(Balance.EH);
                                break;
                            }
                            case EH: {
                                //leftChild is leaf
                                parent.setBalance(Balance.EH);
                                node.setBalance(Balance.EH);
                                break;
                            }
                        }
                        break;
                    }
                }
                ascend = false;
                break;
            }

        }
    }

    /**
     * <p>对以node为根的子树进行单右旋处理
     *
     * <pre>{@code
     *      100 (node)                    80
     *      /                            /  \
     *     80             >>>>>         60  100
     *    /
     *   60
     * }
     */
    private void rightRotate(Node<E> node){

        Node<E> center = node.getLeftChild();

        if(node.getParent() == null){
            center.setParent(null);
            root = center;
        } else if(isLeft(node)){
            node.getParent().setLeftChild(center);
        }else {
            node.getParent().setRightChild(center);
        }

        node.setLeftChild(center.getRightChild());
        center.setRightChild(node);
    }

    /**
     * 对以node为根的子树进行单左旋处理
     *
     * <pre>{@code
     *      100 (node)                       110
     *        \                             /  \
     *        110            >>>>>        100   120
     *          \
     *          120
     * }
     */
    private void leftRotate(Node<E> node){

        Node<E> center = node.getRightChild();

        if(node.getParent() == null){
            center.setParent(null);
            root = center;
        } else if(isLeft(node)){
            node.getParent().setLeftChild(center);
        }else {
            node.getParent().setRightChild(center);
        }

        node.setRightChild(center.getLeftChild());
        center.setLeftChild(node);
    }

    /**
     * 对以node为根的子树进行处理
     *
     * <pre>{@code
     *      110 (node)                     105
     *      /                              /  \
     *   100             >>>>>           100   110
     *     \
     *     105
     * }
     */
    private void leftRightRotate(Node<E> node){
        leftRotate(node.getLeftChild());
        rightRotate(node);
    }

    /**
     * 对以node为根的子树进行处理
     *
     * <pre>{@code
     *      100 (node)                       105
     *        \                             /  \
     *        110            >>>>>        100   110
     *        /
     *      105
     * }
     */
    private void rightLeftRotate(Node<E> node){
        rightRotate(node.getRightChild());
        leftRotate(node);
    }

    private boolean isLeft(Node<E> node){
        return Direction.LEFT.equals(node.getDirection());
    }

    private boolean isRight(Node<E> node){
        return Direction.RIGHT.equals(node.getDirection());
    }

    @Override
    protected BalancedNode<E> newNode(E key) {
        return new BalancedNode<>(key);
    }

    private static class BalancedNode<E extends Comparable<? super E>> extends Node<E>{

        private Balance balance = Balance.EH;

        public BalancedNode(E value) {
            super(value);
        }

        public Balance getBalance() {
            return balance;
        }

        public void setBalance(Balance balance) {
            this.balance = balance;
        }

        @Override
        public BalancedNode<E> getParent() {
            return (BalancedNode<E>)super.getParent();
        }

        @Override
        public BalancedNode<E> getLeftChild() {
            return (BalancedNode<E>)super.getLeftChild();
        }

        @Override
        public BalancedNode<E> getRightChild() {
            return (BalancedNode<E>)super.getRightChild();
        }

        @Override
        public String toString() {
            return super.toString() + "," + balance;
        }
    }

    private static enum Balance {
        LH, //左边更高
        EH, //两边等高
        RH  //右边更高
    }

    //extra ~~

    /**
     * N层平衡二叉树至少多少个结点
     * @param depth N
     */
    public static int leastNodes(int depth){
        if(depth == 1){
            return 1;
        } else if(depth == 2){
            return 2;
        }
        return leastNodes(depth - 2) + leastNodes(depth - 1) + 1;
    }


}
