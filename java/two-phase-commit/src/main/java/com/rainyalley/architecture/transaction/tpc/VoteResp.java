package com.rainyalley.architecture.transaction.tpc;

public class VoteResp {

    private boolean allow;

    private Node node;

    public VoteResp(boolean allow, Node node) {
        this.allow = allow;
        this.node = node;
    }

    public boolean isAllow() {
        return this.allow;
    }

    public Node getNode() {
        return node;
    }
}
