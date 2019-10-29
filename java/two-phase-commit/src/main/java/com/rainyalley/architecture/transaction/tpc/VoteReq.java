package com.rainyalley.architecture.transaction.tpc;

import java.util.List;

public class VoteReq {

    private MasterNode masterNode;

    private List<Node> nodeList;

    public VoteReq(MasterNode masterNode, List<Node> nodeList) {
        this.masterNode = masterNode;
        this.nodeList = nodeList;
    }

    public MasterNode getMasterNode() {
        return masterNode;
    }
}
