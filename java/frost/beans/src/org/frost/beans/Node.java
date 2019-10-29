package org.frost.beans;

import org.frost.core.Assert;
import java.util.ArrayList;
import java.util.List;

class Node {

    /**
     * 默认子节点数量
     */
    private final static Integer CHILDREN_NUM = 1;

    /**
     * 默认节点属性数量
     */
    private final static Integer ATTRIBUTE_NUM = 2;

    private String id;
    private Node parentNode;
    private List<Node> childrenNodes;
    private List<Attribute> attributes;
    private Tag type;

    /**
     * @param id 节点ID
     * @param parentNode 父节点
     * @param type 节点类型
     */
    Node(String id, Node parentNode, Tag type){
        Assert.notNull(id,"node id cannot be null");
        Assert.notNull(type,"node type cannot be null");
        this.id = id;
        this.parentNode = parentNode;
        this.type = type;
        if(parentNode != null){
            this.parentNode.addChildNode(this);
        }
    }

    Node getParentNode() {
        return parentNode;
    }

    List<Node> getChildrenNodes() {
        return childrenNodes;
    }

    void addChildNode(Node child) {
        if(this.childrenNodes == null){
            childrenNodes = new ArrayList<>(CHILDREN_NUM);
        }
        this.childrenNodes.add(child);
    }

    List<Attribute> getAttributes() {
        return attributes;
    }

    void addAttribute(Attribute attribute) {
        if(this.attributes == null){
            this.attributes = new ArrayList<>(ATTRIBUTE_NUM);
        }
        this.attributes.add(attribute);
    }

    String attribute(AttributeName attributeName){
        for (Attribute attribute : attributes) {
            if(attribute.getName().equals(attributeName)){
                return attribute.getValue();
            }
        }
        return null;
    }

    Tag getType() {
        return type;
    }
}

