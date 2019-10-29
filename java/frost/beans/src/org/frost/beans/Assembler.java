package org.frost.beans;


import org.frost.core.util.BeanUtils;

import java.util.Collection;

class Assembler {

    private Configuration configuration;

    private BeanFactory beanFactory;

    Assembler(Configuration configuration, BeanFactory beanFactory){
        this.configuration = configuration;
        this.beanFactory = beanFactory;
    }

    void assemble(){
        Collection<Node> nodes = configuration.getNodes();
        for (Node node : nodes) {
            handleNode(node);
        }
    }

    private void handleNode(Node node){
        Tag type = node.getType();
        if(!Tag.PROPERTY.equals(type)){
            return;
        }
        String name = node.attribute(AttributeName.NAME);
        if(name == null){
            throw new AttributeMissingException(String.format("%s missing attribute [name]",node.getType().lowerName()));
        }

        String ref = node.attribute(AttributeName.REF);
        String value = node.attribute(AttributeName.VALUE);

        //TODO 判断待优化,异常类型待优化
        if((ref != null && value != null) || (ref == null && value == null)){
            throw new IllegalArgumentException(String.format("ref and value There can only be one",ref));
        }

        Object parentBean = beanFactory.get(node.getParentNode());
        Object target = null;

        if(ref != null){
            target = beanFactory.get(ref);
            if(target == null){
                throw new IllegalArgumentException(String.format("cannot find bean for ref:[%s]",ref));
            }
        }
        if(value != null){
            target = value;
        }

        try {
            BeanUtils.setProperty(parentBean,name,target);
        } catch (Exception e) {
            //TODO 异常类型待优化
            throw new IllegalArgumentException(e);
        }
    }
}
