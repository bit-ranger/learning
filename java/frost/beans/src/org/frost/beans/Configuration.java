package org.frost.beans;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sllx on 14-12-5.
 */
class Configuration {

    private Map<String,Node> nodesHolder = new HashMap<>();

    Configuration(){}

    Node getNode(String id){
        return nodesHolder.get(id);
    }

    Node putNode(String id, Node node){
        return nodesHolder.put(id,node);
    }

    Collection<Node> getNodes(){
        return nodesHolder.values();
    }

}
