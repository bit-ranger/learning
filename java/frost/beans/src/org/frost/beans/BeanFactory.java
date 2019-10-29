package org.frost.beans;

import org.frost.core.Assert;
import org.frost.core.util.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sllx on 14-12-5.
 */
class BeanFactory {

    private Map<String,Object> beansHolder = new HashMap<>();

    private Map<Node,Object> nodeBeanMapper = new HashMap<>();

    private Configuration configuration;

    Object get(String beanID){
        return beansHolder.get(beanID);
    }

    Object get(Node node){
        return nodeBeanMapper.get(node);
    }


    BeanFactory(Configuration configuration){
        Assert.notNull(configuration,"configuration can not be null");
        this.configuration = configuration;
        createBeans();
    }

    private void createBeans(){
        Collection<Node> nodes = configuration.getNodes();
        for (Node node : nodes) {
            createBean(node);
        }
    }

    private void createBean(Node node){
        Tag type = node.getType();
        if(!Tag.BEAN.equals(type)){
            return;
        }

        //创建Bean
        Object classValue = node.attribute(AttributeName.CLASS);
        if(classValue == null){
            throw new AttributeMissingException(String.format("%s missing attribute [class]",node.getType().lowerName()));
        }
        Class<?> clazz = null;
        Object bean = null;
        try{
            clazz  =  Class.forName(String.valueOf(classValue));
            bean = clazz.newInstance();
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }

        //确定id
        String id =node.attribute(AttributeName.ID);
        if(StringUtils.isBlank(id)){
            id = node.attribute(AttributeName.NAME);
        }
        if(StringUtils.isBlank(id)){
            id = clazz.getName();
        }

        if(beansHolder.containsKey(id)){
            throw new BeanIdRepeatException(String.format("bean id [%s] repeated",id));
        }
        beansHolder.put(id,bean);
        nodeBeanMapper.put(node,bean);
    }

    private class BeanIdRepeatException extends RuntimeException{


        private static final long serialVersionUID = -195474653112000878L;

        public BeanIdRepeatException(String message) {
            super(message);
        }
    }
}
