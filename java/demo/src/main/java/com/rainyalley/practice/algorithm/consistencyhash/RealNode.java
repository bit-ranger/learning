package com.rainyalley.practice.algorithm.consistencyhash;

import java.util.ArrayList;
import java.util.List;

class RealNode<K, V> {
    private K key;
    private V value;

    private List<VirtualNode<K, V>> virtualNodeList;

    public RealNode(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public List<VirtualNode<K, V>> getVirtualNodeList() {
        return virtualNodeList;
    }

    public void push(VirtualNode<K, V> virtualNode) {
        if (virtualNodeList == null) {
            virtualNodeList = new ArrayList<VirtualNode<K, V>>();
        }

        virtualNodeList.add(virtualNode);
    }
}
