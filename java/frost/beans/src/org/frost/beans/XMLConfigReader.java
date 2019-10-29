package org.frost.beans;

import org.dom4j.*;
import org.dom4j.Attribute;
import org.dom4j.io.SAXReader;
import org.frost.core.util.ObjectUtils;
import org.frost.core.util.StringUtils;

class XMLConfigReader {

    private final static String ROOT_ID = "0";

    private final static String DELIMITER = ".";

    private String currentLocation;

    private Configuration configuration = new Configuration();

    Configuration read(String location) throws DocumentException {
        return this.read(new String[]{location});
    }

    Configuration read(String[] locations) throws DocumentException {
        SAXReader redder = new SAXReader();
        for (String location : locations) {
            this.currentLocation = location;
            Document doc = redder.read(currentLocation);
            Element root = doc.getRootElement();
            String rootName = root.getName();
            if(!Tag.BEANS.name().equalsIgnoreCase(rootName)){
                throw new DocumentException(String.format("root node must be [%s]",Tag.BEANS.lowerName()));
            }
            initNode(root);
        }
        return configuration;
    }

    private void initNode(Element element){
        element.accept(new VisitorSupport() {

            private Node current;

            @Override
            public void visit(Element node) {
                String tagName = ObjectUtils.toString(node.getName());
                Tag type = Tag.getSupportedTag(tagName);
                if(type == null){
                    throw new UnsupportedTagException(String.format("unsupported tag [%s]",tagName));
                }
                //根据当前元素在xml文档中的位置创建唯一标识符
                String id = createID(node, new StringBuilder());
                String parentID = parentID(id);
                Node parent = configuration.getNode(parentID);
                current = new Node(id,parent,type);
                configuration.putNode(id,current);
            }

            @Override
            public void visit(Attribute attribute){
                String aName = ObjectUtils.toString(attribute.getName());
                AttributeName attributeName = current.getType().getSupportedAttributeName(aName);
                if(attributeName == null){
                    throw new UnsupportedAttributeException(String.format("unsupported attribute [%s] in tag [%s]",aName,current.getType().lowerName()));
                }
                current.addAttribute(new org.frost.beans.Attribute(attributeName,attribute.getValue()));
            }

        });
    }

    private String createID(Element node, StringBuilder id){
        Element parent = node.getParent();
        if(parent == null){
            id.insert(0,ROOT_ID);
            id.insert(0,DELIMITER);
            id.insert(0,currentLocation);
            return id.toString();
        }else{
            int index = parent.indexOf(node);
            id.insert(0,index);
            id.insert(0,DELIMITER);
            return createID(parent,id);
        }
    }

    private String parentID(String id){
        int lastPointIndex =  id.lastIndexOf(DELIMITER);
        if(lastPointIndex < 0){
            return StringUtils.EMPTY;
        } else {
            return id.substring(0,lastPointIndex);
        }
    }

    private class UnsupportedTagException extends RuntimeException{


        private static final long serialVersionUID = -3081197767722567338L;

        public UnsupportedTagException(String message) {
            super(message);
        }
    }

    private class UnsupportedAttributeException extends RuntimeException{


        private static final long serialVersionUID = -5821840856440530685L;

        public UnsupportedAttributeException(String message) {
            super(message);
        }
    }
}
