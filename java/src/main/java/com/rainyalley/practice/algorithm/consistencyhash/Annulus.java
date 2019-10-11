package com.rainyalley.practice.algorithm.consistencyhash;

import java.util.*;

/**
 * 一致性hash环
 * @param <K>
 * @param <V>
 */
public class Annulus<K, V> implements Map<K,V>{

    /**
     * 虚节点数量
     */
    final static int VIRTUAL_NODE_NUM = 150;

    private TreeMap<Integer,VirtualNode<K,V>> virtualNodeTreeMap = new TreeMap<Integer,VirtualNode<K,V>>();
    private TreeMap<K, RealNode<K, V>> realNodeTreeMap = new TreeMap<K ,RealNode<K, V>>();

    public V put(K key, V value){
        if(value == null){
            throw new IllegalArgumentException("value can not be null");
        }

        RealNode<K, V> realNode = new RealNode<K, V>(key, value);
        RealNode<K, V> lr = realNodeTreeMap.put(key, realNode);
        removeVirtual(lr);

        for (VirtualNode<K,V> virtualNode : generateVirtual(key, value)) {
            //最后被替换的虚节点
            VirtualNode<K,V> lrv = virtualNodeTreeMap.put(virtualNode.getIndex(), virtualNode);
            //如果lr不为null, 表示此节点已被占用, 将放弃占用此节点
            if(lrv != null){
                virtualNodeTreeMap.put(lrv.getIndex(), lrv);
            } else {
                realNode.push(virtualNode);
            }
        }

        if(lr == null){
            return null;
        } else {
            return lr.getValue();
        }
    }


    public V get(Object key){
        //大于key的最小节点
        Map.Entry<Integer, VirtualNode<K,V>> ceilingEntry = virtualNodeTreeMap.ceilingEntry(hash(key));

        if(ceilingEntry == null){
            //取最小节点, 从最大跃至最小, 构成环状
            ceilingEntry = virtualNodeTreeMap.firstEntry();
        }

        if(ceilingEntry == null){
            return null;
        } else {
            return ceilingEntry.getValue().getHost().getValue();
        }
    }

    @Override
    public V remove(Object key) {
        RealNode<K,V> rm = realNodeTreeMap.remove(key);
        removeVirtual(rm);
        return rm.getValue();
    }


    private int removeVirtual(RealNode<K,V> realNode){

        if(realNode == null || realNode.getVirtualNodeList() == null){
            return 0;
        }

        for (VirtualNode<K,V> virtualNode : realNode.getVirtualNodeList()) {
            virtualNodeTreeMap.remove(virtualNode.getIndex());
        }

        return realNode.getVirtualNodeList().size();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    @Override
    public int size() {
        return realNodeTreeMap.size();
    }

    @Override
    public boolean isEmpty() {
        return realNodeTreeMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }




    private int hash(Object key){
        return key.hashCode();
    }


    /**
     * 生成虚节点
     * @param key
     * @param value
     * @return
     */
    private List<VirtualNode<K,V>> generateVirtual(K key, V value){

        return Collections.emptyList();
    }
}
