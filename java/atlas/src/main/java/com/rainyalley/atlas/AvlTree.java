package com.rainyalley.atlas;

public class AvlTree {

    private Storage storage;

    public AvlTree(Storage storage) {
        this.storage = storage;
    }

    /**
     * 获取存放key的节点
     * @param root
     * @param key
     * @return
     */
    public AvlTreeNode get(AvlTreeNode root, String key){
        AvlTreeNode curr = root;
        while (curr != null){
            if(curr.getKey().equals(key)){
                return curr;
            } else if(curr.getKey().compareTo(key) > 0){
                if(curr.getLeft() == 0){
                    return null;
                }
                byte[] leftBytes = storage.read(curr.getLeft());
                curr = AvlTreeNode.toNode(leftBytes);
            } else {
                if(curr.getRight() == 0){
                    return null;
                }
                byte[] rightBytes = storage.read(curr.getRight());
                curr = AvlTreeNode.toNode(rightBytes);
            }
        }

        return null;
    }

    /**
     *
     * @param root
     * @param key
     * @param value
     * @return 最新的root节点
     */
    public AvlTreeNode insert(AvlTreeNode root, String key, long value){
        AvlTreeNode curr = root;
        AvlTreeNode newRoot = null;
        while (curr != null){
            if(curr.getKey().equals(key)){
                curr.setValue(value);
                newRoot = root;
                break;
            }
            // 当前节点 > key， 走左路
            else if(curr.getKey().compareTo(key) > 0){
                //没有左子， 新节点作左子
                if(curr.getLeft() == 0){
                    AvlTreeNode newNode = new AvlTreeNode(key, value, curr);
                    writeNode(newNode);
                    curr.setLeft(newNode.getAddress());
                    newRoot = rotate(curr, newNode, findNode(curr, curr.getRight()));
                    break;
                } else {
                    AvlTreeNode leftNode = findNode(curr, curr.getLeft());
                    //左子 < key，新节点作左子
                    if(leftNode.getKey().compareTo(key) < 0){
                        AvlTreeNode newNode = new AvlTreeNode(key, value, curr);
                        newNode.setLeft(leftNode.getAddress());
                        newNode.setHeight(leftNode.getHeight() + 1);
                        writeNode(newNode);
                        curr.setLeft(newNode.getAddress());
                        newRoot = rotate(curr, newNode, findNode(curr, curr.getRight()));
                        break;
                    } else {
                        curr = leftNode;
                    }
                }

            }
            // 当前节点 < key， 走右路
            else {
                //没有右子， 新节点作右子
                if(curr.getRight() == 0){
                    AvlTreeNode newNode = new AvlTreeNode(key, value, curr);
                    long newNodeAddress = storage.write(newNode.toBytes());
                    curr.setRight(newNodeAddress);
                    newRoot = rotate(curr, findNode(curr, curr.getLeft()), newNode);
                    break;
                } else {
                    AvlTreeNode rightNode = findNode(curr, curr.getRight());
                    //右子 > key，新节点作右子
                    if(rightNode.getKey().compareTo(key) > 0){
                        AvlTreeNode newNode = new AvlTreeNode(key, value, curr);
                        newNode.setRight(rightNode.getAddress());
                        newNode.setHeight(rightNode.getHeight() + 1);
                        long newNodeAddress = storage.write(newNode.toBytes());
                        curr.setRight(newNodeAddress);
                        newRoot = rotate(curr, findNode(curr, curr.getLeft()), newNode);
                        break;
                    } else {
                        curr = rightNode;
                    }
                }

            }
        }

        if(newRoot == null){
            newRoot = new AvlTreeNode(key, value, null);
            writeNode(newRoot);
        }

        return newRoot;
    }


    /**
     * 当前失去平衡的节点
     * @param parent
     */
    private AvlTreeNode rotate(AvlTreeNode parent, AvlTreeNode left, AvlTreeNode right){
        //旋转，由子向父上溯
        while (true){
            AvlTreeNode grandParent = parent.getParent();
            int parentLR = 0;
            if(grandParent != null){
                if(parent.getAddress() == grandParent.getLeft()){
                    parentLR = -1;
                } else if(parent.getAddress() == grandParent.getRight()){
                    parentLR = 1;
                } else {
                    throw new IllegalArgumentException("invalid parent");
                }
            }


            long topHeight = height(parent);

            //左失衡
            if(height(left) - height(right) >= 2){
                if(left.getLeft() != 0){
                    parent = rotateLL(parent, left, right);
                } else if(left.getRight() != 0){
                    parent = rotateLR(parent, left, right);
                }
            }
            //右失衡
            else if(height(right) - height(left) >= 2){
                if(right.getRight() != 0){
                    parent = rotateRR(parent, left, right);
                } else if(right.getLeft() != 0){
                    parent = rotateRL(parent, left, right);
                }
            }

            else {
                parent.setHeight(Math.max(height(left), height(right)) + 1);
                writeNode(parent);
                if(parentLR == -1){
                    grandParent.setLeft(parent.getAddress());
                } else if(parentLR == 1){
                    grandParent.setRight(parent.getAddress());
                }
            }

            if(parent.getParent() == null){
                break;
            }

            //树高度未变化，停止上溯
            if(parent.getHeight() <= topHeight){
                break;
            }


            if(grandParent == null){
                break;
            } else {
                left = findNode(grandParent, grandParent.getLeft());
                right = findNode(grandParent, grandParent.getRight());
                parent = grandParent;
            }

        }

        return parent;
    }

    private long height(AvlTreeNode node){
        if(node == null){
            return 0;
        } else {
            return node.getHeight();
        }
    }


    private String key(AvlTreeNode node){
        if(node == null){
            return null;
        } else {
            return node.getKey();
        }
    }


    private AvlTreeNode findNode(AvlTreeNode parent, long address){
        if(address == 0){
            return null;
        } else {
            byte[] nodeBytes = storage.read(address);
            AvlTreeNode node =  AvlTreeNode.toNode(nodeBytes);
            node.setAddress(address);
            node.setParent(parent);
            return node;
        }
    }

    private AvlTreeNode writeNode(AvlTreeNode node){
        long address = storage.write(node.toBytes());
        node.setAddress(address);
        return node;
    }


    /**
     * 最新的根节点
     * @param root
     * @param key
     * @return
     */
    public AvlTreeNode delete(AvlTreeNode root, String key){
        //TODO
        return null;
    }


    private AvlTreeNode rotateLL(AvlTreeNode tree, AvlTreeNode left, AvlTreeNode right){


        AvlTreeNode insertedNode = findNode(left, left.getLeft());

        AvlTreeNode newRight = tree;
        newRight.setLeft(left.getRight());
        newRight.setHeight(height(insertedNode));
        writeNode(newRight);

        AvlTreeNode newTop = left;
        newTop.setRight(newRight.getAddress());
        newTop.setHeight(height(insertedNode) + 1);
        writeNode(newTop);

        AvlTreeNode newLeft = insertedNode;


        newTop.setParent(tree.getParent());
        newRight.setParent(newTop);
        newLeft.setParent(newTop);

        return newTop;
    }

    private AvlTreeNode rotateRR(AvlTreeNode tree, AvlTreeNode left, AvlTreeNode right){

        AvlTreeNode insertedNode = findNode(right, right.getRight());

        AvlTreeNode newLeft = tree;
        newLeft.setRight(right.getLeft());
        newLeft.setHeight(height(insertedNode));
        writeNode(newLeft);


        AvlTreeNode newTop = right;
        newTop.setLeft(newLeft.getAddress());
        newTop.setHeight(height(insertedNode) + 1);
        writeNode(newTop);

        AvlTreeNode newRight = insertedNode;

        newTop.setParent(tree.getParent());
        newRight.setParent(newTop);
        newLeft.setParent(newTop);
        return newTop;
    }

    private AvlTreeNode rotateLR(AvlTreeNode tree, AvlTreeNode left, AvlTreeNode right){
        AvlTreeNode insertedNode = findNode(left, left.getRight());

        AvlTreeNode newLeft = left;
        newLeft.setRight(insertedNode.getLeft());
        newLeft.setHeight(height(insertedNode));
        writeNode(newLeft);

        AvlTreeNode newRight = tree;
        newRight.setLeft(insertedNode.getRight());
        newRight.setHeight(height(insertedNode));
        writeNode(newRight);

        AvlTreeNode newTop = insertedNode;
        newTop.setLeft(newLeft.getAddress());
        newTop.setRight(newRight.getAddress());
        newTop.setHeight(height(insertedNode) + 1);
        writeNode(newTop);

        newTop.setParent(tree.getParent());
        newLeft.setParent(newTop);
        newRight.setParent(newTop);

        return newTop;
    }

    private AvlTreeNode rotateRL(AvlTreeNode tree, AvlTreeNode left, AvlTreeNode right){
        AvlTreeNode insertedNode = findNode(right, right.getLeft());

        AvlTreeNode newLeft = tree;
        newLeft.setRight(insertedNode.getLeft());
        newLeft.setHeight(height(insertedNode));
        writeNode(newLeft);

        AvlTreeNode newRight = right;
        newRight.setLeft(insertedNode.getRight());
        newRight.setHeight(height(insertedNode));
        writeNode(newRight);

        AvlTreeNode newTop = insertedNode;
        newTop.setLeft(newLeft.getAddress());
        newTop.setRight(newRight.getAddress());
        newTop.setHeight(height(insertedNode) + 1);
        writeNode(newTop);

        newTop.setParent(tree.getParent());
        newLeft.setParent(newTop);
        newRight.setParent(newTop);

        return newTop;
    }


}
