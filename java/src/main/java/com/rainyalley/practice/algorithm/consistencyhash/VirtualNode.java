package com.rainyalley.practice.algorithm.consistencyhash;

class VirtualNode<K, V> {

    /**
     * 索引
     */
    private int index;

    /**
     * 宿主
     */
    private RealNode<K,V> host;

    public VirtualNode(int index, RealNode<K,V> host) {
        this.index = index;
        this.host = host;
    }

    public int getIndex() {
        return index;
    }

    public RealNode<K,V> getHost() {
        return host;
    }
}
